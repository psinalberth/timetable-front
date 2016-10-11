package br.edu.ifma.csp.timetable.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="TURMA")
public class Turma extends Entidade {

	private static final long serialVersionUID = 375112493590965367L;
	
	@Id
	@Column(name="ID_TURMA")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PERIODO")
	private Periodo periodo;
	
	@NotNull(message="O ano é obrigatório.")
	@Column(name="ANO", columnDefinition="TINYINT(4)")
	private Integer ano;
	
	@NotNull(message="O semestre é obrigatório.")
	@Column(name="SEMESTRE", columnDefinition="TINYINT(2)")
	private Integer semestre;
	
	@NotNull(message="O número de alunos é obrigatório.")
	@Column(name="QTD_ALUNOS", columnDefinition="TINYINT(3)")
	private int qtdAlunos;

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}
	
	public Periodo getPeriodo() {
		return periodo;
	}
	
	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}
	
	public Integer getAno() {
		return ano;
	}
	
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	
	public Integer getSemestre() {
		return semestre;
	}
	
	public void setSemestre(Integer semestre) {
		this.semestre = semestre;
	}
	
	public int getQtdAlunos() {
		return qtdAlunos;
	}
	
	public void setQtdAlunos(int qtdAlunos) {
		this.qtdAlunos = qtdAlunos;
	}
}