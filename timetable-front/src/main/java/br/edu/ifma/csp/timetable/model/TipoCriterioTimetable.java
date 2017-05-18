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
@Table(name="TIPO_CRITERIO_TIMETABLE")
public class TipoCriterioTimetable extends Entidade {
	
	private static final long serialVersionUID = 3463916789921375296L;
	
	public static final int DISCIPLINA_LECIONADA = 1;
	public static final int DISCIPLINA_NAO_LECIONADA = 2;
	public static final int DISCIPLINA_ELETIVA = 3;
	public static final int HORARIO_DE_INICIO_APOS = 4;
	public static final int HORARIO_DE_INICIO_ATE = 5;
	public static final int OFERTA_SEMANAL_UNICA = 6;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_TIPO_CRITERIO_TIMETABLE")
	private int id;
	
	@NotBlank
	@Column(name="DESCRICAO", length=45)
	private String descricao;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_DETALHE_TIMETABLE")
	private TipoDetalheTimetable tipoDetalheTimetable;
	
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
	
	public TipoDetalheTimetable getTipoDetalheTimetable() {
		return tipoDetalheTimetable;
	}
	
	public void setTipoDetalheTimetable(TipoDetalheTimetable tipoDetalheTimetable) {
		this.tipoDetalheTimetable = tipoDetalheTimetable;
	}
}
