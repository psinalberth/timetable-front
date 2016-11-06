package br.edu.ifma.csp.timetable.viewmodel;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import br.edu.ifma.csp.timetable.model.PreferenciaDisciplinaProfessor;
import br.edu.ifma.csp.timetable.model.PreferenciaHorarioProfessor;
import br.edu.ifma.csp.timetable.model.Professor;

public class ProfessorViewModel extends ViewModel<Professor> {
	
	private List<PreferenciaDisciplinaProfessor> preferenciasDisciplinaSelecionadas;
	private List<PreferenciaHorarioProfessor> preferenciasHorarioSelecionadas;
	
	@AfterCompose(superclass=true)
	public void init() {
		
	}
	
	@NotifyChange("entidadeSelecionada")
	public void initDetalhes() {
		
		entidadeSelecionada.setPreferenciasDisciplina(new ArrayList<PreferenciaDisciplinaProfessor>());
		
		PreferenciaDisciplinaProfessor preferencia = new PreferenciaDisciplinaProfessor();
		preferencia.setProfessor(entidadeSelecionada);
		
		entidadeSelecionada.getPreferenciasDisciplina().add(preferencia);
	}
	
	@Command
	@NotifyChange("entidadeSelecionada")
	public void adicionarPreferenciaDisciplina() {
		
		PreferenciaDisciplinaProfessor preferencia = new PreferenciaDisciplinaProfessor();
		preferencia.setProfessor(entidadeSelecionada);
		
		entidadeSelecionada.getPreferenciasDisciplina().add(0, preferencia);
	}
	
	@Command
	@NotifyChange("entidadeSelecionada")
	public void removerPreferenciaDisciplina() {
		entidadeSelecionada.getPreferenciasDisciplina().removeAll(preferenciasDisciplinaSelecionadas);
	}
	
	@Command
	@NotifyChange("entidadeSelecionada")
	public void adicionarPreferenciaHorario() {
		
		PreferenciaHorarioProfessor preferencia = new PreferenciaHorarioProfessor();
		preferencia.setProfessor(entidadeSelecionada);
		
		entidadeSelecionada.getPreferenciasHorario().add(0, preferencia);
	}
	
	@Command
	@NotifyChange("entidadeSelecionada")
	public void removerPreferenciaHorario() {
		entidadeSelecionada.getPreferenciasHorario().removeAll(preferenciasHorarioSelecionadas);
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
