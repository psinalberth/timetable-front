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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="DETALHE_DISCIPLINA")
public class DetalheDisciplina extends Entidade {

	private static final long serialVersionUID = -1273536257865814586L;
	
	@Id
	@Column(name="ID_DETALHE")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotNull(message="Os <b>créditos</b> são obrigatórios.")
	@Column(name="CREDITOS", columnDefinition="TINYINT(2)")
	private Integer creditos;
	
	@NotNull(message="A <b>carga horária</b> é obrigatória.")
	@Column(name="CARGA_HORARIA", columnDefinition="TINYINT(3)")
	private Integer cargaHoraria;
	
	@NotNull
	@Column(name="OBRIGATORIA")
	private boolean obrigatoria = true;
	
	@Column(name="GRUPO_ELETIVA")
	private String grupoEletiva;
	
	@NotNull
	@Column(name="DISCIPLINA_LABORATORIO")
	private boolean disciplinaLaboratorio = true;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PERIODO")
	private Periodo periodo;
	
	@NotNull(message="A <b>disciplina</b> é obrigatória.")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DISCIPLINA")
	private Disciplina disciplina;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
	@JoinTable(name="PRE_REQUISITO_DISCIPLINA", joinColumns = {@JoinColumn(name="ID_DETALHE")}, inverseJoinColumns = {@JoinColumn(name="ID_DISCIPLINA")})
	private List<Disciplina> preRequisitos = new ArrayList<Disciplina>();
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Integer getCreditos() {
		return creditos;
	}
	
	public void setCreditos(Integer creditos) {
		this.creditos = creditos;
	}
	
	public Integer getCargaHoraria() {
		return cargaHoraria;
	}
	
	public void setCargaHoraria(Integer cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}
	
	public Periodo getPeriodo() {
		return periodo;
	}
	
	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}
	
	public Disciplina getDisciplina() {
		return disciplina;
	}
	
	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}
	
	public String getGrupoEletiva() {
		return grupoEletiva;
	}
	
	public void setGrupoEletiva(String grupoEletiva) {
		this.grupoEletiva = grupoEletiva;
	}

	public boolean isObrigatoria() {
		return obrigatoria;
	}

	public void setObrigatoria(boolean obrigatoria) {
		this.obrigatoria = obrigatoria;
	}
	
	public boolean isDisciplinaLaboratorio() {
		return disciplinaLaboratorio;
	}
	
	public void setDisciplinaLaboratorio(boolean disciplinaLaboratorio) {
		this.disciplinaLaboratorio = disciplinaLaboratorio;
	}
	
	@AssertFalse(message="O grupo de eletivas é obrigatório.")
	public boolean isGrupoEletivasObrigatorio() {
		return ((!isObrigatoria()) && getGrupoEletiva() == null);
	}

	public List<Disciplina> getPreRequisitos() {
		return preRequisitos;
	}

	public void setPreRequisitos(List<Disciplina> preRequisitos) {
		this.preRequisitos = preRequisitos;
	}
}