package br.edu.ifma.csp.timetable.repository;

import java.util.List;

import br.edu.ifma.csp.timetable.model.Disciplina;
import br.edu.ifma.csp.timetable.model.MatrizCurricular;
import br.edu.ifma.csp.timetable.model.Professor;

public interface Disciplinas extends Repository<Disciplina> {
	
	/**
	 * Recupera todas as disciplinas pertencentes a determinada matriz curricular.
	 * 
	 * @param matriz Matriz curricular utilizada como critério de pesquisa.
	 * 
	 * @return Retorna o conjunto de disciplina pertencentes à matriz curricular ou {@code null} caso
	 * não existem disciplinas relacionadas à matriz curricular.
	 */
	public List<Disciplina> allByMatrizCurricular(MatrizCurricular matriz);
	
	/**
	 * Recupera todas as disciplinas lecionáveis por um professor.
	 * 
	 * @param professor Professor utilizado como critério de pesquisa.
	 * 
	 * @return Retorna o conjunto de disciplina lecionáveis por um docente ou {@code null} caso
	 * não existem disciplinas para o referido docente.
	 */
	public List<Disciplina> allByPreferenciaProfessor(Professor professor);
	
	/**
	 * Recupera todas as disciplinas obrigatórias pertencentes a determinada matriz curricular.
	 * 
	 * @param matriz Matriz curricular utilizada como critério de pesquisa.
	 * 
	 * @return Retorna o conjunto de disciplina obrigatórias pertencentes à matriz curricular ou 
	 * {@code null} caso não existem disciplinas relacionadas à matriz curricular.
	 */
	public List<Disciplina> allObrigatoriasByMatrizCurricular(MatrizCurricular matriz);
	
	/**
	 * Recupera todas as disciplinas eletivas pertencentes a determinada matriz curricular.
	 * 
	 * @param matriz Matriz curricular utilizada como critério de pesquisa.
	 * 
	 * @return Retorna o conjunto de disciplina eletivas pertencentes à matriz curricular ou 
	 * {@code null} caso não existem disciplinas relacionadas à matriz curricular.
	 */
	public List<Disciplina> allEletivasByMatrizCurricular(MatrizCurricular matrizCurricular);
}
