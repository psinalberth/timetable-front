package br.edu.ifma.csp.timetable.model.report;

import java.io.Serializable;

public class DadosRelatorioProfessorDisciplina implements Serializable {
	
	private static final long serialVersionUID = -9077113934126866007L;
	
	private String disciplina;
	private String professor;
	private String departamento;
	
	public String getDisciplina() {
		return disciplina;
	}
	
	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}
	public String getProfessor() {
		return professor;
	}
	
	public void setProfessor(String professor) {
		this.professor = professor;
	}
	
	public String getDepartamento() {
		return departamento;
	}
	
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
}
