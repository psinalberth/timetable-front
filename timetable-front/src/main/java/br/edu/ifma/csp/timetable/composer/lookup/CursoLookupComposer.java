package br.edu.ifma.csp.timetable.composer.lookup;

import org.zkoss.bind.annotation.Init;

import br.edu.ifma.csp.timetable.dao.CursoDao;
import br.edu.ifma.csp.timetable.model.Curso;
import br.edu.ifma.csp.timetable.repository.Cursos;
import br.edu.ifma.csp.timetable.util.Lookup;

public class CursoLookupComposer extends LookupComposer<Curso> {

	private Cursos cursos;
	
	private static final long serialVersionUID = -5971563460064982966L;
	
	@Init
	public void init() {
		cursos = Lookup.dao(CursoDao.class);
		setCol(cursos.all());
		getBinder().notifyChange(this, "*");
	}

}
