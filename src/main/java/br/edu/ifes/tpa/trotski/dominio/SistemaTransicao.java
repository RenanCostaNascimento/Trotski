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
	 * estados que obedecem a propriedade de irreflexividade, à saber: R
	 * é irreflexiva, se e somente se, para todo x em A, (x, x) não pertence à
	 * R.
	 * 
	 * @return os estados que possuem relação de reflexividade.
	 */
	public String verificarIrreflexividade() {

		return matrizTransicao.verificarIrreflexividade();
	}

	@Override
	public String toString() {
		return matrizTransicao.toString();
	}

}
