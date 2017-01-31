package br.edu.ifma.csp.timetable.dao.report;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.edu.ifma.csp.timetable.model.report.DadosRelatorioProfessorHorario;
import br.edu.ifma.csp.timetable.repository.report.ReportRepository;

@Stateless
public class RelatorioProfessorRestricoesDao implements ReportRepository<DadosRelatorioProfessorHorario> {
	
	@PersistenceContext(unitName="timetable-front")
	protected EntityManager manager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DadosRelatorioProfessorHorario> recuperarDados() {
		
		String sql = 
				
		"select " +
			"prof.NOME professor, dep.NOME departamento, " + 
			"(case " +
				"when hor.DIA = 'SEGUNDA' then 0 " +
				"when hor.DIA = 'TERCA' then 1 " +
				"when hor.DIA = 'QUARTA' then 2 " +
				"when hor.DIA = 'QUINTA' then 3 " +
				"when hor.DIA = 'SEXTA' then 4 " +
			"end) indice, " +
			"hor.HORA_INICIO horario " +
		"from PROFESSOR prof " +
			"inner join DEPARTAMENTO dep on " +
				"dep.ID_DEPARTAMENTO = prof.ID_DEPARTAMENTO " +
			"inner join PREFERENCIA_HORARIO_PROFESSOR pref on " +	
				"pref.ID_PROFESSOR = prof.ID_PROFESSOR " +
			"inner join HORARIO hor on " +
				"hor.ID_HORARIO = pref.ID_HORARIO " +
		"order by " +
			"prof.NOME, HORA_INICIO, field(DIA, 'SEGUNDA', 'TERCA', 'QUARTA', 'QUINTA', 'SEXTA') asc";
		
		List<Object[]> rows = manager.createNativeQuery(sql).getResultList();
		List<DadosRelatorioProfessorHorario> dados = new ArrayList<DadosRelatorioProfessorHorario>(rows.size());
		
		return dados;
	}

}
