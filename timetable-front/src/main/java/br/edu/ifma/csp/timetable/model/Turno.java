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
@Table(name="TURNO")
@Unique(columnName="codigo")
public class Turno extends Entidade {

	private static final long serialVersionUID = 1739895240053862613L;
	
	@Id
	@Column(name="ID_TURNO")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotBlank(message="codigo#O código é obrigatório.")
	@Column(name="CODIGO", unique=true, length=10)
	private String codigo;
	
	@NotBlank(message="nome#O nome é obrigatório.")
	@Column(name="NOME", length=40)
	private String nome;
	
	@NotBlank(message="descricao#A descrição é obrigatória.")
	@Column(name="DESCRICAO", length=80)
	private String descricao;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="turno")
	private Set<MatrizCurricular> matrizes = new HashSet<MatrizCurricular>();

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
}
