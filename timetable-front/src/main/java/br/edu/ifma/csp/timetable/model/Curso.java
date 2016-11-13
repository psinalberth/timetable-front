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
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import br.edu.ifma.csp.timetable.annotation.Unique;

@Entity
@Table(name="CURSO")
public class Curso extends Entidade {

	private static final long serialVersionUID = 2945698851298486207L;
	
	@Id
	@Column(name="ID_CURSO")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotBlank(message="codigo#O <b>código</b> é obrigatório.")
	@Column(name="CODIGO", unique=true, length=2)
	@Unique(message="codigo#O <b>código</b> selecionado já está em uso.")
	@Pattern(regexp="^[\\p{Alpha}]{2}$", message="O <b>código</b> deve ser alfabético de dois dígitos.")
	private String codigo;
	
	@NotBlank(message="nome#O <b>nome</b> é obrigatório.")
	@Column(name="NOME", length=80)
	private String nome;
	
	@NotBlank(message="descricao#A <b>descrição</b> é obrigatória.")
	@Column(name="DESCRICAO", length=100)
	private String descricao;
	
	@NotNull(message="O <b>departamento</b> é obrigatório.")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DEPARTAMENTO")
	private Departamento departamento;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="curso")
	private Set<MatrizCurricular> matrizes = new HashSet<MatrizCurricular>();

	@Override
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
	
	public Departamento getDepartamento() {
		return departamento;
	}
	
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	
	public Set<MatrizCurricular> getMatrizes() {
		return matrizes;
	}
	
	public void setMatrizes(Set<MatrizCurricular> matrizes) {
		this.matrizes = matrizes;
	}
}