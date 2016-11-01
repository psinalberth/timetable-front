package br.edu.ifma.csp.timetable.repository;

import java.util.Date;
import java.util.List;

import br.edu.ifma.csp.timetable.model.Horario;

public interface Horarios extends Repository<Horario> {
	
	public List<Date> allHorariosInicio();
}
