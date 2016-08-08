package br.edu.ifma.csp.timetable.composer.lookup;

import org.zkoss.bind.annotation.Init;

import br.edu.ifma.csp.timetable.dao.DisciplinaDao;
import br.edu.ifma.csp.timetable.model.Disciplina;
import br.edu.ifma.csp.timetable.repository.Disciplinas;
import br.edu.ifma.csp.timetable.util.Lookup;

public class DisciplinaLookupComposer extends LookupComposer<Disciplina> {
	
	private static final long serialVersionUID = -3212074239486472736L;
	
	private Disciplinas disciplinas;
	
	@Init
	public void init() {
		disciplinas = Lookup.dao(DisciplinaDao.class);
		setCol(disciplinas.all());
		getBinder().notifyChange(this, "*");
	}
	
	public void search() {
		
		setCol(disciplinas.all());
		getBinder().notifyChange(this, "*");
	}

}
