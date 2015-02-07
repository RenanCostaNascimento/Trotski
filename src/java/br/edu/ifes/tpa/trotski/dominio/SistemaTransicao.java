package br.edu.ifes.tpa.trotski.dominio;

public class SistemaTransicao {
	
	private boolean[][] matrizAtivacao;
	private boolean[][] matrizDesativacao;
	private MatrizTransicao matrizTransicao;
	
	
	
	public SistemaTransicao(boolean [][] matrizAtivacao, boolean [][] matrizDesativacao){
		this.matrizAtivacao = matrizAtivacao;
		this.matrizDesativacao = matrizDesativacao;
		matrizTransicao = new MatrizTransicao(matrizAtivacao, matrizDesativacao);
	}
	
	@Override
	public String toString(){
		return matrizTransicao.toString();
	}

	
}
