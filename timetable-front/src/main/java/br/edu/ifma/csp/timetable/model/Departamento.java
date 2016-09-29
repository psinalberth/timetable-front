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
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import br.edu.ifma.csp.timetable.annotation.Unique;

@Entity
@Table(name="DEPARTAMENTO")
public class Departamento extends Entidade {

	private static final long serialVersionUID = 1279015074878610823L;
	
	@Id
	@Column(name="ID_DEPARTAMENTO")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotBlank(message="codigo#O código é obrigatório.")
	@Column(name="CODIGO", unique=true, length=5)
	@Unique(message="codigo#O código selecionado já está em uso.")
	@Pattern(regexp="^[\\p{Alpha}]{3,5}$", message="O código deve ser alfabético de três a cinco dígitos.")
	private String codigo;
	
	@NotBlank(message="nome#O nome é obrigatório.")
	@Column(name="NOME", length=80)
	private String nome;
	
	@NotBlank(message="descricao#A descrição é obrigatória.")
	@Column(name="DESCRICAO", length=100)
	private String descricao;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="departamento")
	private Set<Professor> professores = new HashSet<Professor>();
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="departamento")
	private Set<Curso> cursos = new HashSet<Curso>();

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Set<Professor> getProfessores() {
		return professores;
	}
	
	public void setProfessores(Set<Professor> professores) {
		this.professores = professores;
	}
	
	public Set<Curso> getCursos() {
		return cursos;
	}
	
	public void setCursos(Set<Curso> cursos) {
		this.cursos = cursos;
	}
}