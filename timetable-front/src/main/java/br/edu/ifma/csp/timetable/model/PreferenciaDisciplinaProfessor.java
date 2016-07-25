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
@Table(name="PREFERENCIA_DISCIPLINA_PROFESSOR")
public class PreferenciaDisciplinaProfessor extends Entidade {

	private static final long serialVersionUID = 5311232373351030581L;
	
	@Id
	@Column(name="ID_PREFERENCIA")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotNull
	@JoinColumn(name="PROFESSOR_ID")
	@ManyToOne(fetch=FetchType.LAZY)
	private Professor professor;
	
	@NotNull
	@JoinColumn(name="DISCIPLINA_ID")
	@ManyToOne(fetch=FetchType.LAZY)
	private Disciplina disciplina;
	
	public PreferenciaDisciplinaProfessor() {
	
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Professor getProfessor() {
		return professor;
	}
	
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	
	public Disciplina getDisciplina() {
		return disciplina;
	}
	
	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}
}