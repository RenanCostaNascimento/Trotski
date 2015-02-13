package br.edu.ifes.tpa.trotski.app;

import javax.swing.JFrame;

import br.edu.ifes.tpa.trotski.dominio.MatrizTransicao;
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

		MatrizTransicao matriz = sistema.getMatrizTransicao();

		// Informações sobre o Sistema de Transição.

		// TODO Indicar se as seguintes propriedades são válidas ou não para a
		// relação de transição: reflexiva; írreflexiva; simétrica;
		// anti-simétrica; assimétrica; e transitiva.
		// Se não for, você deve apresentar todos os pares ordenados de estados
		// presentes ou ausentes que não satisfazem a propriedade.
		System.out.println("Todas as Relações:");
		System.out.println(sistema.toString());
		System.out.println("Relações reflexivas:");
		System.out.println(sistema.verificarReflexividade());
		System.out.println("Relações não reflexivas:");
		System.out.println(sistema.verificarIrreflexividade());
		System.out.println("Relações simétricas:");
		System.out.println(sistema.verificarSimetria());
		System.out.println("Relações Antissimétricas:");
		System.out.println(sistema.verificarAntissimetria());
		System.out.println("Relações Assimétricas:");
		System.out.println(sistema.verificarAssimetria());
		System.out.println("Relações Transitivas:");
		System.out.println(sistema.verificarTransitividade());
		System.out.println("Fecho transitivo do estado FTT:");
		System.out.println(sistema.verificarFechoTransitivo("FTT"));

		// TODO Dizer se a relação é de equivalência ou ordem parcial.

		// TODO Apresentar os fechos reflexivo, simétrico e transitivo da
		// relação, caso ela não seja reflexiva, simétrica e transitiva,
		// respectivamente.

		// TODO O sistema é capaz de retornar ao estado inicial?

		// TODO Determinar se é possível para um par de estados (x,y), se é
		// possível alcançar y partindo de x.

		// Exibe a janela para a visualização do grafo.
		GraphWindow frame = new GraphWindow(matriz);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.setVisible(true);
	}

}
