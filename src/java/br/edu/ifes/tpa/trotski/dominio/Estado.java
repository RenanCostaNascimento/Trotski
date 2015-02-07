package br.edu.ifes.tpa.trotski.dominio;

import java.util.HashSet;
import java.util.Set;

public class Estado {

	private boolean[] configuracao;
	private Set<Estado> proximosEstados;

	public Estado(int tamanhoConfiguracao) {
		proximosEstados = new HashSet<Estado>();
		configuracao = new boolean[tamanhoConfiguracao];
		for (int i = 0; i < configuracao.length; i++) {
			configuracao[i] = false;
		}
	}

	public Set<Estado> getProximosEstados() {
		return proximosEstados;
	}

	public void setProximosEstados(Set<Estado> proximosEstados) {
		this.proximosEstados = proximosEstados;
	}

	public void addProximoEstado(Estado proximoEstado) {
		proximosEstados.add(proximoEstado);
	}

	public boolean[] getConfiguracao() {
		return configuracao;
	}

	public void setConfiguracao(boolean[] configuracao) {
		this.configuracao = configuracao;
	}

	public void mudarConfiguracao(int posicao, boolean novoValor) {
		this.configuracao[posicao] = novoValor;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (Estado estado : proximosEstados) {
			builder.append(this.nomeRepresentativoEstado() + " -> ");
			builder.append(estado.nomeRepresentativoEstado() + ", \n");
		}

		return builder.toString();
	}

	/**
	 * Cria um nome representativo para o estado utilizando sua configuração.
	 * 
	 * @return o nome representativo.
	 */
	public String nomeRepresentativoEstado() {
		String nome = "";
		for (int i = 0; i < configuracao.length; i++) {
			if (this.configuracao[i]) {
				nome += "t";
			} else {
				nome += "f";
			}
		}
		return nome;
	}

	/**
	 * Copia a configuração de um estado para o outro.
	 * 
	 * @param estado
	 *            o estado que se deseja copiar a configuração.
	 */
	public void copiarConfiguracao(Estado estado) {
		for (int i = 0; i < estado.configuracao.length; i++) {
			this.configuracao[i] = estado.configuracao[i];
		}

	}

	/**
	 * Verifica se um estado já existe em um conjunto de estados. A verificação
	 * é realizada através da configuração dos estados.
	 * 
	 * @param conjuntoEstado
	 *            o conjunto de estados de onde se quer verificar a existência
	 *            de novoEstado.
	 * @param novoEstado
	 *            o novo estado que se deseja verificar a existência.
	 * @return true se o estado já existe no conjunto passado como parâmetro.
	 */
	public static boolean estadoExiste(Set<Estado> conjuntoEstado,
			Estado novoEstado) {

		int deveExistir;
		for (Estado estado : conjuntoEstado) {
			deveExistir = 0;
			for (int i = 0; i < novoEstado.getConfiguracao().length; i++) {
				if (estado.getConfiguracao()[i] != novoEstado.getConfiguracao()[i]) {
					break;
				}
				deveExistir++;
			}
			if (deveExistir == novoEstado.getConfiguracao().length) {
				return true;
			}
		}

		return false;
	}

}
