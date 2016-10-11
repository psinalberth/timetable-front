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

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="DETALHE_DISCIPLINA")
public class DetalheDisciplina extends Entidade {

	private static final long serialVersionUID = -1273536257865814586L;
	
	@Id
	@Column(name="ID_DETALHE")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotBlank(message="O código é obrigatório.")
	@Column(name="CODIGO", length=3)
	private String codigo;
	
	@NotNull(message="Os créditos são obrigatórios.")
	@Column(name="CREDITOS", columnDefinition="TINYINT(2)")
	private int creditos;
	
	@NotNull(message="A carga horária é obrigatória.")
	@Column(name="CARGA_HORARIA", columnDefinition="TINYINT(3)")
	private int cargaHoraria;
	
	@NotNull
	@Column(name="OBRIGATORIA")
	private boolean obrigatoria;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PERIODO")
	private Periodo periodo;
	
	@NotNull(message="A disciplina é obrigatória.")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DISCIPLINA")
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

	public boolean isObrigatoria() {
		return obrigatoria;
	}

	public void setObrigatoria(boolean obrigatoria) {
		this.obrigatoria = obrigatoria;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}