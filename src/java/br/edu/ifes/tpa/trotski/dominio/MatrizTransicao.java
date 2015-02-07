package br.edu.ifes.tpa.trotski.dominio;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class MatrizTransicao {

	private boolean[][] matrizRepresentativa;
	private Set<Estado> estados;
	private Queue<Estado> filaEstados;

	public MatrizTransicao(boolean[][] matrizAtivacao,
			boolean[][] matrizDesativacao) {

		estados = new HashSet<Estado>();
		filaEstados = new LinkedList<Estado>();

		Estado estadoInicial = new Estado(matrizAtivacao.length);
		estados.add(estadoInicial);
		filaEstados.add(estadoInicial);
		ativarPossiveisEstados(matrizAtivacao, matrizDesativacao);

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Estado estado : estados) {
			builder.append(estado.toString());
		}

		return builder.toString();
	}
}
