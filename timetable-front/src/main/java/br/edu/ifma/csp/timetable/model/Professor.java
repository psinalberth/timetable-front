package br.edu.ifma.csp.timetable.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="PROFESSOR")
public class Professor extends Entidade {

	private static final long serialVersionUID = -6331342046688981121L;
	
	@Id
	@NotNull
	@Column(name="ID_PROFESSOR")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotBlank(message="nome#O nome é obrigatório.")
	@Column(name="NOME", length=100)
	private String nome;
	
	@NotBlank(message="endereco#O endereço é obrigatório.")
	@Column(name="ENDERECO", length=140)
	private String endereco;
	
	@NotNull(message="O departamento é obrigatório.")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DEPARTAMENTO")
	private Departamento departamento;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="professor")
	private Set<Aula> aulas = new HashSet<Aula>();
	
	@Valid
	@Size(min=1, message="Nenhuma preferência adicionada.")
	@OneToMany(fetch=FetchType.LAZY, mappedBy="professor", cascade=CascadeType.ALL, orphanRemoval=true)
	private Set<PreferenciaDisciplinaProfessor> preferenciasDisciplina = new HashSet<PreferenciaDisciplinaProfessor>();
	
	@Valid
	@OneToMany(fetch=FetchType.LAZY, mappedBy="professor", cascade=CascadeType.ALL, orphanRemoval=true)
	private Set<PreferenciaHorarioProfessor> preferenciasHorario = new HashSet<PreferenciaHorarioProfessor>();

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
	
	public Departamento getDepartamento() {
		return departamento;
	}
	
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	
	public Set<Aula> getAulas() {
		return aulas;
	}
	
	public void setAulas(Set<Aula> aulas) {
		this.aulas = aulas;
	}
	
	public Set<PreferenciaDisciplinaProfessor> getPreferenciasDisciplina() {
		return preferenciasDisciplina;
	}
	
	public void setPreferenciasDisciplina(Set<PreferenciaDisciplinaProfessor> preferenciasDisciplina) {
		this.preferenciasDisciplina = preferenciasDisciplina;
	}
	
	public Set<PreferenciaHorarioProfessor> getPreferenciasHorario() {
		return preferenciasHorario;
	}
	
	public void setPreferenciasHorario(Set<PreferenciaHorarioProfessor> preferenciasHorario) {
		this.preferenciasHorario = preferenciasHorario;
	}
	
	@Override
	public String toString() {
		return this.getNome();
	}
}