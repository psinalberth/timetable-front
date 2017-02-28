package br.edu.ifma.csp.timetable.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	@NotBlank(message="codigo#O <b>código</b> é obrigatório.")
	@Column(name="CODIGO", unique=true, length=5)
	@Unique(message="codigo#O código selecionado já está em uso.")
	@Pattern(regexp="^[\\p{Alpha}]{3,5}$", message="O código deve ser alfabético de três a cinco dígitos.")
	private String codigo;
	
	@NotBlank(message="nome#O <b>nome</b> é obrigatório.")
	@Column(name="NOME", length=80)
	private String nome;
	
	@NotBlank(message="descricao#A <b>descrição</b> é obrigatória.")
	@Column(name="DESCRICAO", length=100)
	private String descricao;

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