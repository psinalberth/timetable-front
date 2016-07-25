package br.edu.ifma.csp.timetable.model;

public enum Dia {
	
	SEGUNDA("SEG", "Segunda-Feira"),
	TERCA("TER", "Terça-Feira"),
	QUARTA("QUA", "Quarta-Feira"),
	QUINTA("QUI", "Quinta-Feira"),
	SEXTA("SEX", "Sexta-Feira");
	
	private String codigo;
	private String descricao;

	Dia(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
