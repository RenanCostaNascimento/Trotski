package br.edu.ifes.tpa.trotski.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.edu.ifes.tpa.trotski.dominio.SistemaTransicao;

public class Util {

	/**
	 * Lê todas as linhas de um arquivo e retorna tudo como uma String só.
	 * 
	 * @param arquivo
	 *            Arquivo a ser lido.
	 * @return Texto contido no arquivo.
	 * @throws IOException
	 */
	public static String lerArquivo(String arquivo) throws IOException {
		// Pega as linhas do arquivo de entrada
		List<String> lines = Files.readAllLines(Paths.get(arquivo),
				StandardCharsets.UTF_8);

		// Concatena as linhas
		StringBuilder sb = new StringBuilder();
		for (String line : lines) {
			sb.append(line + "\n");
		}

		// Retorna todo o texto do arquivo
		return sb.toString();
	}

	public static SistemaTransicao JSONtoSistemaTransicao(String json)
			throws JSONException {
		JSONObject obj = new JSONObject(json);

		// Monta a matriz de ativação
		JSONArray ativacao = obj.getJSONArray("matrizAtivacao");
		JSONArray desativacao = obj.getJSONArray("matrizDesativacao");
		int tamanho = ativacao.length();

		boolean[][] matrizAtivacao = new boolean[tamanho][tamanho];
		for (int i = 0; i < tamanho; i++) {
			JSONArray linha = ativacao.getJSONArray(i);
			// boolean[] linha = new boolean[tamanho];
			for (int j = 0; j < tamanho; j++) {
				boolean b = linha.getBoolean(j);
				// linha[j] = b;
				matrizAtivacao[i][j] = b;
			}
		}

		boolean[][] matrizDesativacao = new boolean[tamanho][tamanho];
		for (int i = 0; i < tamanho; i++) {
			JSONArray linha = desativacao.getJSONArray(i);
			for (int j = 0; j < tamanho; j++) {
				boolean b = linha.getBoolean(j);
				matrizDesativacao[i][j] = b;
			}
		}

		// ! Exibe as matrizes
		System.out.println("Matriz de Ativação");
		for (int i = 0; i < tamanho; i++) {
			for (int j = 0; j < tamanho; j++) {
				System.out.print(matrizAtivacao[i][j] + ", ");
			}
			System.out.println("");
		}
		System.out.println("Matriz de Desativação");
		for (int i = 0; i < tamanho; i++) {
			for (int j = 0; j < tamanho; j++) {
				System.out.print(matrizDesativacao[i][j] + ", ");
			}
			System.out.println("");
		}

		// Cria o sistema de transição
		SistemaTransicao sistema = new SistemaTransicao(matrizAtivacao,
				matrizDesativacao);

		return sistema;
	}
}
