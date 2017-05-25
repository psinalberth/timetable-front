package br.edu.ifma.csp.timetable.viewmodel.report;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Messagebox;

import br.edu.ifma.csp.timetable.dao.CursoDao;
import br.edu.ifma.csp.timetable.dao.DisciplinaDao;
import br.edu.ifma.csp.timetable.dao.ProfessorDao;
import br.edu.ifma.csp.timetable.dao.TipoRelatorioDao;
import br.edu.ifma.csp.timetable.dao.report.RelatorioProfessorDisciplinaDao;
import br.edu.ifma.csp.timetable.model.Curso;
import br.edu.ifma.csp.timetable.model.Disciplina;
import br.edu.ifma.csp.timetable.model.Professor;
import br.edu.ifma.csp.timetable.model.TipoRelatorio;
import br.edu.ifma.csp.timetable.model.report.DadosRelatorioProfessorDisciplina;
import br.edu.ifma.csp.timetable.repository.Cursos;
import br.edu.ifma.csp.timetable.repository.Disciplinas;
import br.edu.ifma.csp.timetable.repository.Professores;
import br.edu.ifma.csp.timetable.repository.TiposRelatorio;
import br.edu.ifma.csp.timetable.repository.report.ReportRepository;
import br.edu.ifma.csp.timetable.util.Lookup;
import br.edu.ifma.csp.timetable.util.Report;

public class ReportViewModel {
	
	@SuppressWarnings("rawtypes")
	ReportRepository repository;
	
	private final TiposRelatorio tiposRelatorio = Lookup.dao(TipoRelatorioDao.class);
	private final Professores professores = Lookup.dao(ProfessorDao.class);
	private final Disciplinas disciplinas = Lookup.dao(DisciplinaDao.class);
	private final Cursos cursos = Lookup.dao(CursoDao.class);
	
	private List<TipoRelatorio> colTiposRelatorio;
	private List<Professor> colProfessores;
	private List<Disciplina> colDisciplinas;
	private List<Curso> colCursos;
	
	private Professor professor;
	private Disciplina disciplina;
	private Curso curso;
	
	@NotifyChange({"colTiposRelatorio", "colProfessores", "colDisciplinas", "colCursos"})
	@Init
	public void init() {
		
		repository = Lookup.dao(RelatorioProfessorDisciplinaDao.class);
		
		setColTiposRelatorio(tiposRelatorio.all());
		setColProfessores(professores.all());
		setColDisciplinas(disciplinas.all());
		setColCursos(cursos.all());
	}
	
	@SuppressWarnings("unchecked")
	@Command
	@NotifyChange("arquivo")
	public void gerar() {
		
		List<DadosRelatorioProfessorDisciplina> dados = repository.recuperarDados();
		
		
		if (!dados.isEmpty()) {
			Report.render("rel_professor_disciplina", dados);
			
		} else {
			Messagebox.show("Nenhuma informação encontrada para este relatório", "Timetable", Messagebox.OK, Messagebox.INFORMATION);
		}
	}

	public List<TipoRelatorio> getColTiposRelatorio() {
		return colTiposRelatorio;
	}

	public void setColTiposRelatorio(List<TipoRelatorio> colTiposRelatorio) {
		this.colTiposRelatorio = colTiposRelatorio;
	}

	public List<Professor> getColProfessores() {
		return colProfessores;
	}

	public void setColProfessores(List<Professor> colProfessores) {
		this.colProfessores = colProfessores;
	}

	public List<Disciplina> getColDisciplinas() {
		return colDisciplinas;
	}

	public void setColDisciplinas(List<Disciplina> colDisciplinas) {
		this.colDisciplinas = colDisciplinas;
	}

	public List<Curso> getColCursos() {
		return colCursos;
	}

	public void setColCursos(List<Curso> colCursos) {
		this.colCursos = colCursos;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
}