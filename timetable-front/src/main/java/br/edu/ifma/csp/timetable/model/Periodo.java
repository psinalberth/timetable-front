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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="PERIODO")
public class Periodo extends Entidade {

	private static final long serialVersionUID = -1031109365878584073L;
	
	@Id
	@Column(name="ID_PERIODO")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotNull(message="O código é obrigatório.")
	@Column(name="CODIGO", columnDefinition="TINYINT(2)")
	private Integer codigo;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_MATRIZ")
	private MatrizCurricular matrizCurricular;
	
	@Valid
	@OneToMany(fetch=FetchType.LAZY, mappedBy="periodo", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<DetalheDisciplina> detalhes = new ArrayList<DetalheDisciplina>();
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="periodo", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Turma> turmas = new ArrayList<Turma>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
	public MatrizCurricular getMatrizCurricular() {
		return matrizCurricular;
	}
	
	public void setMatrizCurricular(MatrizCurricular matrizCurricular) {
		this.matrizCurricular = matrizCurricular;
	}

	public List<DetalheDisciplina> getDetalhes() {
		return detalhes;
	}
	
	public void setDetalhes(List<DetalheDisciplina> detalhes) {
		this.detalhes = detalhes;
	}
	
	public List<Turma> getTurmas() {
		return turmas;
	}
	
	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}
}