package br.edu.ifma.csp.timetable.handler;

import br.edu.ifma.csp.timetable.dao.HorarioDao;
import br.edu.ifma.csp.timetable.model.Dia;
import br.edu.ifma.csp.timetable.model.Horario;
import br.edu.ifma.csp.timetable.repository.Horarios;
import br.edu.ifma.csp.timetable.util.Lookup;

public class HorarioHandler {
	
	private Horarios horarios = Lookup.dao(HorarioDao.class);
	
	public void save(Horario horario) {
		
		for (Dia dia : Dia.values()) {
			
			Horario h = new Horario();
			h.setDia(dia);
			h.setHoraInicio(horario.getHoraInicio());
			h.setHoraFim(horario.getHoraFim());
			
			horarios.save(h);
		}
	}
	
	public void delete(Horario horario) {
		
	}
}
