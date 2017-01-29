package br.edu.ifma.csp.timetable.model;

import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="PERFIL")
public class Perfil extends Entidade {
	
	private static final long serialVersionUID = -9136646307254483767L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_PERFIL")
	private int id;
	
	@NotBlank
	@Column(name="NOME", length=40)
	private String nome;
	
	@NotBlank
	@Column(name="DESCRICAO", length=100)
	private String descricao;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name="PERMISSAO", joinColumns = {@JoinColumn(name="ID_PERFIL")}, inverseJoinColumns = {@JoinColumn(name="ID_TRANSACAO")})
	private List<Transacao> transacoes = new ArrayList<Transacao>();
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
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
	
	public List<Transacao> getTransacoes() {
		return transacoes;
	}
	
	public void setTransacoes(List<Transacao> transacoes) {
		this.transacoes = transacoes;
	}
}