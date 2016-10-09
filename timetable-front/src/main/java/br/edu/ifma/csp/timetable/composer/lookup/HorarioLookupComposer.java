package br.edu.ifma.csp.timetable.composer.lookup;

import org.zkoss.bind.annotation.Init;

import br.edu.ifma.csp.timetable.dao.HorarioDao;
import br.edu.ifma.csp.timetable.model.Horario;
import br.edu.ifma.csp.timetable.repository.Horarios;
import br.edu.ifma.csp.timetable.util.Lookup;

public class HorarioLookupComposer extends LookupComposer<Horario> {
	
	private static final long serialVersionUID = 3545573988760858925L;
	
	private Horarios horarios;
	
	@Init
	public void setup() {
		
		horarios = Lookup.dao(HorarioDao.class);
		setCol(horarios.all());
		getBinder().notifyChange(this, "*");
	}

}
