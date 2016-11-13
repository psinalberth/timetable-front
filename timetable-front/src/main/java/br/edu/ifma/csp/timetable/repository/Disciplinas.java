package br.edu.ifma.csp.timetable.repository;

import java.util.List;

import br.edu.ifma.csp.timetable.model.Disciplina;
import br.edu.ifma.csp.timetable.model.MatrizCurricular;
import br.edu.ifma.csp.timetable.model.Professor;

public interface Disciplinas extends Repository<Disciplina> {
	
	public List<Disciplina> allByMatrizCurricular(MatrizCurricular matriz);
	
	public List<Disciplina> allByPreferenciaProfessor(Professor professor);

	List<Disciplina> allObrigatoriasByMatrizCurricular(MatrizCurricular matriz);
	
	List<Disciplina> allEletivasByMatrizCurricular(MatrizCurricular matrizCurricular);
}
