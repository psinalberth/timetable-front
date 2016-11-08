package br.edu.ifma.csp.timetable.repository;

import br.edu.ifma.csp.timetable.model.DetalheDisciplina;
import br.edu.ifma.csp.timetable.model.Disciplina;
import br.edu.ifma.csp.timetable.model.MatrizCurricular;

public interface DetalhesDisciplina extends Repository<DetalheDisciplina> {
	
	public DetalheDisciplina byMatrizCurricularDisciplina(MatrizCurricular matrizCurricular, Disciplina disciplina);
}
