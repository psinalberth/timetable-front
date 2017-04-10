package br.edu.ifma.csp.timetable.viewmodel;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValuesException;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Column;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Span;
import org.zkoss.zul.Vlayout;

import br.edu.ifma.csp.timetable.dao.HorarioDao;
import br.edu.ifma.csp.timetable.dao.LocalDao;
import br.edu.ifma.csp.timetable.dao.ProfessorDao;
import br.edu.ifma.csp.timetable.dao.TipoDetalheTimetableDao;
import br.edu.ifma.csp.timetable.handler.TimetableHandler;
import br.edu.ifma.csp.timetable.model.Aula;
import br.edu.ifma.csp.timetable.model.DetalheTimetable;
import br.edu.ifma.csp.timetable.model.Local;
import br.edu.ifma.csp.timetable.model.Periodo;
import br.edu.ifma.csp.timetable.model.Professor;
import br.edu.ifma.csp.timetable.model.Timetable;
import br.edu.ifma.csp.timetable.model.TipoDetalheTimetable;
import br.edu.ifma.csp.timetable.repository.Horarios;
import br.edu.ifma.csp.timetable.repository.Locais;
import br.edu.ifma.csp.timetable.repository.Professores;
import br.edu.ifma.csp.timetable.repository.TiposDetalheTimetable;
import br.edu.ifma.csp.timetable.util.Lookup;
import br.edu.ifma.csp.timetable.util.Validations;

public class TimetableViewModel extends ViewModel<Timetable> {
	
	private Horarios horarios = Lookup.dao(HorarioDao.class);
	private Locais locais = Lookup.dao(LocalDao.class);
	private Professores professores = Lookup.dao(ProfessorDao.class);
	private TiposDetalheTimetable tiposDetalhe = Lookup.dao(TipoDetalheTimetableDao.class);
	
	private List<Professor> colProfessores;
	private List<Local> colLocais;
	private List<Date> colHorariosInicio;
	private List<TipoDetalheTimetable> colTiposDetalhe;
	
	private Local local;
	private Professor professor;
	private Periodo periodo;
	
	@Wire("#form #grid")
	private Grid grid;
	
	private List<DetalheTimetable> detalhesSelecionados;
	
	@AfterCompose(superclass=true)
	@NotifyChange({"colProfessores", "colLocais", "colTiposDetalhe", "colHorariosInicio"})
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		
		setColProfessores(professores.all());
		setColLocais(locais.all());
		setColTiposDetalhe(tiposDetalhe.all());
		setColHorariosInicio(horarios.allHorariosInicio());
		
