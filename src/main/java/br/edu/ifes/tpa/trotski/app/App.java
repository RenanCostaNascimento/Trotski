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
				{ false, true, false }, { true, false, true } };

		SistemaTransicao sistema = new SistemaTransicao(matrizAtivacao,
				matrizDesativacao);

		// Informações sobre o Sistema de Transição.

		// TODO Indicar se as seguintes propriedades são válidas ou não para a
		// relação de transição: reflexiva; írreflexiva; simétrica;
		// anti-simétrica; assimétrica; e transitiva.
		// Se não for, você deve apresentar todos os pares ordenados de estados
		// presentes ou ausentes que não satisfazem a propriedade.
		System.out.println("Transições reflexivas:");
		System.out.println(sistema.verificarReflexividade());
		System.out.println("Transições não reflexivas:");
		System.out.println(sistema.verificarIrreflexividade());
		System.out.println("Transições simétricas:");
		System.out.println(sistema.verificarSimetria());

		// TODO Dizer se a relação é de equivalência ou ordem parcial.

		// TODO Apresentar os fechos reflexivo, simétrico e transitivo da
		// relação, caso ela não seja reflexiva, simétrica e transitiva,
		// respectivamente.

		// TODO O sistema é capaz de retornar ao estado inicial?

		// TODO Determinar se é possível para um par de estados (x,y), se é
		// possível alcançar y partindo de x.
	}

}