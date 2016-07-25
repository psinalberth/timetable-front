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
@Table(name="PROFESSOR")
public class Professor extends Entidade {

	private static final long serialVersionUID = -6331342046688981121L;
	
	@Id
	@NotNull
	@Column(name="ID_PROFESSOR")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@Column(name="NOME")
	private String nome;
	
	@NotNull
	@Column(name="ENDERECO")
	private String endereco;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="professor")
	private Set<Aula> aulas = new HashSet<Aula>();
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="professor")
	private Set<PreferenciaDisciplinaProfessor> preferencias = new HashSet<PreferenciaDisciplinaProfessor>();
	
	public Professor() {
	
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
	
	public String getEndereco() {
		return endereco;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public Set<Aula> getAulas() {
		return aulas;
	}
	
	public void setAulas(Set<Aula> aulas) {
		this.aulas = aulas;
	}
	
	public Set<PreferenciaDisciplinaProfessor> getPreferencias() {
		return preferencias;
	}
	
	public void setPreferencias(Set<PreferenciaDisciplinaProfessor> preferencias) {
		this.preferencias = preferencias;
	}
	
	@Override
	public String toString() {
		return this.getNome();
	}
}