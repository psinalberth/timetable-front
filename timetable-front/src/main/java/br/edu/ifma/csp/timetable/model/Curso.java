package br.edu.ifma.csp.timetable.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="CURSO")
public class Curso extends Entidade {

	private static final long serialVersionUID = 2945698851298486207L;
	
	@Id
	@Column(name="ID_CURSO")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotNull
	@Column(name="NOME")
	private String nome;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="curso")
	private Set<Periodo> periodos = new HashSet<Periodo>();
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="curso")
	private Set<MatrizCurricular> matrizes = new HashSet<MatrizCurricular>();
	
	public Curso() {
	
	}

	@Override
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
	
	public Set<Periodo> getPeriodos() {
		return periodos;
	}
	
	public void setPeriodos(Set<Periodo> periodos) {
		this.periodos = periodos;
	}
	
	public Set<MatrizCurricular> getMatrizes() {
		return matrizes;
	}
	
	public void setMatrizes(Set<MatrizCurricular> matrizes) {
		this.matrizes = matrizes;
	}
}