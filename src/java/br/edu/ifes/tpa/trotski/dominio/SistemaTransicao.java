package br.edu.ifes.tpa.trotski.dominio;

public class SistemaTransicao {
	
	private int quantidadeAcoes;
	private boolean[][] matrizAtivacao;
	private boolean[][] matrizDesativacao;
	private MatrizTransicao matrizTransicao;
	
	
	
	public SistemaTransicao(boolean [][] matrizAtivacao){
		this.quantidadeAcoes = quantidadeAcoes;
		this.matrizAtivacao = matrizAtivacao;
		this.matrizDesativacao = matrizDesativacao;
		matrizTransicao = new MatrizTransicao(matrizAtivacao);
	}
	
	@Override
	public String toString(){
		return matrizTransicao.toString();
	}

	
}
