package br.edu.ifma.csp.timetable.dao;

import java.util.List;

import javax.ejb.Stateless;

import br.edu.ifma.csp.timetable.model.Disciplina;
import br.edu.ifma.csp.timetable.model.MatrizCurricular;
import br.edu.ifma.csp.timetable.model.Professor;
import br.edu.ifma.csp.timetable.repository.Professores;

@Stateless
public class ProfessorDao extends RepositoryDao<Professor> implements Professores {
	
	@SuppressWarnings("unchecked")
	public List<Professor> all() {
		
		String sql = 
				
		"select pro.* from PROFESSOR pro " +
			"inner join PREFERENCIA_DISCIPLINA_PROFESSOR pre on " + 
				"pre.ID_PROFESSOR = pro.ID_PROFESSOR " +
		"group by pro.ID_PROFESSOR " +
		"order by count(pre.ID_DISCIPLINA) asc";
		
		return this.manager.createNativeQuery(sql, Professor.class).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Professor> allByMatrizCurricular(MatrizCurricular matriz) {
		
		String sql = 
		
		"select * from PROFESSOR where ID_PROFESSOR in ( " +
			"select distinct(pro.ID_PROFESSOR) from PROFESSOR pro " +     
			"inner join PREFERENCIA_DISCIPLINA_PROFESSOR pre on " +   
				"pre.ID_PROFESSOR = pro.ID_PROFESSOR " +
			"inner join DETALHE_DISCIPLINA det on " +        
				"det.ID_DISCIPLINA = pre.ID_DISCIPLINA and " +
				"det.OBRIGATORIA = 1 " +
			"inner join PERIODO per on " +        
				"per.ID_PERIODO = det.ID_PERIODO " +     
			"inner join MATRIZ_CURRICULAR mat on " + 
				"mat.ID_MATRIZ = per.ID_MATRIZ and " +              
	            "mat.ID_MATRIZ = :matrizId)";
		
		return this.manager.createNativeQuery(sql, Professor.class).setParameter("matrizId", matriz.getId()).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Professor> allByPreferenciaDisciplina(Disciplina disciplina) {
		
		String sql = 
				
		"select pro.* from PROFESSOR pro " +
			"inner join PREFERENCIA_DISCIPLINA_PROFESSOR pre on " +
				"pre.ID_PROFESSOR = pro.ID_PROFESSOR and " +
		        "pre.ID_DISCIPLINA = :disciplinaId ";
		
		return this.manager.createNativeQuery(sql, Professor.class).setParameter("disciplinaId", disciplina.getId()).getResultList();
	}
}
