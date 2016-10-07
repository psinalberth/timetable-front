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
@Table(name="PREFERENCIA_HORARIO_PROFESSOR")
public class PreferenciaHorarioProfessor extends Entidade {
	
	private static final long serialVersionUID = 5657565247702409911L;
	
	@Id
	@Column(name="ID_PREFERENCIA")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotNull(message="O professor é obrigatório.")
	@JoinColumn(name="ID_PROFESSOR")
	@ManyToOne(fetch=FetchType.LAZY)
	private Professor professor;
	
	@NotNull(message="O horário é obrigatório.")
	@JoinColumn(name="ID_HORARIO")
	@ManyToOne(fetch=FetchType.LAZY)
	private Horario horario;

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Horario getHorario() {
		return horario;
	}

	public void setHorario(Horario horario) {
		this.horario = horario;
	}
}