package br.edu.ifma.csp.timetable.dao;

import java.util.List;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<DetalheDisciplina> allByMatrizCurricular(MatrizCurricular matrizCurricular) {
		
		String sql = 
		
		"select * from DETALHE_DISCIPLINA where ID_DETALHE in ( " +
			"select distinct(det.ID_DETALHE) from DETALHE_DISCIPLINA det " +
				"inner join PREFERENCIA_DISCIPLINA_PROFESSOR pre on " +
					"pre.ID_DISCIPLINA = det.ID_DISCIPLINA " +
				"inner join DISCIPLINA dis on " +
					"dis.ID_DISCIPLINA = det.ID_DISCIPLINA " +
				"inner join PERIODO per on " +
					"per.ID_PERIODO = det.ID_PERIODO " +
				"inner join MATRIZ_CURRICULAR mat on " +
					"mat.ID_MATRIZ = per.ID_MATRIZ and " +
					"mat.ID_MATRIZ = :matrizCurricularId " +
			 "where " +
				"det.OBRIGATORIA = 1 " +
				"order by per.CODIGO asc)";
		
		
		return this.manager.createNativeQuery(sql, DetalheDisciplina.class)
				.setParameter("matrizCurricularId", matrizCurricular.getId()).getResultList();
	}

}
