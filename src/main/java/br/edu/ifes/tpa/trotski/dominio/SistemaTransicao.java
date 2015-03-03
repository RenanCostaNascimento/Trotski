package br.edu.ifes.tpa.trotski.dominio;

public class SistemaTransicao {

	private boolean[][] matrizAtivacao;
	private boolean[][] matrizDesativacao;
	private MatrizTransicao matrizTransicao;

	public SistemaTransicao(boolean[][] matrizAtivacao,
			boolean[][] matrizDesativacao) {
		this.matrizAtivacao = matrizAtivacao;
		this.matrizDesativacao = matrizDesativacao;
		matrizTransicao = new MatrizTransicao(matrizAtivacao, matrizDesativacao);
	}

	/**
	 * Verifica todas as relações da matriz de transição, retornando todos os
	 * estados que obedecem a propriedade de reflexividade, à saber: R é
	 * reflexiva, se e somente se, para todo x em A, (x, x) pertence à R.
	 * 
	 * @return os estados que possuem relação de reflexividade.
	 */
	public String verificarReflexividade() {

		return matrizTransicao.verificarReflexividade();
	}

	/**
	 * Verifica todas as relações da matriz de transição, retornando todos os
	 * estados que obedecem a propriedade de irreflexividade, à saber: R é
	 * irreflexiva, se e somente se, para todo x em A, (x, x) não pertence à R.
	 * 
	 * @return os estados que possuem relação de reflexividade.
	 */
	public String verificarIrreflexividade() {
		return matrizTransicao.verificarIrreflexividade();
	}

	/**
	 * Verifica o fecho reflexivo do grafo como um todo, retornando o mesmo.
	 * 
	 * @return o fecho transitivo das relações contidas no grafo.
	 */
	public String verificarFechoReflexivo() {
		return matrizTransicao.verificarFechoReflexivo();
	}

	/**
	 * Verifica quais transições são simétricas.
	 * 
	 * @return as transições que são simétricas.
	 */
	public String verificarSimetria() {
		return matrizTransicao.verificarSimetria();
	}

	/**
	 * Verifica quais transições são antissimétricas.
	 * 
	 * @return as transições que são antissimétricas.
	 */
	public String verificarAntissimetria() {
		return matrizTransicao.verificarAntissimetria();
	}

	/**
	 * Verifica quais transições são assimétricas.
	 * 
	 * @return as transições que são assimétricas.
	 */
	public String verificarAssimetria() {
		return matrizTransicao.verificarAssimetria();
	}

	/**
	 * Percorre a matriz de transição, encontrando todas as relações transitivas
	 * da mesma. Uma relação de transitividade é dada por e1 -> e2 -> e3.
	 * 
	 * @return todas as relações de transitividade.
	 */
	public String verificarTransitividade() {
		return matrizTransicao.verificarTransitividade();
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
	public String verificarFechoTransitivo(String nomeEstado) {

		for (Estado estado : matrizTransicao.getEstados()) {
			if (estado.nomeRepresentativoEstado().equals(nomeEstado)) {
				return matrizTransicao.verificarFechoTransitivo(estado);
			}
		}

		return "Estado ausente do grafo.";
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
	public boolean verificarEquivalencia(String configuracaoEstado1,
			String configuracaoEstado2) {

		Estado estado1 = null, estado2 = null;

		for (Estado estado : matrizTransicao.getEstados()) {
			if (estado.nomeRepresentativoEstado().equals(configuracaoEstado1)) {
				estado1 = estado;
			}
			if (estado.nomeRepresentativoEstado().equals(configuracaoEstado2)) {
				estado2 = estado;
			}
		}

		return matrizTransicao.verificarEquivalencia(estado1, estado2);
	}

	public boolean verificarCaminho(String configuracao1, String configuracao2) {
		Estado x = matrizTransicao.getEstado(configuracao1);
		Estado y = matrizTransicao.getEstado(configuracao2);
		return matrizTransicao.verificarCaminho(x, y);
	}

	/**
	 * Retorna a matriz de transição gerada pelas matrizes de ativação e
	 * desativação.
	 * 
	 * @return Matriz de transição.
	 */
	public MatrizTransicao getMatrizTransicao() {
		return matrizTransicao;
	}

	@Override
	public String toString() {
		return matrizTransicao.toString();
	}

}
