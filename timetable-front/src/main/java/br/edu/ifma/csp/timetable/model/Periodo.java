package br.edu.ifma.csp.timetable.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="PERIODO")
public class Periodo extends Entidade {

	private static final long serialVersionUID = -1031109365878584073L;
	
	@Id
	@Column(name="ID_PERIODO")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotNull
	@Column(name="CODIGO")
	private String codigo;
	
	@NotNull
	@Column(name="QTD_ALUNOS")
	private int qtdAlunos;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CURSO_ID")
	private Curso curso;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="periodo")
	private Set<DetalheDisciplina> detalhes = new HashSet<DetalheDisciplina>();
	
	public Periodo() {
	
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public int getQtdAlunos() {
		return qtdAlunos;
	}

	public void setQtdAlunos(int qtdAlunos) {
		this.qtdAlunos = qtdAlunos;
	}
	
	public Curso getCurso() {
		return curso;
	}
	
	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Set<DetalheDisciplina> getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(Set<DetalheDisciplina> detalhes) {
		this.detalhes = detalhes;
	}
}