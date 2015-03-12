package br.edu.ifes.tpa.trotski.app;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.json.JSONException;

import br.edu.ifes.tpa.trotski.dominio.MatrizTransicao;
import br.edu.ifes.tpa.trotski.dominio.SistemaTransicao;
import br.edu.ifes.tpa.trotski.util.Util;

public class App {

	private static String arquivoEntrada = "/home/possatti/projects/Trotski/entradas/exemplo.json";

	public static Scanner s = new Scanner(System.in);

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		// Pega os dados do arquivo
		String json = Util.lerArquivo(arquivoEntrada);

		// Inicializa um sistema de transição, usando o json lido do arquivo.
		SistemaTransicao sistema = null;
		try {
			sistema = Util.JSONtoSistemaTransicao(json);
		} catch (JSONException e) {
			System.err
					.println("Não foi possível interpretar o arquivo de entrada.");
			System.err.println("Talvez ele não tenha sido bem construído.");
			System.exit(1);
		}

		// Pega a matriz de transição do sistema.
		MatrizTransicao matriz = sistema.getMatrizTransicao();

		// Loop principal para a exibição do menu.
		while (true) {
			// Exibe o menu e pede uma opção ao usuário.
			exibirMenu();
			String input = prompt("\nSelecione uma opção: ");

			switch (input.toUpperCase()) {
			case "1":
				// Apresenta todas as relações da grafo
				System.out.println("Todas as Relações:");
				System.out.println(sistema.toString());
				holdOn();
				break;

			case "2":
				// Indica se as seguintes propriedades são válidas ou não para a
				// relação de transição: reflexiva; írreflexiva; simétrica;
				// anti-simétrica; assimétrica; e transitiva.
				System.out.println("Relações reflexivas:");
				System.out.println(sistema.verificarReflexividade());
				holdOn();
				break;

			case "3":
				System.out.println("Relações não reflexivas:");
				System.out.println(sistema.verificarIrreflexividade());
				holdOn();
				break;

			case "4":
				System.out.println("Relações simétricas:");
				System.out.println(sistema.verificarSimetria());
				holdOn();
				break;

			case "5":
				System.out.println("Relações Antissimétricas:");
				System.out.println(sistema.verificarAntissimetria());
				holdOn();
				break;

			case "6":
				System.out.println("Relações Assimétricas:");
				System.out.println(sistema.verificarAssimetria());
				holdOn();
				break;

			case "7":
				System.out.println("Relações Transitivas:");
				System.out.println(sistema.verificarTransitividade());
				holdOn();
				break;

			case "8":
				// Diz se a relação é de equivalência ou ordem parcial.
				System.out.println("Entre com um estado (ex: FFF):");
				String primeiro = s.next();
				System.out.println("Entre com outro estado (ex: FFF):");
				String segundo = s.next();
				System.out.println("Verificando equivalência entre estado "
						+ primeiro + " e " + segundo + ":");
				System.out.println(sistema.verificarEquivalencia(primeiro,
						segundo));
				holdOn();
				break;

			case "9":
				System.out.println("Entre com um estado (ex: FFF):");
				String umEstado = s.next();
				System.out.println("Entre com outro estado (ex: FFF):");
				String outroEstado = s.next();
				System.out.println("Verificando ordem parcial entre estado "
						+ umEstado + " e " + outroEstado + ":");
				System.out.println(sistema.verificarOrdemParcial(umEstado,
						outroEstado));
				holdOn();
				break;

			case "A":
				// Apresenta os fechos reflexivo, simétrico e transitivo da
				// relação, caso ela não seja reflexiva, simétrica e transitiva,
				// respectivamente.
				System.out.println("Entre com um estado (ex: FFF):");
				String estado = s.next();
				System.out
						.println("Fecho transitivo do estado " + estado + ":");
				System.out.println(sistema.verificarFechoTransitivo(estado));
				holdOn();
				break;

			case "B":
				System.out.println("Fecho reflexivo do grafo:");
				System.out.println(sistema.verificarFechoReflexivo());
				holdOn();
				break;

			case "C":
				System.out.println("Fecho simétrico do grafo:");
				System.out.println(sistema.verificarFechoSimetrico());
				holdOn();
				break;

			case "D":
				// Verifica se o sistema é capaz de retornar ao estado inicial.
				System.out
						.println("Verificando se o sistema pode retornar ao estado inicial (FFF):");
				System.out.println(sistema.verificarCaminho("FFF", "FFF"));
				holdOn();
				break;

			case "E":
				// Determinar se é possível para um par de estados (x,y), se é
				// possível alcançar y partindo de x.
				System.out.println("Entre com um estado (ex: FFF):");
				String algumEstado = s.next();
				System.out.println("Entre com outro estado (ex: FFF):");
				String maisUmEstado = s.next();
				System.out.println("Verificando se há caminho de "
						+ algumEstado + " para " + maisUmEstado + ":");
				System.out.println(sistema.verificarCaminho(algumEstado,
						maisUmEstado));
				holdOn();
				break;

			case "F":
				// Exibe a janela para a visualização do grafo.
				GraphWindow frame = new GraphWindow(matriz);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setSize(600, 600);
				frame.setVisible(true);
				break;

			case "0":
				// Encerra a execução.
				System.exit(0);

			default:
				System.out.println("Sua entrada não pôde ser interpretada."
						+ "\nPor favor, selecione uma das opções do menu.");
			}
		}
	}

	/**
	 * Exibe o menu principal da aplicação.
	 */
	private static void exibirMenu() {
		System.out.println("Trotski");
		System.out.println("=======");
		System.out.println("");
		System.out.println(" 1 - Exibir Todas as Relações");
		System.out.println(" 2 - Exibir Relações reflexivas");
		System.out.println(" 3 - Exibir Relações não reflexivas");
		System.out.println(" 4 - Exibir Relações simétricas");
		System.out.println(" 5 - Exibir Relações Antissimétricas");
		System.out.println(" 6 - Exibir Relações Assimétricas");
		System.out.println(" 7 - Exibir Relações Transitivas");

		// Diz se a relação é de equivalência ou ordem parcial.
		System.out.println(" 8 - Verificar equivalência entre estados");
		System.out.println(" 9 - Verificar ordem parcial entre estados");

		// Apresenta os fechos reflexivo, simétrico e transitivo da
		// relação, caso ela não seja reflexiva, simétrica e transitiva,
		// respectivamente.
		System.out.println(" A - Exibir fecho transitivo de um estado");
		System.out.println(" B - Exibir fecho reflexivo do grafo");
		System.out.println(" C - Exibir Fecho simétrico do grafo");

		// Verifica se o sistema é capaz de retornar ao estado inicial.
		System.out
				.println(" D - Verificar se o sistema pode retornar ao estado inicial (FFF)");

		// Determinar se é possível para um par de estados (x,y), se é
		// possível alcançar y partindo de x.
		System.out.println(" E - Verificar existência de caminho");

		// Exibir representação da grafo
		System.out.println(" F - Exibir representação visual da grafo");
		System.out.println(" 0 (zero) - Sair do programa");
	}

	/**
	 * Pede ao usuário uma entrada.
	 * 
	 * @return Uma String com a entrada do usuário.
	 */
	public static String prompt() {
		return prompt("");
	}

	/**
	 * Pede ao usuário uma entrada, exibindo uma mensagem antes.
	 *
	 * @param mensagem
	 *            Mensagem a ser exibida antes de ser pedido uma entrada.
	 * @return Uma String com a entrada do usuário.
	 */
	public static String prompt(String mensagem) {
		System.out.println(mensagem);
		System.out.print(" >> ");
		String entrada = s.next();
		System.out.println("");

		return entrada;
	}

	/**
	 * Faz a execução esperar uma resposta do usuário. Quando ele pressionar
	 * enter, a execução irá continuar.
	 */
	public static void holdOn() {
		System.out.print("\nPressione enter para continuar...");
		try {
			System.in.read();
		} catch (IOException ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("");
	}
}
