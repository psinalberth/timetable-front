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

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="LOCAL")
public class Local extends Entidade {

	private static final long serialVersionUID = 8220616165195675417L;
	
	@Id
	@Column(name="ID_LOCAL")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotBlank(message="O <b>nome</b> é obrigatório.")
	@Column(name="NOME", length=80)
	private String nome;
	
	@NotNull(message="A <b>capacidade</b> é obrigatória.")
	@Column(name="CAPACIDADE", columnDefinition="TINYINT(3)")
	private Integer capacidade;
	
	@NotNull(message="O <b>tipo de local</b> é obrigatório.")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_LOCAL")
	private TipoLocal tipoLocal;
	
	@JoinColumn(name="ID_DEPARTAMENTO")
	@ManyToOne(fetch=FetchType.LAZY)
	private Departamento departamento;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="local")
	private Set<Aula> aulas = new HashSet<Aula>();

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

	public Integer getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(Integer capacidade) {
		this.capacidade = capacidade;
	}
	
	public Departamento getDepartamento() {
		return departamento;
	}
	
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	
	public TipoLocal getTipoLocal() {
		return tipoLocal;
	}
	
	public void setTipoLocal(TipoLocal tipoLocal) {
		this.tipoLocal = tipoLocal;
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