package br.edu.ifes.tpa.trotski.dominio;

public class SistemaTransicao {
	
	private int quantidadeAcoes;
	private boolean[][] matrizAtivacao;
	private boolean[][] matrizDesativacao;
	private boolean[][] matrizTransicao;
	
	
	
	public SistemaTransicao(int quantidadeAcoes, boolean [][] matrizAtivacao, boolean [][] matrizDesativacao){
		this.quantidadeAcoes = quantidadeAcoes;
		this.matrizAtivacao = matrizAtivacao;
		this.matrizDesativacao = matrizDesativacao;
		gerarMatrizTransicao();
	}



	private void gerarMatrizTransicao() {
		// TODO Auto-generated method stub
	}
	
}
