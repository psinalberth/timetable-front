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
@Table(name="DETALHE_TIMETABLE")
public class DetalheTimetable extends Entidade {

	private static final long serialVersionUID = 5810683509162326702L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_DETALHE")
	private int id;
	
	private String entidade;

	private Professor professor;
	
	private String criterio;
	
	private Disciplina disciplina;
	
	private Local local;
	
	private Periodo periodo;
	
	private Horario horario;
	
	private String horarioInicio;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIMETABLE")
	private Timetable timetable;

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}
	
	public String getEntidade() {
		return entidade;
	}

	public void setEntidade(String entidade) {
		this.entidade = entidade;
		setCriterio(null);
		setPeriodo(null);
		setDisciplina(null);
		setLocal(null);
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
		setProfessor(null);
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public Horario getHorario() {
		return horario;
	}

	public void setHorario(Horario horario) {
		this.horario = horario;
	}
	
	public Timetable getTimetable() {
		return timetable;
	}
	
	public void setTimetable(Timetable timetable) {
		this.timetable = timetable;
	}
	
	public String getCriterio() {
		return criterio;
	}
	
	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}
	
	public String getHorarioInicio() {
		return horarioInicio;
	}
	
	public void setHorarioInicio(String horarioInicio) {
		this.horarioInicio = horarioInicio;
	}
	
	public boolean isCriterioDisciplina() {
		return entidade != null && entidade.equalsIgnoreCase("Disciplina");
	}
	
	public boolean isCriterioProfessor() {
		return entidade != null && entidade.equalsIgnoreCase("Professor");
	}
	
	public boolean isCriterioPeriodo() {
		return entidade != null && entidade.equalsIgnoreCase("Período");
	}
	
	public boolean isCriterioPeriodoHorario() {
		return isCriterioPeriodo() && criterio != null && criterio.contains("Horários");
	}
	
	public boolean isCriterioPeriodoEletiva() {
		return isCriterioPeriodo() && criterio != null && criterio.equalsIgnoreCase("Eletiva");
	}
	
	public boolean isCriterioLocal() {
		return entidade != null && entidade.equalsIgnoreCase("Local");
	}
}