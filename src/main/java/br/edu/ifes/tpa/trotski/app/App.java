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

		// Apresenta todas as relações da grafo
		System.out.println("Todas as Relações:");
		System.out.println(sistema.toString());

		// Indica se as seguintes propriedades são válidas ou não para a
		// relação de transição: reflexiva; írreflexiva; simétrica;
		// anti-simétrica; assimétrica; e transitiva.
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

		// Diz se a relação é de equivalência ou ordem parcial.
		System.out.println("Verificando equivalência entre estado FFF e TFF:");
		System.out.println(sistema.verificarEquivalencia("FFF", "TFF") + "\n");
		System.out.println("Verificando equivalência entre estado TTT e TTF:");
		System.out.println(sistema.verificarEquivalencia("TTT", "TTF") + "\n");
		System.out.println("Verificando ordem parcial entre estado FFF e TFF:");
		System.out.println(sistema.verificarOrdemParcial("FFF", "TFF") + "\n");
		System.out.println("Verificando ordem parcial entre estado TTT e TTF:");
		System.out.println(sistema.verificarOrdemParcial("TTT", "TTF") + "\n");

		// Apresenta os fechos reflexivo, simétrico e transitivo da
		// relação, caso ela não seja reflexiva, simétrica e transitiva,
		// respectivamente.
		System.out.println("Fecho transitivo do estado FFF:");
		System.out.println(sistema.verificarFechoTransitivo("FFF"));
		System.out.println("Fecho reflexivo do grafo:");
		System.out.println(sistema.verificarFechoReflexivo());
		System.out.println("Fecho simétrico do grafo:");
		System.out.println(sistema.verificarFechoSimetrico());

		// Verifica se o sistema é capaz de retornar ao estado inicial.
		System.out
				.println("Verificando se o sistema pode retornar ao estado inicial (FFF):");
		System.out.println(sistema.verificarCaminho("FFF", "FFF"));

		// Determinar se é possível para um par de estados (x,y), se é
		// possível alcançar y partindo de x.
		System.out.println("Verificando caminho de FFF para TTT:");
		System.out.println(sistema.verificarCaminho("FFF", "TTT"));

		// Exibe a janela para a visualização do grafo.
		GraphWindow frame = new GraphWindow(matriz);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.setVisible(true);
	}

}
