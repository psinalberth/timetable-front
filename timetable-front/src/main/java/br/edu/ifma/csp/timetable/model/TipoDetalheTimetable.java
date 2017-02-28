package br.edu.ifma.csp.timetable.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="TIPO_DETALHE_TIMETABLE")
public class TipoDetalheTimetable extends Entidade {

	private static final long serialVersionUID = -657602950162529859L;
	
	public static final int DISCIPLINA = 1;
	public static final int PERIODO = 2;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_TIPO_DETALHE_TIMETABLE")
	private int id;
	
	@NotBlank
	@Column(name="DESCRICAO", length=20)
	private String descricao;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="tipoDetalheTimetable")
	private List<TipoCriterioTimetable> tiposCriterioTimetable = new ArrayList<TipoCriterioTimetable>();

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public List<TipoCriterioTimetable> getTiposCriterioTimetable() {
		return tiposCriterioTimetable;
	}
	
	public void setTiposCriterioTimetable(List<TipoCriterioTimetable> tiposCriterioTimetable) {
		this.tiposCriterioTimetable = tiposCriterioTimetable;
	}
}
