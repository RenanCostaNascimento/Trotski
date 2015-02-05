package br.edu.ifes.tpa.trotski.dominio;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class MatrizTransicao {

	private boolean[][] matrizRepresentativa;
	private Set<Estado> estados;
	private Queue<Estado> filaEstados;

	public MatrizTransicao(boolean[][] matrizAtivacao) {
		
		estados = new HashSet<Estado>();
		filaEstados = new  LinkedList<Estado>();

		Estado estadoInicial = new Estado(matrizAtivacao.length);
		estados.add(estadoInicial);
		filaEstados.add(estadoInicial);
		ativarPossiveisEstados(matrizAtivacao);

//		desativarPossiveisEstados(matrizDesativacao);

	}

	/**
	 * Ativa a matriz de desativação, de modo que novos estados sejam gerados ou
	 * arestas criadas, se possível.
	 * 
	 * @param matrizDesativacao
	 *            a matriz de desativação que se deseja ativar;
	 */
	private void desativarPossiveisEstados(boolean[][] matrizDesativacao) {
		// TODO Auto-generated method stub

	}

	/**
	 * Ativa a matriz de ativação, de modo que novos estados sejam gerados ou
	 * arestas criadas, se possível.
	 * 
	 * @param matrizAtivacao
	 *            a matriz de ativação que se deseja ativar.
	 */
	private void ativarPossiveisEstados(boolean[][] matrizAtivacao) {
		while(!filaEstados.isEmpty()) {
			Estado estado = filaEstados.remove();
			for (int i = 0; i < matrizAtivacao.length; i++) {
				boolean[] condicaoAtivacao = new boolean[matrizAtivacao.length];
				for (int j = 0; j < matrizAtivacao.length; j++) {
					condicaoAtivacao[j] = matrizAtivacao[i][j];
				}
				ativarEstado(estado, condicaoAtivacao, i);
			}
		}
	}

	/**
	 * Ativa um estado, se possível, gerando um novo estado ou uma nova aresta.
	 * 
	 * @param estado
	 *            o estado que se deseja ativar.
	 * @param condicaoAtivacao
	 *            a condição de ativação para o estado.
	 * @param posicaoConfiguracao
	 *            a posição de configuração do estado que será ativado caso a
	 *            condição seja verdadeira.
	 */
	private void ativarEstado(Estado estado, boolean[] condicaoAtivacao,
			int posicaoConfiguracao) {
		boolean estadoDeveAtivar = true;
		for (int i = 0; i < condicaoAtivacao.length; i++) {
			if (condicaoAtivacao[i]
					&& condicaoAtivacao[i] != estado.getConfiguracao()[i]) {
				estadoDeveAtivar = false;
				break;
			}
		}
		if (estadoDeveAtivar) {
			Estado novoEstado = new Estado(condicaoAtivacao.length);
			novoEstado.copiarConfiguracao(estado);
			novoEstado.ativarConfiguracao(posicaoConfiguracao);
			if (!estadoExiste(novoEstado)) {
				estado.addProximoEstado(novoEstado);
				estados.add(novoEstado);
				filaEstados.add(novoEstado);
			}
		}

	}

	/**
	 * Verifica se um estado já existe.
	 * 
	 * @param novoEstado
	 *            o estado que se deseja verificar a existência.
	 * @return true se o estado já existir.
	 */
	private boolean estadoExiste(Estado novoEstado) {

		int deveExistir;
		for (Estado estado : estados) {
			deveExistir = 0;
			for (int i = 0; i < novoEstado.getConfiguracao().length; i++) {
				if (estado.getConfiguracao()[i] != novoEstado.getConfiguracao()[i]) {
					break;
				}
				deveExistir++;
			}
			if(deveExistir == novoEstado.getConfiguracao().length){
				return true;
			}
		}

		return false;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		for(Estado estado : estados){
			builder.append(estado.toString());
		}
		
		return builder.toString();
	}
}
