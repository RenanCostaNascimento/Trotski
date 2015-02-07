package br.edu.ifes.tpa.trotski.util;

import org.json.JSONObject;

public class JSONConverter {
	public static void lerJSON(String json) {
		JSONObject obj = new JSONObject(json);
		String numeroAcoes = obj.getString("numeroAcoes");

		System.out.println("numero de ações:");
		System.out.println(numeroAcoes);

		// JSONArray arr = obj.getJSONArray("posts");
		// for (int i = 0; i < arr.length(); i++)
		// {
		// String post_id = arr.getJSONObject(i).getString("post_id");
		// ......
		// }
	}
}
