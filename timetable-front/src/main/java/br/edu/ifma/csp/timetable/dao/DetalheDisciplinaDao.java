package br.edu.ifma.csp.timetable.dao;

import javax.ejb.Stateless;

import br.edu.ifma.csp.timetable.model.DetalheDisciplina;
import br.edu.ifma.csp.timetable.model.Disciplina;
import br.edu.ifma.csp.timetable.model.MatrizCurricular;
import br.edu.ifma.csp.timetable.repository.DetalhesDisciplina;

@Stateless
public class DetalheDisciplinaDao extends RepositoryDao<DetalheDisciplina> implements DetalhesDisciplina {

	@Override
	public DetalheDisciplina byMatrizCurricularDisciplina(MatrizCurricular matrizCurricular, Disciplina disciplina) {
		
		String sql = 
				
		"select det.* from DETALHE_DISCIPLINA det " +
			"inner join DISCIPLINA dis on " +
				"dis.ID_DISCIPLINA = det.ID_DISCIPLINA and " +
				"dis.ID_DISCIPLINA = :disciplinaId " +
			"inner join PERIODO per on " +
				"per.ID_PERIODO = det.ID_PERIODO " +
			"inner join MATRIZ_CURRICULAR mat on " +
				"mat.ID_MATRIZ = per.ID_MATRIZ and " +
				"mat.ID_MATRIZ = :matrizCurricularId";
		
		
		return (DetalheDisciplina) this.manager.createNativeQuery(sql, DetalheDisciplina.class)
				.setParameter("disciplinaId", disciplina.getId())
				.setParameter("matrizCurricularId", matrizCurricular.getId()).getSingleResult();
	}

}
