package br.edu.ifma.csp.timetable.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="DETALHE_DISCIPLINA")
public class DetalheDisciplina extends Entidade {

	private static final long serialVersionUID = -1273536257865814586L;
	
	@Id
	@Column(name="ID_DETALHE")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotNull
	@Column(name="CREDITOS")
	private int creditos;
	
	@NotNull
	@Column(name="CARGA_HORARIA")
	private int cargaHoraria;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MATRIZ_ID")
	private MatrizCurricular matrizCurricular;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PERIODO_ID")
	private Periodo periodo;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DISCIPLINA_ID")
	private Disciplina disciplina;
	
	public DetalheDisciplina() {
	
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getCreditos() {
		return creditos;
	}
	
	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}
	
	public int getCargaHoraria() {
		return cargaHoraria;
	}
	
	public void setCargaHoraria(int cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}
	
	public MatrizCurricular getMatrizCurricular() {
		return matrizCurricular;
	}
	
	public void setMatrizCurricular(MatrizCurricular matrizCurricular) {
		this.matrizCurricular = matrizCurricular;
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
}