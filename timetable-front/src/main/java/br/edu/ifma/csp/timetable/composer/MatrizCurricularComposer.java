package br.edu.ifma.csp.timetable.composer;

import java.util.List;

import org.zkoss.bind.annotation.Init;

import br.edu.ifma.csp.timetable.dao.CursoDao;
import br.edu.ifma.csp.timetable.model.Curso;
import br.edu.ifma.csp.timetable.model.MatrizCurricular;
import br.edu.ifma.csp.timetable.repository.Cursos;
import br.edu.ifma.csp.timetable.util.Lookup;

public class MatrizCurricularComposer extends Composer<MatrizCurricular> {

	private static final long serialVersionUID = 20928881137239658L;
	
	private Cursos cursos;
	
	private List<Curso> colCursos;

	@Init
	public void init() {
		
		cursos = Lookup.dao(CursoDao.class);
		setColCursos(cursos.all());
		getBinder().notifyChange(this, "*");
	}
	
	public List<Curso> getColCursos() {
		return colCursos;
	}
	
	public void setColCursos(List<Curso> colCursos) {
		this.colCursos = colCursos;
	}
}
