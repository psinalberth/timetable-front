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

import org.hibernate.validator.constraints.NotBlank;

import br.edu.ifma.csp.timetable.annotation.Unique;

@Entity
@Table(name="CURSO")
@Unique(columnName="codigo")
public class Curso extends Entidade {

	private static final long serialVersionUID = 2945698851298486207L;
	
	@Id
	@Column(name="ID_CURSO")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotBlank(message="codigo#O código é obrigatório.")
	@Column(name="CODIGO", unique=true)
	private String codigo;
	
	@NotBlank(message="nome#O nome é obrigatório.")
	@Column(name="NOME")
	private String nome;
	
	@NotBlank(message="descricao#A descrição é obrigatória.")
	@Column(name="DESCRICAO")
	private String descricao;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="curso")
	private Set<Turma> periodos = new HashSet<Turma>();
	
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
	
	public Set<Turma> getPeriodos() {
		return periodos;
	}
	
	public void setPeriodos(Set<Turma> periodos) {
		this.periodos = periodos;
	}
	
	public Set<MatrizCurricular> getMatrizes() {
		return matrizes;
	}
	
	public void setMatrizes(Set<MatrizCurricular> matrizes) {
		this.matrizes = matrizes;
	}
}