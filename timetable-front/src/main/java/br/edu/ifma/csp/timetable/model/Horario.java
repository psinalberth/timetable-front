package br.edu.ifma.csp.timetable.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="HORARIO")
public class Horario extends Entidade {
	
	private static final long serialVersionUID = 1553473334723287398L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_HORARIO")
	private int id;
	
	//private

	public int getId() {
		return id;
	}
	
}
