package br.edu.ifma.csp.timetable.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import br.edu.ifma.csp.timetable.model.Horario;
import br.edu.ifma.csp.timetable.repository.Horarios;

@Stateless
public class HorarioDao extends RepositoryDao<Horario> implements Horarios {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Horario> all() {
		
		String sql = 
		
		"select * from HORARIO order by field(DIA, 'SEGUNDA', 'TERCA', 'QUARTA', 'QUINTA', 'SEXTA'), HORA_INICIO";
		
		return this.manager.createNativeQuery(sql, Horario.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Date> allHorariosInicio()  {
		
		List<Date> dates = new ArrayList<Date>();
		
		String sql = "select * from HORARIO where DIA = 'SEGUNDA' order by HORA_INICIO asc";
		
		List<Horario> horarios = this.manager.createNativeQuery(sql, Horario.class).getResultList();
		
		for (Horario horario : horarios) {
			dates.add(horario.getHoraInicio());
		}
		
		return dates;
	}
}
