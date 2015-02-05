package br.edu.ifes.tpa.trotski.dominio;

import java.util.HashSet;
import java.util.Set;

public class Estado {

	private static int id = 0;
	private String nome;
	private boolean[] configuracao;
	private Set<Estado> proximosEstados;

	public Estado(int tamanhoConfiguracao) {
		proximosEstados = new HashSet<Estado>();
		configuracao = new boolean[tamanhoConfiguracao];
		for (int i = 0; i < configuracao.length; i++) {
			configuracao[i] = false;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<Estado> getProximosEstados() {
		return proximosEstados;
	}

	public void setProximosEstados(Set<Estado> proximosEstados) {
		this.proximosEstados = proximosEstados;
	}

	public void addProximoEstado(Estado proximoEstado) {
		proximosEstados.add(proximoEstado);
	}

	public boolean[] getConfiguracao() {
		return configuracao;
	}

	public void setConfiguracao(boolean[] configuracao) {
		this.configuracao = configuracao;
	}

	public void ativarConfiguracao(int posicao) {
		this.configuracao[posicao] = true;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		
		for(Estado estado : proximosEstados){
			builder.append(nomeRepresentativoEstado(this) + " -> ");
			builder.append(nomeRepresentativoEstado(estado) + ", \n");
		}
		
		return builder.toString();
	}
	
	private String nomeRepresentativoEstado(Estado estado){
		String nome = "";
		for (int i = 0; i < configuracao.length; i++) {
			if (estado.configuracao[i]) {
				nome += "t";
			}else{
				nome += "f";
			}
		}
		return nome;
	}

	public void copiarConfiguracao(Estado estado) {
		for (int i = 0; i < estado.configuracao.length; i++) {
			this.configuracao[i] = estado.configuracao[i];
		}
		
	}

}
