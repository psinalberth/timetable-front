package br.edu.ifma.csp.timetable.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	
	@NotBlank(message="O <b>nome</b> é obrigatório.")
	@Column(name="NOME", length=100)
	private String nome;
	
	@NotBlank(message="O <b>endereço</b> é obrigatório.")
	@Column(name="ENDERECO", length=140)
	private String endereco;
	
	@NotNull(message="O <b>departamento</b> é obrigatório.")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DEPARTAMENTO")
	private Departamento departamento;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="professor")
	private Set<Aula> aulas = new HashSet<Aula>();
	
	@Valid
	@Size(min=1, message="Nenhuma <b>preferência</b> adicionada.")
	@OneToMany(fetch=FetchType.LAZY, mappedBy="professor", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<PreferenciaDisciplinaProfessor> preferenciasDisciplina = new ArrayList<PreferenciaDisciplinaProfessor>();
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(name="HORARIO_INDISPONIVEL_PROFESSOR", joinColumns = {@JoinColumn(name="ID_PROFESSOR")}, inverseJoinColumns = {@JoinColumn(name="ID_HORARIO")})
	private List<Horario> horariosIndisponiveis = new ArrayList<Horario>();

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
	
	public List<PreferenciaDisciplinaProfessor> getPreferenciasDisciplina() {
		return preferenciasDisciplina;
	}
	
	public void setPreferenciasDisciplina(List<PreferenciaDisciplinaProfessor> preferenciasDisciplina) {
		this.preferenciasDisciplina = preferenciasDisciplina;
	}
	
	public List<Horario> getHorariosIndisponiveis() {
		return horariosIndisponiveis;
	}

	public void setHorariosIndisponiveis(List<Horario> horariosIndisponiveis) {
		this.horariosIndisponiveis = horariosIndisponiveis;
	}
	
	@Override
	public String toString() {
		return this.getNome();
	}
}