package br.edu.ifma.csp.timetable.composer;

import java.util.HashSet;
import java.util.List;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;

import br.edu.ifma.csp.timetable.dao.DepartamentoDao;
import br.edu.ifma.csp.timetable.dao.DisciplinaDao;
import br.edu.ifma.csp.timetable.model.Departamento;
import br.edu.ifma.csp.timetable.model.Disciplina;
import br.edu.ifma.csp.timetable.model.PreferenciaDisciplinaProfessor;
import br.edu.ifma.csp.timetable.model.PreferenciaHorarioProfessor;
import br.edu.ifma.csp.timetable.model.Professor;
import br.edu.ifma.csp.timetable.repository.Departamentos;
import br.edu.ifma.csp.timetable.repository.Disciplinas;
import br.edu.ifma.csp.timetable.util.Lookup;

public class ProfessorComposer extends Composer<Professor> {

	private static final long serialVersionUID = -2980484980640308048L;
	
	private Disciplinas disciplinas;
	private Departamentos departamentos;
	
	private List<Disciplina> colDisciplinas;
	private List<Departamento> colDepartamentos;
	
	private List<PreferenciaDisciplinaProfessor> preferenciasDisciplinaSelecionadas;
	private List<PreferenciaHorarioProfessor> preferenciasHorarioSelecionadas;

	@Init
	public void init() {
		
		disciplinas = Lookup.dao(DisciplinaDao.class);
		departamentos = Lookup.dao(DepartamentoDao.class);
		
		setColDisciplinas(disciplinas.all());
		setColDepartamentos(departamentos.all());
		
		this.getBinder().notifyChange(this, "*");
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		initDetalhes();
	}
	
	public void initDetalhes() {
		
		entidade.setPreferenciasDisciplina(new HashSet<PreferenciaDisciplinaProfessor>());
		
		PreferenciaDisciplinaProfessor preferencia = new PreferenciaDisciplinaProfessor();
		preferencia.setProfessor(entidade);
		
		entidade.getPreferenciasDisciplina().add(preferencia);
		
		getBinder().notifyChange(this, "*");
	}
	
	public void adicionarPreferenciaDisciplina() {
		
		PreferenciaDisciplinaProfessor preferencia = new PreferenciaDisciplinaProfessor();
		preferencia.setProfessor(entidade);
		
		entidade.getPreferenciasDisciplina().add(preferencia);
		
		getBinder().notifyChange(entidade, "preferenciasDisciplina");
	}
	
	public void removerPreferenciaDisciplina() {
		entidade.getPreferenciasDisciplina().removeAll(preferenciasDisciplinaSelecionadas);
		getBinder().notifyChange(entidade, "preferenciasDisciplina");
	}
	
	public void adicionarPreferenciaHorario() {
		
		PreferenciaHorarioProfessor preferencia = new PreferenciaHorarioProfessor();
		preferencia.setProfessor(entidade);
		
		entidade.getPreferenciasHorario().add(preferencia);
		
		getBinder().notifyChange(entidade, "preferenciasHorario");
	}
	
	public void removerPreferenciaHorario() {
		entidade.getPreferenciasHorario().removeAll(preferenciasHorarioSelecionadas);
		getBinder().notifyChange(entidade, "preferenciasHorario");
	}
	
	public List<Disciplina> getColDisciplinas() {
		return colDisciplinas;
	}
	
	public void setColDisciplinas(List<Disciplina> colDisciplinas) {
		this.colDisciplinas = colDisciplinas;
	}
	
	public List<Departamento> getColDepartamentos() {
		return colDepartamentos;
	}
	
	public void setColDepartamentos(List<Departamento> colDepartamentos) {
		this.colDepartamentos = colDepartamentos;
	}

	public List<PreferenciaDisciplinaProfessor> getPreferenciasDisciplinaSelecionadas() {
		return preferenciasDisciplinaSelecionadas;
	}

	public void setPreferenciasDisciplinaSelecionadas(List<PreferenciaDisciplinaProfessor> preferenciasDisciplinaSelecionadas) {
		this.preferenciasDisciplinaSelecionadas = preferenciasDisciplinaSelecionadas;
	}

	public List<PreferenciaHorarioProfessor> getPreferenciasHorarioSelecionadas() {
		return preferenciasHorarioSelecionadas;
	}

	public void setPreferenciasHorarioSelecionadas(List<PreferenciaHorarioProfessor> preferenciasHorarioSelecionadas) {
		this.preferenciasHorarioSelecionadas = preferenciasHorarioSelecionadas;
	}
}