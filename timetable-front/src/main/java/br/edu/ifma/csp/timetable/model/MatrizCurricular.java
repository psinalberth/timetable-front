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
@Table(name="MATRIZ_CURRICULAR")
public class MatrizCurricular extends Entidade {

	private static final long serialVersionUID = -932527619399754046L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_MATRIZ")
	private int id;
	
	@NotNull(message="O ano é obrigatório.")
	@Column(name="ANO", scale=4)
	private int ano;
	
	@NotNull(message="A quantidade de semestres é obrigatória.")
	@Column(name="SEMESTRES", scale=2)
	private int semestres;
	
	@NotNull(message="O curso é obrigatório.")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CURSO")
	private Curso curso;
	
	@NotNull(message="O turno é obrigatório.")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TURNO")
	private Turno turno;
	
	public MatrizCurricular() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public Turno getTurno() {
		return turno;
	}
	
	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public int getSemestres() {
		return semestres;
	}

	public void setSemestres(int semestres) {
		this.semestres = semestres;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
}