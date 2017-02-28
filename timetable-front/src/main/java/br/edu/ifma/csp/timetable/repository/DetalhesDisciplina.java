package br.edu.ifma.csp.timetable.repository;

import java.util.List;

import br.edu.ifma.csp.timetable.model.DetalheDisciplina;
import br.edu.ifma.csp.timetable.model.Disciplina;
import br.edu.ifma.csp.timetable.model.MatrizCurricular;

public interface DetalhesDisciplina extends Repository<DetalheDisciplina> {
	
	public DetalheDisciplina byMatrizCurricularDisciplina(MatrizCurricular matrizCurricular, Disciplina disciplina);
	
	public List<DetalheDisciplina> allByMatrizCurricular(MatrizCurricular matrizCurricular);
}
