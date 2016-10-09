package br.edu.ifma.csp.timetable.composer;

import java.util.HashSet;
import java.util.List;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;

import br.edu.ifma.csp.timetable.model.PreferenciaDisciplinaProfessor;
import br.edu.ifma.csp.timetable.model.PreferenciaHorarioProfessor;
import br.edu.ifma.csp.timetable.model.Professor;

public class ProfessorComposer extends Composer<Professor> {

	private static final long serialVersionUID = -2980484980640308048L;	
	
	private List<PreferenciaDisciplinaProfessor> preferenciasDisciplinaSelecionadas;
	private List<PreferenciaHorarioProfessor> preferenciasHorarioSelecionadas;
	
	@Init
	public void init() {
		
		this.getBinder().notifyChange(this, "*");
		Messagebox.show("It Works!");
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
//		initDetalhes();
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