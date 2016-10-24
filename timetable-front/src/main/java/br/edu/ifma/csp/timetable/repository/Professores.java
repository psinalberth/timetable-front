package br.edu.ifma.csp.timetable.repository;

import java.util.List;

import br.edu.ifma.csp.timetable.model.Disciplina;
import br.edu.ifma.csp.timetable.model.MatrizCurricular;
import br.edu.ifma.csp.timetable.model.Professor;

public interface Professores extends Repository<Professor> {
	
	public List<Professor> allByMatrizCurricular(MatrizCurricular matriz);
	
	public List<Professor> allByPreferenciaDisciplina(Disciplina disciplina);
}
