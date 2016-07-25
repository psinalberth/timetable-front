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

@Entity
@Table(name="LOCAL")
public class Local extends Entidade {

	private static final long serialVersionUID = 8220616165195675417L;
	
	@Id
	@Column(name="ID_LOCAL")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotNull
	@Column(name="NOME")
	private String nome;
	
	@NotNull
	@Column(name="CAPACIDADE")
	private int capacidade;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="local")
	private Set<Aula> aulas = new HashSet<Aula>();
	
	public Local() {
	
	}

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

	public int getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}
	
	public Set<Aula> getAulas() {
		return aulas;
	}
	
	public void setAulas(Set<Aula> aulas) {
		this.aulas = aulas;
	}
	
	@Override
	public String toString() {
		return this.getNome();
	}
}