package br.edu.ifma.csp.timetable.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="HORARIO")
public class Horario extends Entidade {
	
	private static final long serialVersionUID = 1553473334723287398L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_HORARIO")
	private int id;
	
	@Column(name="DIA", length=15)
	@Enumerated(EnumType.STRING)
	private Dia dia;
	
	@Temporal(TemporalType.TIME)
	@NotNull(message="A hora de início é obrigatória.")
	@Column(name="HORA_INICIO")
//	@Unique(message="A hora de início selecionada já está em uso.")
	private Date horaInicio;
	
	@Temporal(TemporalType.TIME)
	@NotNull(message="A hora de fim é obrigatória.")
	@Column(name="HORA_FIM")
//	@Unique(message="A hora de fim selecionada já está em uso.")
	private Date horaFim;

	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		
	}
	
	public Dia getDia() {
		return dia;
	}
	
	public void setDia(Dia dia) {
		this.dia = dia;
	}

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(Date horaFim) {
		this.horaFim = horaFim;
	}
}	