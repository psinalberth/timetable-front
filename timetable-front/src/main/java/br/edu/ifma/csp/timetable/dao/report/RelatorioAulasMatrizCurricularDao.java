package br.edu.ifma.csp.timetable.dao.report;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.edu.ifma.csp.timetable.model.report.DadosRelatorioAulasMatrizCurricular;
import br.edu.ifma.csp.timetable.repository.report.ReportRepository;

@Stateless
public class RelatorioAulasMatrizCurricularDao implements ReportRepository<DadosRelatorioAulasMatrizCurricular> {
	
	@PersistenceContext(unitName="timetable-front")
	protected EntityManager manager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DadosRelatorioAulasMatrizCurricular> recuperarDados() {
		
		String sql = 
				
		"select " +
			"aula.PERIODO periodo, hor.HORA_INICIO horario, " +
		    "GROUP_CONCAT((case when hor.DIA = 'SEGUNDA' then CONCAT(dis.SIGLA, '\n', loc.NOME) else null end)) segunda, " +
		    "GROUP_CONCAT((case when hor.DIA = 'TERCA' then CONCAT(dis.SIGLA, '\n', loc.NOME) else null end)) terca, " +
		    "GROUP_CONCAT((case when hor.DIA = 'QUARTA' then CONCAT(dis.SIGLA, '\n', loc.NOME) else null end)) quarta, " +
		    "GROUP_CONCAT((case when hor.DIA = 'QUINTA' then CONCAT(dis.SIGLA, '\n', loc.NOME) else null end)) quinta, " +
		    "GROUP_CONCAT((case when hor.DIA = 'SEXTA' then CONCAT(dis.SIGLA, '\n', loc.NOME) else null end)) sexta " +
		"from AULA aula " +
			"inner join DISCIPLINA dis on " +
				"dis.ID_DISCIPLINA = aula.ID_DISCIPLINA " +
			"inner join LOCAL loc on " +
				"loc.ID_LOCAL = aula.ID_LOCAL " +
			"inner join HORARIO hor on " +
				"hor.ID_HORARIO = aula.ID_HORARIO " +
		"group by " +
			"aula.PERIODO, hor.HORA_INICIO " +
		"order by " +
			"aula.PERIODO, hor.HORA_INICIO asc";
		
		List<Object[]> result = manager.createNativeQuery(sql).getResultList();
		
		List<DadosRelatorioAulasMatrizCurricular> dados = new ArrayList<DadosRelatorioAulasMatrizCurricular>(result.size());
		
		for (Object[] obj : result) {
			
			DadosRelatorioAulasMatrizCurricular dado = new DadosRelatorioAulasMatrizCurricular();
			
			dado.setPeriodo(new Integer(String.valueOf(obj[0])));
			dado.setHorario((java.util.Date) obj[1]);
			dado.setSegunda(!String.valueOf(obj[2]).equals("null") ? String.valueOf(obj[2]) : null);
			dado.setTerca(!String.valueOf(obj[3]).equals("null") ? String.valueOf(obj[3]): null);
			dado.setQuarta(!String.valueOf(obj[4]).equals("null") ? String.valueOf(obj[4]) : null);
			dado.setQuinta(!String.valueOf(obj[5]).equals("null") ? String.valueOf(obj[5]) : null);
			dado.setSexta(!String.valueOf(obj[6]).equals("null") ? String.valueOf(obj[6]) : null);
			
			dados.add(dado);
		}
		
		return dados;
	}
}
