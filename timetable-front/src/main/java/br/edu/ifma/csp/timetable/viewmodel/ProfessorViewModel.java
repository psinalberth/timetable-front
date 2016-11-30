package br.edu.ifma.csp.timetable.viewmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Span;

import br.edu.ifma.csp.timetable.dao.HorarioDao;
import br.edu.ifma.csp.timetable.model.Horario;
import br.edu.ifma.csp.timetable.model.PreferenciaDisciplinaProfessor;
import br.edu.ifma.csp.timetable.model.PreferenciaHorarioProfessor;
import br.edu.ifma.csp.timetable.model.Professor;
import br.edu.ifma.csp.timetable.repository.Horarios;
import br.edu.ifma.csp.timetable.util.Lookup;

public class ProfessorViewModel extends ViewModel<Professor> {
	
	private List<PreferenciaDisciplinaProfessor> preferenciasDisciplinaSelecionadas;
	private List<PreferenciaHorarioProfessor> preferenciasHorarioSelecionadas;
	
	private List<Date> colHorariosInicio;
	private List<Horario> colHorarios;
	
	private Horarios horarios;
	
	@Wire("#form #grid")
	private Grid grid;
	
	@AfterCompose(superclass=true)
	@NotifyChange({"colHorariosInicio", "colHorarios"})
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		
		horarios = Lookup.dao(HorarioDao.class);
		
		setColHorariosInicio(horarios.allHorariosInicio());
		setColHorarios(horarios.all());
		
		Selectors.wireComponents(view, this, false);
		
		initHorarios();	
	}
	
	@Command
	@NotifyChange({"entidadeSelecionada", "consultando", "removivel", "editando"})
	public void novo() throws InstantiationException, IllegalAccessException {
		super.novo();
		
		if (grid != null) {
			initHorarios();
		}
	}
	
	@Command
	@NotifyChange({"entidadeSelecionada", "consultando", "removivel", "editando"})
	public void editar() {
		
		super.editar();
		
		initHorarios();
		
		for (PreferenciaHorarioProfessor preferencia : entidadeSelecionada.getPreferenciasHorario()) {
			
			int rowIndex = getRowIndex(preferencia.getHorario().getHoraInicio().toString());
			int columnIndex = getColumnIndex(preferencia.getHorario().getDia().getDescricao());
			
			Component comp =  grid.getCell(rowIndex, columnIndex).getChildren().get(0);
			
			if (comp instanceof Checkbox) {
				
				((Checkbox)comp).setChecked(true);
			}	
		}
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
	public void adicionarPreferenciaHorario(Horario horario) {
		
		PreferenciaHorarioProfessor preferencia = new PreferenciaHorarioProfessor();
		
		preferencia.setProfessor(entidadeSelecionada);
		preferencia.setHorario(horario);
		
		entidadeSelecionada.getPreferenciasHorario().add(preferencia);
	}
	
	@Command
	@NotifyChange("entidadeSelecionada")
	public void removerPreferenciaHorario(Horario horario) {
		
		entidadeSelecionada.getPreferenciasHorario().removeIf(new Predicate<PreferenciaHorarioProfessor>() {

			@Override
			public boolean test(PreferenciaHorarioProfessor pref) {
				return pref.getHorario().getId() == horario.getId();
			}
		});
	}
	
	private void initHorarios() {
		
		grid.getRows().getChildren().clear();
		
		for (Date horario : colHorariosInicio) {
			
			Row row = new Row();
			
			Label label = new Label(horario.toString());
			
			row.getChildren().addAll(Arrays.asList(new Component[] {label, new Span(), new Span(), new Span(), new Span(), new Span()}));
			
			grid.getRows().getChildren().add(row);
		}
		
		for (Horario horario : colHorarios) {
			
			int rowIndex = getRowIndex(horario.getHoraInicio().toString());
			int columnIndex = getColumnIndex(horario.getDia().getDescricao());
			
			Checkbox checkbox = new Checkbox();
			checkbox.setAttribute("horario", horario);
			
			checkbox.addEventListener(Events.ON_CHECK, new EventListener<Event>() {

				@Override
				public void onEvent(Event evt) throws Exception {
					
					Horario h = (Horario) checkbox.getAttribute("horario");
					
					if (checkbox.isChecked()) {
						adicionarPreferenciaHorario(h);
						
					} else {
						removerPreferenciaHorario(h);
					}
				}
			});
			
			grid.getCell(rowIndex, columnIndex).appendChild(checkbox);
		}
	}
	
	private int getRowIndex(String label) {
		
		for (int i = 0; i < grid.getRows().getChildren().size(); i++) {
			
			Component c = grid.getCell(i, 0);
			
			if (c instanceof Label) {
				
				if (((Label)c).getValue().equalsIgnoreCase(label)) {
					return i;
				}
			}
		}
		
		return 0;
	}
	
	private int getColumnIndex(String label) {
		
		for (Component column : grid.getColumns().getChildren()) {
			
			if (column instanceof Column) {
				
				if (((Column)column).getLabel().equalsIgnoreCase(label)) {
					
					return grid.getColumns().getChildren().indexOf(column);
				}
			}
		}
		
		return 0;
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
	
	public List<Date> getColHorariosInicio() {
		return colHorariosInicio;
	}
	
	public void setColHorariosInicio(List<Date> colHorariosInicio) {
		this.colHorariosInicio = colHorariosInicio;
	}
	
	public List<Horario> getColHorarios() {
		return colHorarios;
	}
	
	public void setColHorarios(List<Horario> colHorarios) {
		this.colHorarios = colHorarios;
	}
}
