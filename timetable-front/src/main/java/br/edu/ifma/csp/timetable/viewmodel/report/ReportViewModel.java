package br.edu.ifma.csp.timetable.viewmodel.report;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;

import br.edu.ifma.csp.timetable.dao.report.RelatorioProfessorDisciplinaDao;
import br.edu.ifma.csp.timetable.model.report.DadosRelatorioProfessorDisciplina;
import br.edu.ifma.csp.timetable.repository.report.ReportRepository;
import br.edu.ifma.csp.timetable.util.Lookup;
import br.edu.ifma.csp.timetable.util.Report;

public class ReportViewModel {
	
	@SuppressWarnings("rawtypes")
	ReportRepository repository;
	
	@Init
	public void init() {
		repository = Lookup.dao(RelatorioProfessorDisciplinaDao.class);
	}
	
	@SuppressWarnings("unchecked")
	@Command
	public void gerar() {
		
		List<DadosRelatorioProfessorDisciplina> dados = repository.recuperarDados();
		
		if (!dados.isEmpty()) {
			Report.render("rel_professor_disciplina", dados);
		}
	}
}
