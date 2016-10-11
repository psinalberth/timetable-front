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

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="PERIODO")
public class Periodo extends Entidade {

	private static final long serialVersionUID = -1031109365878584073L;
	
	@Id
	@Column(name="ID_PERIODO")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotBlank(message="codigo#O código é obrigatório.")
	@Column(name="CODIGO")
	private String codigo;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_MATRIZ")
	private MatrizCurricular matrizCurricular;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="periodo", orphanRemoval=true)
	private Set<DetalheDisciplina> detalhes = new HashSet<DetalheDisciplina>();
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="periodo", orphanRemoval=true)
	private Set<Turma> turmas = new HashSet<Turma>();

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
	
	public MatrizCurricular getMatrizCurricular() {
		return matrizCurricular;
	}
	
	public void setMatrizCurricular(MatrizCurricular matrizCurricular) {
		this.matrizCurricular = matrizCurricular;
	}

	public Set<DetalheDisciplina> getDetalhes() {
		return detalhes;
	}
	
	public void setDetalhes(Set<DetalheDisciplina> detalhes) {
		this.detalhes = detalhes;
	}
	
	public Set<Turma> getTurmas() {
		return turmas;
	}
	
	public void setTurmas(Set<Turma> turmas) {
		this.turmas = turmas;
	}
}