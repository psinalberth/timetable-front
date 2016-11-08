package br.edu.ifma.csp.timetable.dao;

import java.util.List;

import javax.ejb.Stateless;

import br.edu.ifma.csp.timetable.model.Disciplina;
import br.edu.ifma.csp.timetable.model.MatrizCurricular;
import br.edu.ifma.csp.timetable.model.Professor;
import br.edu.ifma.csp.timetable.repository.Disciplinas;

@Stateless
public class DisciplinaDao extends RepositoryDao<Disciplina> implements Disciplinas {

	@SuppressWarnings("unchecked")
	@Override
	public List<Disciplina> allObrigatoriasByMatrizCurricular(MatrizCurricular matriz) {
		
		String sql = 
				
		"select dis.*, per.CODIGO, det.CREDITOS from DISCIPLINA dis " +
			"inner join DETALHE_DISCIPLINA det on " +
				"det.ID_DISCIPLINA = dis.ID_DISCIPLINA and " +
		        "det.OBRIGATORIA = 1 " +
			"inner join PERIODO per on " +
				"per.ID_PERIODO = det.ID_PERIODO " +
			"inner join MATRIZ_CURRICULAR mat on " +
				"mat.ID_MATRIZ = per.ID_MATRIZ and " +
			    "mat.ID_MATRIZ = :matrizId";
		
		return this.manager.createNativeQuery(sql, Disciplina.class).setParameter("matrizId", matriz.getId()).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Disciplina> allByPreferenciaProfessor(Professor professor) {
		
		String sql = 
		
		"select dis.* from DISCIPLINA dis " +
			"inner join PREFERENCIA_DISCIPLINA_PROFESSOR pre on " +
				"pre.ID_DISCIPLINA = dis.ID_DISCIPLINA and " +
		        "pre.ID_PROFESSOR = :professorId " +
		"order by dis.CODIGO";
		
		return this.manager.createNativeQuery(sql, Disciplina.class).setParameter("professorId", professor.getId()).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Disciplina> allByMatrizCurricular(MatrizCurricular matriz) {
		
		String sql = 
				
		"select dis.*, per.CODIGO, det.CREDITOS from DISCIPLINA dis " +
			"inner join DETALHE_DISCIPLINA det on " +
				"det.ID_DISCIPLINA = dis.ID_DISCIPLINA " +
			"inner join PERIODO per on " +
				"per.ID_PERIODO = det.ID_PERIODO " +
			"inner join MATRIZ_CURRICULAR mat on " +
				"mat.ID_MATRIZ = per.ID_MATRIZ and " +
			    "mat.ID_MATRIZ = :matrizId";
		
		return this.manager.createNativeQuery(sql, Disciplina.class).setParameter("matrizId", matriz.getId()).getResultList();
	}
}
