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

import org.hibernate.validator.constraints.NotBlank;

import br.edu.ifma.csp.timetable.annotation.Unique;

@Entity
@Table(name="DISCIPLINA")
public class Disciplina extends Entidade {

	private static final long serialVersionUID = 3496962150746723949L;
	
	@Id
	@Column(name="ID_DISCIPLINA")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Unique(message="A <b>sigla</b> selecionada já está em uso.")
	@NotBlank(message="A <b>sigla</b> é obrigatória.")
	@Column(name="SIGLA", length=10)
	private String sigla;
	
	@Unique(message="O <b>código</b> selecionado já está em uso.")
	@NotNull(message="O <b>código</b> é obrigatório.")
	@Column(name="CODIGO", columnDefinition="SMALLINT(3)")
	private Integer codigo;
	
	@NotBlank(message="descricao#A <b>descrição</b> é obrigatória.")
	@Column(name="DESCRICAO", length=100)
	private String descricao;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="disciplina")
	private Set<Aula> aulas = new HashSet<Aula>();
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="disciplina")
	private Set<PreferenciaDisciplinaProfessor> preferencias = new HashSet<PreferenciaDisciplinaProfessor>();
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="disciplina")
	private Set<DetalheDisciplina> detalhes = new HashSet<DetalheDisciplina>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getSigla() {
		return sigla;
	}
	
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	
	public void setCodigo(Integer codigo) {
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
	
	@Override
	public String toString() {
		return this.getDescricao();
	}
}