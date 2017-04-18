package br.edu.ifma.csp.timetable.model.report;

import java.io.Serializable;
import java.util.Date;

public class DadosRelatorioAulasMatrizCurricular implements Serializable {

	private static final long serialVersionUID = -1156727444927560519L;
	
	private int periodo;
	private Date horario;
	private String segunda;
	private String terca;
	private String quarta;
	private String quinta;
	private String sexta;
	
	public int getPeriodo() {
		return periodo;
	}
	
	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}
	
	public Date getHorario() {
		return horario;
	}
	
	public void setHorario(Date horario) {
		this.horario = horario;
	}
	
	public String getSegunda() {
		return segunda;
	}
	
	public void setSegunda(String segunda) {
		this.segunda = segunda;
	}
	
	public String getTerca() {
		return terca;
	}
	
	public void setTerca(String terca) {
		this.terca = terca;
	}
	
	public String getQuarta() {
		return quarta;
	}
	
	public void setQuarta(String quarta) {
		this.quarta = quarta;
	}
	
	public String getQuinta() {
		return quinta;
	}
	
	public void setQuinta(String quinta) {
		this.quinta = quinta;
	}
	
	public String getSexta() {
		return sexta;
	}
	
	public void setSexta(String sexta) {
		this.sexta = sexta;
	}
}