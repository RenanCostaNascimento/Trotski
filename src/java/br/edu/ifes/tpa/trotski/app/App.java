package br.edu.ifes.tpa.trotski.app;

import br.edu.ifes.tpa.trotski.dominio.SistemaTransicao;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		boolean[][] matrizAtivacao = { { false, false, false },
				{ true, false, false }, { true, false, false } };
		boolean[][] matrizDesativacao = { { true, true, false },
				{ false, true, false }, { true, false, true} };

		SistemaTransicao sistema = new SistemaTransicao(matrizAtivacao, matrizDesativacao);
		System.out.println(sistema);

	}

}
