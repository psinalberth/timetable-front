package br.edu.ifma.csp.timetable.dao.report;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.edu.ifma.csp.timetable.model.report.DadosRelatorioProfessorDisciplina;
import br.edu.ifma.csp.timetable.repository.report.ReportRepository;

@Stateless
public class RelatorioProfessorDisciplinaDao implements ReportRepository<DadosRelatorioProfessorDisciplina> {
	
	@PersistenceContext(unitName="timetable-front")
	protected EntityManager manager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DadosRelatorioProfessorDisciplina> recuperarDados() {
		
		String sql = 
				
		"select CONCAT('SI', LPAD(dis.CODIGO, 3, '0'),' - ', dis.DESCRICAO) disciplina, prof.NOME professor, dep.NOME departamento " + 
			"from DISCIPLINA dis " +
				"inner join PREFERENCIA_DISCIPLINA_PROFESSOR pref on " +
					"pref.ID_DISCIPLINA = dis.ID_DISCIPLINA " +
				"inner join PROFESSOR prof on " +
					"prof.ID_PROFESSOR = pref.ID_PROFESSOR " +
				"inner join DEPARTAMENTO dep on " +
					"dep.ID_DEPARTAMENTO = prof.ID_DEPARTAMENTO " +
				"inner join CURSO cur on " +
					"cur.ID_DEPARTAMENTO = dep.ID_DEPARTAMENTO " +
				"inner join MATRIZ_CURRICULAR mat on " +
					"mat.ID_CURSO = cur.ID_CURSO " +
				"inner join PERIODO per on " +
					"per.ID_MATRIZ = mat.ID_MATRIZ " +
				"inner join DETALHE_DISCIPLINA det on " +
					"det.ID_PERIODO = per.ID_PERIODO and " +
		            "det.ID_DISCIPLINA = dis.ID_DISCIPLINA " +
			"order by per.CODIGO, dis.CODIGO, prof.NOME asc";
		
		List<Object[]> result = manager.createNativeQuery(sql).getResultList();
		
		List<DadosRelatorioProfessorDisciplina> dados = new ArrayList<DadosRelatorioProfessorDisciplina>(result.size());
		
		for (Object[] obj : result) {
			
			DadosRelatorioProfessorDisciplina dado = new DadosRelatorioProfessorDisciplina();
			
			dado.setDisciplina(String.valueOf(obj[0]));
			dado.setProfessor(String.valueOf(obj[1]));
			dado.setDepartamento(String.valueOf(obj[2]));
			
			dados.add(dado);
		}
		
		return dados;
	}

}
