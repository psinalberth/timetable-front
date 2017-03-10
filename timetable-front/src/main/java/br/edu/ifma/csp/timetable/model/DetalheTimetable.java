package br.edu.ifma.csp.timetable.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="DETALHE_TIMETABLE")
public class DetalheTimetable extends Entidade {

	private static final long serialVersionUID = 5810683509162326702L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_DETALHE")
	private int id;
	
	@NotNull(message="O <b>tipo de detalhe</b> é obrigatório.")
	@JoinColumn(name="ID_TIPO_DETALHE_TIMETABLE")
	@ManyToOne(fetch=FetchType.LAZY)
	private TipoDetalheTimetable tipoDetalheTimetable;
	
	@NotNull
	@JoinColumn(name="ID_TIPO_CRITERIO_TIMETABLE")
	@ManyToOne(fetch=FetchType.LAZY)
	private TipoCriterioTimetable tipoCriterioTimetable;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PROFESSOR")
	private Professor professor;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DISCIPLINA")
	private Disciplina disciplina;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_LOCAL")
	private Local local;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PERIODO")
	private Periodo periodo;
	
	@Temporal(TemporalType.TIME)
	@Column(name="HORARIO")
	private Date horario;
	
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
	
	public TipoDetalheTimetable getTipoDetalheTimetable() {
		return tipoDetalheTimetable;
	}
	
	public void setTipoDetalheTimetable(TipoDetalheTimetable tipoDetalheTimetable) {
		this.tipoDetalheTimetable = tipoDetalheTimetable;
		setTipoCriterioTimetable(null);
	}
	
	public TipoCriterioTimetable getTipoCriterioTimetable() {
		return tipoCriterioTimetable;
	}
	
	public void setTipoCriterioTimetable(TipoCriterioTimetable tipoCriterioTimetable) {
		this.tipoCriterioTimetable = tipoCriterioTimetable;
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

	public Date getHorario() {
		return horario;
	}

	public void setHorario(Date horario) {
		this.horario = horario;
	}
	
	public Timetable getTimetable() {
		return timetable;
	}
	
	public void setTimetable(Timetable timetable) {
		this.timetable = timetable;
	}
	
	public boolean isTipoDetalheDisciplina() {
		
		if (tipoDetalheTimetable == null)
			return true;
		
		return (tipoDetalheTimetable.getId() == TipoDetalheTimetable.DISCIPLINA);
	}
	
	public boolean isTipoDetalhePeriodo() {
		
		if (tipoDetalheTimetable == null)
			return true;
		
		return (tipoDetalheTimetable.getId() == TipoDetalheTimetable.PERIODO);
	}
	
	public boolean isTipoCriterioProfessor() {
		return isTipoDetalheDisciplina() && tipoCriterioTimetable != null && 
			   (tipoCriterioTimetable.getId() == TipoCriterioTimetable.DISCIPLINA_LECIONADA || 
			    tipoCriterioTimetable.getId() == TipoCriterioTimetable.DISCIPLINA_NAO_LECIONADA);
	}
	
	public boolean isTipoCriterioEletiva() {
		return isTipoDetalhePeriodo() && tipoCriterioTimetable != null && tipoCriterioTimetable.getId() == TipoCriterioTimetable.DISCIPLINA_ELETIVA;
	}
	
	public boolean isTipoCriterioHorario() {
		return isTipoDetalhePeriodo() && tipoCriterioTimetable != null &&
			   (tipoCriterioTimetable.getId() == TipoCriterioTimetable.HORARIO_DE_INICIO_APOS || 
				tipoCriterioTimetable.getId() == TipoCriterioTimetable.HORARIO_DE_INICIO_ATE);
	}
	
	@AssertTrue(message="O <b>critério</b> é obrigatório.")
	public boolean isTipoCriterioSelecionado() {
		return tipoDetalheTimetable != null && tipoCriterioTimetable != null;
	}
	
	@AssertTrue(message="O <b>professor</b> é obrigatório.")
	public boolean isProfessorSelecionado() {
		
		if (tipoCriterioTimetable == null)
			return true;
		
		return isTipoCriterioProfessor() && professor != null;
	}
	
	@AssertTrue(message="A <b>disciplina</b> é obrigatória.")
	public boolean isDisciplinaSelecionada() {
		
		if (tipoCriterioTimetable == null)
			return true;
		
		return isTipoCriterioEletiva() && disciplina != null;
	}
	
	@AssertTrue(message="O <b>horário</b> é obrigatório.")
	public boolean isHorarioSelecionado() {
		
		if (tipoCriterioTimetable == null)
			return true;
		
		return isTipoCriterioHorario() && horario != null;
	}
}