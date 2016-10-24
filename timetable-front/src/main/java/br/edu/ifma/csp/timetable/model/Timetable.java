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
@Table(name="TIMETABLE")
public class Timetable extends Entidade {

	private static final long serialVersionUID = 3209546400076397884L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_TIMETABLE")
	private int id;
	
	@NotNull(message="A matriz curricular é obrigatória.")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_MATRIZ")
	private MatrizCurricular matrizCurricular;
	
	@NotNull(message="O ano é obrigatório.")
	@Column(name="ANO", columnDefinition="SMALLINT(4)")
	private Integer ano;
	
	@NotNull(message="O semestre é obrigatório.")
	@Column(name="SEMESTRE", columnDefinition="TINYINT(1)")
	private Integer semestre;
	
	@Valid
	@OneToMany(fetch=FetchType.LAZY, mappedBy="timetable", cascade=CascadeType.ALL ,orphanRemoval=true)
	private List<Aula> aulas = new ArrayList<Aula>();
	
	@Valid
	@OneToMany(fetch=FetchType.LAZY, mappedBy="timetable", cascade=CascadeType.ALL ,orphanRemoval=true)
	private List<DetalheTimetable> detalhes = new ArrayList<DetalheTimetable>();

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public MatrizCurricular getMatrizCurricular() {
		return matrizCurricular;
	}

	public void setMatrizCurricular(MatrizCurricular matrizCurricular) {
		this.matrizCurricular = matrizCurricular;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Integer getSemestre() {
		return semestre;
	}

	public void setSemestre(Integer semestre) {
		this.semestre = semestre;
	}

	public List<Aula> getAulas() {
		return aulas;
	}

	public void setAulas(List<Aula> aulas) {
		this.aulas = aulas;
	}
	
	public List<DetalheTimetable> getDetalhes() {
		return detalhes;
	}
	
	public void setDetalhes(List<DetalheTimetable> detalhes) {
		this.detalhes = detalhes;
	}
}