package br.edu.ifma.csp.timetable.composer;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValuesException;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Column;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Vlayout;

import br.edu.ifma.csp.timetable.dao.HorarioDao;
import br.edu.ifma.csp.timetable.dao.LocalDao;
import br.edu.ifma.csp.timetable.dao.ProfessorDao;
import br.edu.ifma.csp.timetable.handler.TimetableHandler;
import br.edu.ifma.csp.timetable.model.Aula;
import br.edu.ifma.csp.timetable.model.DetalheTimetable;
import br.edu.ifma.csp.timetable.model.Local;
import br.edu.ifma.csp.timetable.model.Professor;
import br.edu.ifma.csp.timetable.model.Timetable;
import br.edu.ifma.csp.timetable.repository.Horarios;
import br.edu.ifma.csp.timetable.repository.Locais;
import br.edu.ifma.csp.timetable.repository.Professores;
import br.edu.ifma.csp.timetable.util.Lookup;
import br.edu.ifma.csp.timetable.util.Validations;

public class TimetableComposer extends Composer<Timetable> {
	
	private static final long serialVersionUID = -5412250576930148325L;
	
	private Horarios horarios;
	private Locais locais;
	private Professores professores;
	
	private List<Professor> colProfessores;
	private List<Local> colLocais;
	
	private Local local;
	private Professor professor;
	
	@Wire("#form #grid")
	private Grid grid;
	
	private List<DetalheTimetable> detalhesSelecionados;

	@Init
	public void init() {
		
		horarios = Lookup.dao(HorarioDao.class);
		professores = Lookup.dao(ProfessorDao.class);
		locais = Lookup.dao(LocalDao.class);
		
		setColProfessores(professores.all());
		setColLocais(locais.all());
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}
	
	@Override
	public void save() {
		
		getBinder().notifyChange(this, "*");
		
		Validations.validate(getBinder(), entidade, repository);
		
		try {
			
			TimetableHandler handler = new TimetableHandler();
			handler.setTimetable(entidade);
			handler.execute();
			getBinder().notifyChange(entidade, "aulas");
			
			buildRows();
			
		} catch (WrongValuesException ex) {	
			ex.printStackTrace();
		}
	}
	
	public void lookup() {
		
		System.out.println(professor);
		
		buildRows();
		
		for (Component c : grid.getRows().getChildren()) {
			
			if (c instanceof Row && c.getChildren().size() > 0) {
			
				for (Component x : c.getChildren()) {
					
					if (x instanceof Vlayout && c.getChildren().size() > 0) {
						
						for (Component v : x.getChildren()) {
							
							if (v instanceof Vlayout && v.getChildren().size() > 0) {
								
								
								Component local = v.getChildren().get(2);
								Component comp = v.getChildren().get(1);
								
								if (getLocal() != null) {
									
									if (!((Label)local).getValue().equalsIgnoreCase(getLocal().getNome())) {
										v.setVisible(false);
									}
								}
								
								if (getProfessor() != null) {
								
									if (comp instanceof Label) {
										
										if (!((Label)comp).getValue().equalsIgnoreCase(professor.getNome())) {
											v.setVisible(false);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void adicionarDetalhe() {
		
		DetalheTimetable detalheTimetable = new DetalheTimetable();
		
		detalheTimetable.setTimetable(entidade);
		entidade.getDetalhes().add(detalheTimetable);
		
		getBinder().notifyChange(entidade, "detalhes");
	}
	
	public void removerDetalhe() {
		
		entidade.getDetalhes().removeAll(detalhesSelecionados);
		
		getBinder().notifyChange(entidade, "detalhes");
	}
	
	private void buildRows() {
		
		List<Date> listaHorarios = horarios.allHorariosInicio();
		
		grid.getRows().getChildren().clear();
		
		for (Date horario : listaHorarios) {
			
			Row row = new Row();
			
			Label label = new Label(horario.toString());
			//label.setSclass("z-column");
			
			Vlayout layoutSegunda = new Vlayout();
			layoutSegunda.setSpacing("20px");
			
			Vlayout layoutTerca = new Vlayout();
			layoutTerca.setSpacing("20px");
			
			Vlayout layoutQuarta = new Vlayout();
			layoutQuarta.setSpacing("20px");
			
			Vlayout layoutQuinta = new Vlayout();
			layoutQuinta.setSpacing("20px");
			
			Vlayout layoutSexta = new Vlayout();
			layoutSexta.setSpacing("20px");
			
			row.getChildren().addAll(Arrays.asList(new Component[]{label, layoutSegunda, layoutTerca, layoutQuarta, layoutQuinta, layoutSexta}));
			grid.getRows().getChildren().add(row);
		}
		
		for (Aula aula : entidade.getAulas()) {
			
			//if (aula.getPeriodo() == 5) {
				
				Vlayout vlayout = new Vlayout();
				
				Label labelDisciplina = new Label(aula.getDisciplina().getSigla());
				labelDisciplina.setTooltiptext(aula.getDisciplina().getDescricao());
				
				vlayout.appendChild(labelDisciplina);
				vlayout.appendChild(new Label(aula.getProfessor().getNome()));
				vlayout.appendChild(new Label(aula.getLocal().getNome()));
				
				int rowIndex = getRowIndex(aula.getHorario().getHoraInicio().toString());
				int colIndex = getColumnIndex(aula.getHorario().getDia().getDescricao());
				
				if (!(rowIndex == 0 && colIndex == 0)) {
				
					Component cell = grid.getCell(rowIndex, colIndex);
					
					cell.appendChild(vlayout);
				
				}
			//}
		}
		
		getBinder().notifyChange(this, "entidade");
	}
	
	public void limpar(int opc) {
		
		switch (opc) {
			case 2:
				setProfessor(null);
				getBinder().notifyChange(this, "professor");
				break;
				
			case 3:
				setLocal(null);
				getBinder().notifyChange(this, "local");
				break;
		}
		
		lookup();
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
	
	public void onChange() {
		getBinder().notifyChange(entidade, "detalhes");
	}
	
	public boolean isSolucaoEncontrada() {
		return entidade != null && entidade.getAulas() != null && entidade.getAulas().size() > 0;
	}
	
	public boolean isCriterioProfessor() {
		return true;
	}
	
	public boolean isCriterioDisciplina() {
		return true;
	}
	
	public Professor getProfessor() {
		return professor;
	}
	
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	
	public Local getLocal() {
		return local;
	}
	
	public void setLocal(Local local) {
		this.local = local;
	}
	
	public List<Professor> getColProfessores() {
		return colProfessores;
	}
	
	public void setColProfessores(List<Professor> colProfessores) {
		this.colProfessores = colProfessores;
	}
	
	public List<Local> getColLocais() {
		return colLocais;
	}
	
	public void setColLocais(List<Local> colLocais) {
		this.colLocais = colLocais;
	}
	
	public List<DetalheTimetable> getDetalhesSelecionados() {
		return detalhesSelecionados;
	}
	
	public void setDetalhesSelecionados(List<DetalheTimetable> detalhesSelecionados) {
		this.detalhesSelecionados = detalhesSelecionados;
	}
}
