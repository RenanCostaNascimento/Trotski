package br.edu.ifes.tpa.trotski.dominio;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class MatrizTransicao {

	private boolean[][] matrizRepresentativa;
	private Set<Estado> estados;
	private Set<Estado[]> transicoes;
	private Queue<Estado> filaEstados;

	public MatrizTransicao(boolean[][] matrizAtivacao,
			boolean[][] matrizDesativacao) {

		estados = new HashSet<Estado>();
		filaEstados = new LinkedList<Estado>();

		Estado estadoInicial = new Estado(matrizAtivacao.length);
		estados.add(estadoInicial);
		filaEstados.add(estadoInicial);
		ativarPossiveisEstados(matrizAtivacao, matrizDesativacao);

		// Forma as transições. Só deve ser invocado depois que o todos os
		// estados já foram gerados.
		this.transicoes = obterTransicoes();
	}

	/**
	 * Ativa a matriz de ativação, de modo que novos estados sejam gerados ou
	 * arestas criadas, se possível.
	 * 
	 * @param matrizAtivacao
	 *            a matriz de ativação que se deseja ativar.
	 */
	private void ativarPossiveisEstados(boolean[][] matrizAtivacao,
			boolean[][] matrizDesativacao) {
		while (!filaEstados.isEmpty()) {
			Estado estado = filaEstados.remove();
			for (int i = 0; i < matrizAtivacao.length; i++) {
				boolean[] condicaoAtivacao = new boolean[matrizAtivacao.length];
				boolean[] condicaoDesativacao = new boolean[matrizDesativacao.length];
				for (int j = 0; j < matrizAtivacao.length; j++) {
					condicaoAtivacao[j] = matrizAtivacao[i][j];
					condicaoDesativacao[j] = matrizDesativacao[i][j];
				}
				// tenta ativar o estado i
				ativarDesativarEstado(estado, condicaoAtivacao, i, true);
				// tenta desativar o estado i
				ativarDesativarEstado(estado, condicaoDesativacao, i, false);
			}
		}
	}

	/**
	 * Ativa ou desativa um estado, se possível, gerando um novo estado ou uma
	 * nova aresta.
	 * 
	 * @param estado
	 *            o estado que se deseja ativar ou desativar.
	 * @param condicaoAtivacaoDesativacao
	 *            a condição de ativação ou desativação para o estado.
	 * @param posicaoConfiguracao
	 *            a posição de configuração do estado que será ativado ou
	 *            desativado caso a condição seja verdadeira.
	 * @param ativacao
	 *            determina se o estado passado como parâmetro deve ativar ou
	 *            desativar.
	 */
	private void ativarDesativarEstado(Estado estado,
			boolean[] condicaoAtivacaoDesativacao, int posicaoConfiguracao,
			boolean ativacao) {
		boolean estadoDeveAtivarDesativar = true;
		for (int i = 0; i < condicaoAtivacaoDesativacao.length; i++) {
			if (condicaoAtivacaoDesativacao[i]
					&& condicaoAtivacaoDesativacao[i] != estado
							.getConfiguracao()[i]) {
				estadoDeveAtivarDesativar = false;
				break;
			}
		}
		if (estadoDeveAtivarDesativar) {
			Estado novoEstado = new Estado(condicaoAtivacaoDesativacao.length);
			novoEstado.copiarConfiguracao(estado);
			novoEstado.mudarConfiguracao(posicaoConfiguracao, ativacao);
			// verifica se o estado existe no grafo
			if (!Estado.estadoExiste(estados, novoEstado)) {
				// cria uma nova aresta para o novo estado e o adiciona no grafo
				estados.add(novoEstado);
				estado.addProximoEstado(getEstado(novoEstado));
				filaEstados.add(getEstado(novoEstado));
			} else {
				// verifica se já existe uma aresta para o novo estado
				if (!Estado.estadoExiste(estado.getProximosEstados(),
						novoEstado)) {
					// só cria uma nova aresta para o novo estado
					estado.addProximoEstado(getEstado(novoEstado));
				}
			}
		}
	}

	/**
	 * Verifica todas as relações da matriz de transição, retornando todos os
	 * estados que obedecem a propriedade de reflexividade, à saber: R é
	 * reflexiva, se e somente se, para todo x em A, (x, x) pertence à R.
	 * 
	 * @return os estados que possuem relação de reflexividade.
	 */
	public String verificarReflexividade() {
		StringBuilder builder = new StringBuilder();

		for (Estado estado : estados) {
			if (verificarReflexividade(estado)) {
				builder.append(estado.nomeRepresentativoEstado() + "\n");
			}
		}

		return builder.toString();
	}

	/**
	 * Verifica todas as relações da matriz de transição, retornando todos os
	 * estados que obedecem a propriedade de irreflexividade, à saber: R é
	 * irreflexiva, se e somente se, para todo x em A, (x, x) não pertence à R.
	 * 
	 * @return os estados que possuem relação de reflexividade.
	 */
	public String verificarIrreflexividade() {
		StringBuilder builder = new StringBuilder();

		for (Estado estado : estados) {
			if (!verificarReflexividade(estado)) {
				builder.append(estado.nomeRepresentativoEstado() + "\n");
			}
		}

		return builder.toString();
	}

	/**
	 * Verifica se um estado obedece a propriedade de reflexividade.
	 * 
	 * @param estado
	 *            o estado que se deseja verificar a propriedade.
	 * @return true se o estado obedecer a propriedade.
	 */
	private boolean verificarReflexividade(Estado estado) {

		String nomeEstado = estado.nomeRepresentativoEstado();
		for (Estado proximoEstado : estado.getProximosEstados()) {
			String nomeProximoEstado = proximoEstado.nomeRepresentativoEstado();
			if (nomeEstado.equals(nomeProximoEstado)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Verifica o fecho reflexivo do grafo como um todo, retornando o mesmo.
	 * 
	 * @return o fecho transitivo das relações contidas no grafo.
	 */
	public String verificarFechoReflexivo() {
		StringBuilder builder = new StringBuilder();

		for (Estado estado : estados) {
			builder.append(estado.toString());
			if (!verificarReflexividade(estado)) {
				builder.append(estado.nomeRepresentativoEstado() + " -> "
						+ estado.nomeRepresentativoEstado() + "\n");
			}
		}

		return builder.toString();
	}

	/**
	 * Verifica quais transições são simétricas. Os vertices x e y tem uma
	 * transição simétrica se há uma relação de x para y e de y para x. Sendo
	 * que x e y podem ser iguais.
	 * 
	 * @return as transições que são simétricas.
	 */
	public String verificarSimetria() {
		StringBuilder builder = new StringBuilder();

		for (Estado estado : estados) {
			for (Estado proximoEstado : estado.getProximosEstados()) {

				if (verificarSimetria(estado, proximoEstado)) {
					builder.append(estado.nomeRepresentativoEstado() + " <-> "
							+ proximoEstado.nomeRepresentativoEstado() + "\n");
				}
			}
		}

		return builder.toString();
	}

	/**
	 * Verifica se há simetria entre dois estados.
	 * 
	 * @param estado1
	 *            um dos estados que se quer saber a simetria.
	 * @param estado2
	 *            outro estado que se quer saber a simetria.
	 * @return true se os estados formarem uma relação de simetria.
	 */
	private boolean verificarSimetria(Estado estado1, Estado estado2) {

		boolean estado1ArcoEstado2 = false;
		boolean estado2ArcoEstado1 = false;

		for (Estado proximoEstado1 : estado1.getProximosEstados()) {
			if (Estado.configuracoesIguais(proximoEstado1, estado2)) {
				estado1ArcoEstado2 = true;
				break;
			}
		}
		for (Estado proximoEstado2 : estado2.getProximosEstados()) {
			if (Estado.configuracoesIguais(proximoEstado2, estado1)) {
				estado2ArcoEstado1 = true;
				break;
			}
		}

		if (estado1ArcoEstado2 && estado2ArcoEstado1) {
			return true;
		}

		return false;
	}

	/**
	 * Verifica quais transições são antissimétricas. Os vertices x e y tem uma
	 * transição anti-simétrica se há uma relação de x para y, porém não há uma
	 * relação de y para x. Entretando, x e y podem ser iguais. Isto é, relações
	 * reflexivas são permitidas.
	 * 
	 * @return as transições que são antissimétricas.
	 */
	public String verificarAntissimetria() {
		StringBuilder builder = new StringBuilder();

		for (Estado estado : estados) {
			for (Estado proximoEstado : estado.getProximosEstados()) {

				if (!verificarSimetria(estado, proximoEstado)
						|| Estado.configuracoesIguais(estado, proximoEstado)) {
					builder.append(estado.nomeRepresentativoEstado() + " -> "
							+ proximoEstado.nomeRepresentativoEstado() + "\n");
				}
			}
		}

		return builder.toString();
	}

	/**
	 * Verifica quais transições são assimétricas. Os vertices x e y tem uma
	 * transição assimetrica se há uma relação de x para y, porém não há uma
	 * relação de y para x. Mesmo que x e y sejam iguais. Isto é, relações
	 * reflexivas NÃO são permitidas.
	 * 
	 * @return as transições que são assimétricas.
	 */
	public String verificarAssimetria() {
		StringBuilder builder = new StringBuilder();

		for (Estado estado : estados) {
			for (Estado proximoEstado : estado.getProximosEstados()) {

				if (!verificarSimetria(estado, proximoEstado)) {
					builder.append(estado.nomeRepresentativoEstado() + " -> "
							+ proximoEstado.nomeRepresentativoEstado() + "\n");
				}
			}
		}

		return builder.toString();
	}

	/**
	 * Percorre a matriz de transição, encontrando todas as relações transitivas
	 * da mesma. Uma relação transitiva é representada por um grafo direcionado
	 * que, para qualquer sequência de arcos que, saindo de um elemento A,
	 * chegam a um elemento Z, existe um arco ligando A e Z diretamente.
	 * 
	 * @return todas as relações de transitividade.
	 */
	public String verificarTransitividade() {
		StringBuilder builder = new StringBuilder();

		for (Estado estado : estados) {
			for (Estado proximoEstado : estado.getProximosEstados()) {
				if (!Estado.configuracoesIguais(estado, proximoEstado)
						&& verificarAdjacenciaDirecionada(estado, proximoEstado)) {
					builder.append(estado.nomeRepresentativoEstado() + " -> "
							+ proximoEstado.nomeRepresentativoEstado() + "\n");
				}
			}

		}

		return builder.toString();
	}

	/**
	 * Verifica se o estado1 possui uma aresta para estado2 diretamente.
	 * 
	 * @param estado1
	 *            o estado de onde a aresta parte.
	 * @param estado2
	 *            o estado em que a aresta chega.
	 * @return true se existe ligação direta de estado1 para estado2.
	 */
	private boolean verificarAdjacenciaDirecionada(Estado estado1,
			Estado estado2) {

		return Estado.estadoExiste(estado1.getProximosEstados(), estado2);

	}

	/**
	 * Verifica o fecho transitivo de um estado. O fecho transitivo direto (ftd)
	 * de um vértice v é o conjunto de todos os vértices que podem ser atingidos
	 * por algum caminho iniciando em v.
	 * 
	 * @param estado
	 *            o estado do qual ser quer saber o fecho transitivo.
	 * @return uma string contendo o nome de todos os estados que fazer parte do
	 *         fecho transitivo.
	 */
	public String verificarFechoTransitivo(Estado estado) {

		StringBuilder builder = new StringBuilder();
		Set<String> estadosVisitados = new HashSet<>();
		Queue<Estado> fila = new LinkedList<>();
		fila.add(estado);

		while (!fila.isEmpty()) {
			verificarFechoTransitivo(fila.remove(), estadosVisitados, fila);
		}

		for (String nomeEstado : estadosVisitados) {
			builder.append(nomeEstado + "\n");
		}

		return builder.toString();
	}

	/**
	 * Verifica o fecho transitivo de um estado, adicionando os estados que
	 * fazer parte do mesmo em um set.
	 * 
	 * @param estado
	 *            o estado que se deseja saber o fecho transitivo.
	 * @param estadosVisitados
	 *            o set contendo os estados do fecho transitivo.
	 * @param fila
	 */
	private void verificarFechoTransitivo(Estado estado,
			Set<String> estadosVisitados, Queue<Estado> fila) {

		Iterator<Estado> iterator = estado.getProximosEstados().iterator();

		while (iterator.hasNext()) {
			Estado proximoEstado = iterator.next();
			if (adicionarEstado(estadosVisitados, proximoEstado)) {
				fila.add(proximoEstado);
			}
		}
	}

	/**
	 * Adiciona um estado em um set de estados.
	 * 
	 * @param setEstados
	 *            o set de estado em que o estado será inserido.
	 * @param estado
	 *            o estado que se deseja inserir.
	 * @return true se o estado foi adicionado.
	 */
	private boolean adicionarEstado(Set<String> setEstados, Estado estado) {

		return setEstados.add(estado.nomeRepresentativoEstado());

	}

	/**
	 * Verifica se há relação de equivalência entre 2 estados.
	 * 
	 * @param estado1
	 *            o primeiro estado.
	 * @param estado2
	 *            o segundo estado.
	 * @return true se houve relação de equivalência.
	 */
	public boolean verificarEquivalencia(Estado estado1, Estado estado2) {

		// Não é necessário verificar a transitividade entre os estados, pois se
		// a verificação de simetria for obedecida, então a verificação de
		// transitividade também será obedecida, uma vez que existe uma ligação
		// direta entre os dois estados.
		if (verificarReflexividade(estado1) && verificarReflexividade(estado2)
				&& verificarSimetria(estado1, estado2)) {
			return true;
		}

		return false;
	}

	/**
	 * Usa o algoritmo de busca em largura para verificar se é possível chegar
	 * em y a partir de x.
	 * 
	 * @param x
	 *            Estado de partida.
	 * @param y
	 *            Estado a se chegar.
	 * @return true, se for possível chegar em y a partir de x.
	 */
	public boolean verificarCaminho(Estado x, Estado y) {
		Queue<Estado> q = new LinkedList<>();
		List<Estado> descobertos = new LinkedList<Estado>();

		Estado v = x;

		q.add(v);
		descobertos.add(v);

		while (q.size() > 0) {
			v = q.poll();
			for (Estado w : v.getProximosEstados()) {
				// Verifica se o destino foi alcançado
				if (Estado.configuracoesIguais(w, y)) {
					return true;
				}

				if (!descobertos.contains(w)) {
					q.add(w);
					descobertos.add(w);
				}
			}
		}

		return false;
	}

	/**
	 * Gera uma conjunto de transições. Onde cada transição é um vetor com
	 * apenas dois estados, e indica que há uma transição do primeiro estado
	 * para o segundo.
	 * 
	 * @return conjunto de transições.
	 */
	private Set<Estado[]> obterTransicoes() {
		Set<Estado[]> transicoes = new HashSet<Estado[]>();

		for (Estado estado : estados) {
			for (Estado proximo : estado.getProximosEstados()) {
				Estado transicao[] = { estado, proximo };
				transicoes.add(transicao);
			}
		}

		return transicoes;
	}

	public Set<Estado> getEstados() {
		return estados;
	}

	public Set<Estado[]> getTransicoes() {
		return transicoes;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Estado estado : estados) {
			builder.append(estado.toString());
		}

		return builder.toString();
	}

	/**
	 * Procura dentro da matriz o estado que tenha a configuração igual a
	 * indicada e o retorna.
	 * 
	 * @param representacao
	 *            A representacao do estado desejado.
	 * @return O estado desejado.
	 */
	public Estado getEstado(String representacao) {

		for (Estado estado : estados) {
			if (representacao.equals(estado.nomeRepresentativoEstado())) {
				return estado;
			}
		}

		return null;
	}

	/**
	 * Busca por um estado que tenha uma configuração igual a do estado
	 * indicado.
	 * 
	 * @param configuracao
	 *            Estado com a configuração a qual deseja-se buscar.
	 * @return Estado encontrado.
	 */
	public Estado getEstado(Estado configuracao) {

		for (Estado estado : estados) {
			if (Estado.configuracoesIguais(estado, configuracao)) {
				return estado;
			}
		}

		return null;
	}
}
