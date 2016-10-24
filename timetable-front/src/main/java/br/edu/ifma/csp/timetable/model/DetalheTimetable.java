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
	
	public Timetable getTimetable() {
		return timetable;
	}
	
	public void setTimetable(Timetable timetable) {
		this.timetable = timetable;
	}
}