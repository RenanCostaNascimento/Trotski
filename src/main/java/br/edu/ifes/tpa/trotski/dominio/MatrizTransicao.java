package br.edu.ifes.tpa.trotski.dominio;

import java.util.HashSet;
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
				estado.addProximoEstado(novoEstado);
				estados.add(novoEstado);
				filaEstados.add(novoEstado);
			} else {
				// verifica se já existe uma aresta para o novo estado
				if (!Estado.estadoExiste(estado.getProximosEstados(),
						novoEstado)) {
					// só cria uma nova aresta para o novo estado
					estado.addProximoEstado(novoEstado);
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
				builder.append(estado.nomeRepresentativoEstado() + ",\n");
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
				builder.append(estado.nomeRepresentativoEstado() + ",\n");
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
	 * Verifica quais transições são simétricas.
	 *
	 * @return as transições que são simétricas.
	 */
	public String verificarSimetria() {
		List<Estado[]> transicoesSimetricas = new LinkedList<Estado[]>();

		// Encontra as transições simétricas.
		for (Estado[] transicao : transicoes) {
			for (Estado[] outraTransicao : transicoes) {
				if (transicao[0] == outraTransicao[1]
						&& transicao[1] == outraTransicao[0])
					transicoesSimetricas.add(transicao);
			}
		}

		// Forma a string
		StringBuilder builder = new StringBuilder();
		for (Estado[] transicao : transicoesSimetricas) {
			builder.append(transicao[0].nomeRepresentativoEstado() + "-->"
					+ transicao[1].nomeRepresentativoEstado() + "\n");
		}

		return builder.toString();
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Estado estado : estados) {
			builder.append(estado.toString());
		}

		return builder.toString();
	}
}
