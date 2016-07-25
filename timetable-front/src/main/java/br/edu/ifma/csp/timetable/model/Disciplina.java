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
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="DISCIPLINA")
public class Disciplina extends Entidade {

	private static final long serialVersionUID = 3496962150746723949L;
	
	@Id
	@Column(name="ID_DISCIPLINA")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotNull
	@Column(name="CODIGO")
	private String codigo;
	
	@NotNull
	@Column(name="DESCRICAO")
	private String descricao;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="disciplina")
	private Set<Aula> aulas = new HashSet<Aula>();
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="disciplina")
	private Set<PreferenciaDisciplinaProfessor> preferencias = new HashSet<PreferenciaDisciplinaProfessor>();
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="disciplina")
	private Set<DetalheDisciplina> detalhes = new HashSet<DetalheDisciplina>();
	
	@Transient
	private transient int cargaHoraria;
	
	@Transient
	private transient int creditos;
	
	public Disciplina() {
	
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
	
	public Set<DetalheDisciplina> getDetalhes() {
		return detalhes;
	}
	
	public void setDetalhes(Set<DetalheDisciplina> detalhes) {
		this.detalhes = detalhes;
	}
	
	public int getCargaHoraria() {
		return cargaHoraria;
	}
	
	public void setCargaHoraria(int cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}
	
	public int getCreditos() {
		return creditos;
	}
	
	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}
	
	@Override
	public String toString() {
		return this.getDescricao();
	}
	
	@PostLoad
	public void postLoad() {
		
	}
}