		Selectors.wireComponents(view, this, false);
	}
	
	@Command
	public void salvar() {
		
		try {
			
			Validations.validate(entidadeSelecionada, repository);
			
			setProfessor(null);
			setPeriodo(null);
			setLocal(null);
			
			repository.save(entidadeSelecionada);
			
			entidadeSelecionada = repository.byId(entidadeSelecionada.getId());
			
			TimetableHandler handler = new TimetableHandler();
			handler.setTimetable(entidadeSelecionada);
			handler.execute();
			
			if (!handler.getSolver().solve()) {
				grid.getRows().getChildren().clear();
			}
			
			setPeriodo(entidadeSelecionada.getMatrizCurricular().getPeriodos().get(0));
			
			lookup();
			
			BindUtils.postNotifyChange(null, null, this, "entidadeSelecionada");
			BindUtils.postNotifyChange(null, null, this, "consultando");
			BindUtils.postNotifyChange(null, null, this, "removivel");
			BindUtils.postNotifyChange(null, null, this, "editando");
			BindUtils.postNotifyChange(null, null, this, "col");
			BindUtils.postNotifyChange(null, null, this, "professor");
			BindUtils.postNotifyChange(null, null, this, "periodo");
			BindUtils.postNotifyChange(null, null, this, "local");
			BindUtils.postNotifyChange(null, null, this, "aulasSelecionadas");
			BindUtils.postNotifyChange(null, null, this, "solucaoEncontrada");
			
		} catch (WrongValuesException ex) {
			Validations.showValidationErrors();
		}
		
		/*String rootDir = "/home/inalberth/csp_casos_teste4/completo";

		MatrizesCurriculares matrizesCurriculares = Lookup.dao(MatrizCurricularDao.class);
		
		File root = new File(rootDir);
		File dir = null;
		
		if (root.mkdir()) {
			
		}
		
		int tamanho = root.listFiles().length + 1;
		
		dir = new File(rootDir + File.separatorChar + "cenario$" + tamanho);
		
		if (dir.mkdir()) {
		
			for (int i = 0; i < 50; i++) {
				
				Teste teste = new Teste();
				teste.setNumeroPeriodos(8);
				teste.setMatrizCurricular(matrizesCurriculares.byId(84));
				teste.setTimetable(repository.byId(951));
				
				teste.execute();
				
				teste.getSolver().showStatistics();
				
				if (teste.getSolver().solve()) {
					
					teste.prettyOut();
					
					try {
						
						File file = new File(dir.getAbsolutePath() + File.separatorChar + "data.dat");
						
						if (!file.exists()) {
							file.createNewFile();
						}
						
						FileWriter fw = new FileWriter(file, true);
						
						fw.write((i+1) + " " + teste.getSolver().getTimeCount() + "\n");
						fw.close();
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}*/
	}
	
	private void buildRows(List<Aula> aulas) {
		
		List<Date> listaHorarios = horarios.allHorariosInicio();
		
		grid.getRows().getChildren().clear();
		
		for (Date horario : listaHorarios) {
			
			Row row = new Row();
			
			Label label = new Label(horario.toString());
			
			Span span = new Span();
			span.appendChild(label);
			
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
			
			row.getChildren().addAll(Arrays.asList(new Component[]{span, layoutSegunda, layoutTerca, layoutQuarta, layoutQuinta, layoutSexta}));
			grid.getRows().getChildren().add(row);
		}
		
		for (Aula aula : aulas) {
				
			Vlayout vlayout = new Vlayout();	
			
			Label labelPeriodo = new Label(String.valueOf(entidadeSelecionada.getMatrizCurricular().getCurso().getCodigo() + "." + aula.getPeriodo()));
			labelPeriodo.setSclass("grid-label");
			
			Label labelDisciplina = new Label(aula.getDisciplina().getSigla());
			labelDisciplina.setTooltiptext(aula.getDisciplina().getDescricao());
			
			Label labelProfessor = new Label(aula.getProfessor().getNome());
			labelProfessor.setSclass("grid-label");
			
			Label labelLocal = new Label(aula.getLocal().getNome());
			labelLocal.setSclass("grid-label");
			
			vlayout.appendChild(labelDisciplina);
			vlayout.appendChild(labelProfessor);
			vlayout.appendChild(labelLocal);
			vlayout.appendChild(labelPeriodo);
			
			int rowIndex = getRowIndex(aula.getHorario().getHoraInicio().toString());
			int colIndex = getColumnIndex(aula.getHorario().getDia().getDescricao());
			
			if (!(rowIndex == 0 && colIndex == 0)) {
			
				Component cell = grid.getCell(rowIndex, colIndex);
				
				if (!(cell instanceof Label)) {
					cell.appendChild(vlayout);
				}
			}
		}
	}
	
	@Command
	@NotifyChange({"professor", "periodo", "local"})
	public void limpar(@BindingParam("opc") int opc) {
		
		switch (opc) {
		
			case 1:
				setPeriodo(null);
				break;
			
			case 2:
				setProfessor(null);
				break;
				
			case 3:
				setLocal(null);
				break;
		}
		
		lookup();
	}
	
	private int getRowIndex(String label) {
		
		for (int i = 0; i < grid.getRows().getChildren().size(); i++) {
			
			Component c = grid.getCell(i, 0);
			
			for (Component x : c.getChildren()) {
			
				if (x instanceof Label) {
					
					if (((Label)x).getValue().equalsIgnoreCase(label)) {
						return i;
					}
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
	
	@Command
	public void lookup() {
		
		List<Aula> aulasFiltradas = entidadeSelecionada.getAulas().stream()
				.filter((aula) -> (getPeriodo() != null && getPeriodo().getCodigo() == aula.getPeriodo()) || 
								  (getProfessor() != null && getProfessor().getId() == aula.getProfessor().getId()) ||
								  (getLocal() != null && getLocal().getId() == aula.getLocal().getId()) ||
								  (getPeriodo() == null && getProfessor() == null && getLocal() == null))
				.collect(Collectors.toList());
		
		buildRows(aulasFiltradas);
	}
	
	@Command
	@NotifyChange("entidadeSelecionada")
	public void onChange() {
		
	}
	
	@Command
	@NotifyChange("entidadeSelecionada")
	public void adicionarDetalhe() {
		
		DetalheTimetable detalheTimetable = new DetalheTimetable();
		
		detalheTimetable.setDataUltAlteracao(entidadeSelecionada.getDataUltAlteracao());
		detalheTimetable.setUsuarioUltAlteracao(entidadeSelecionada.getUsuarioUltAlteracao());
		detalheTimetable.setTimetable(entidadeSelecionada);
		
		entidadeSelecionada.getDetalhes().add(0, detalheTimetable);
	}
	
	@Command
	@NotifyChange("entidadeSelecionada")
	public void removerDetalhe() {
		
		if (detalhesSelecionados != null && detalhesSelecionados.size() > 0) {
			entidadeSelecionada.getDetalhes().removeAll(detalhesSelecionados);
		}
	}
	
	public boolean isSolucaoEncontrada() {
		return entidadeSelecionada != null && entidadeSelecionada.getAulas().size() > 0;
	}
	
	public List<DetalheTimetable> getDetalhesSelecionados() {
		return detalhesSelecionados;
	}
	
	public void setDetalhesSelecionados(List<DetalheTimetable> detalhesSelecionados) {
		this.detalhesSelecionados = detalhesSelecionados;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
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
	
	public List<Date> getColHorariosInicio() {
		return colHorariosInicio;
	}
	
	public void setColHorariosInicio(List<Date> colHorariosInicio) {
		this.colHorariosInicio = colHorariosInicio;
	}

	public List<TipoDetalheTimetable> getColTiposDetalhe() {
		return colTiposDetalhe;
	}

	public void setColTiposDetalhe(List<TipoDetalheTimetable> colTiposDetalhe) {
		this.colTiposDetalhe = colTiposDetalhe;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}
}