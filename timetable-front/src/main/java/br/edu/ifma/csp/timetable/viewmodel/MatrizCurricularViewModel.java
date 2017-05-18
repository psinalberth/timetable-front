package br.edu.ifma.csp.timetable.viewmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;

import br.edu.ifma.csp.timetable.dao.DisciplinaDao;
import br.edu.ifma.csp.timetable.dao.TurnoDao;
import br.edu.ifma.csp.timetable.model.DetalheDisciplina;
import br.edu.ifma.csp.timetable.model.Disciplina;
import br.edu.ifma.csp.timetable.model.MatrizCurricular;
import br.edu.ifma.csp.timetable.model.Periodo;
import br.edu.ifma.csp.timetable.model.Turno;
import br.edu.ifma.csp.timetable.repository.Disciplinas;
import br.edu.ifma.csp.timetable.repository.Turnos;
import br.edu.ifma.csp.timetable.util.Lookup;

public class MatrizCurricularViewModel extends ViewModel<MatrizCurricular> {
	
	private final Turnos turnos = Lookup.dao(TurnoDao.class);
	private final Disciplinas disciplinas = Lookup.dao(DisciplinaDao.class);
	
	private List<Periodo> periodosSelecionados;
	private List<DetalheDisciplina> disciplinasSelecionadas;
	private List<Turno> colTurnos;
	private List<String> colGrupoEletivas;
	private List<Disciplina> colDisciplinas;
	private List<Disciplina> preRequisitosSelecionados;
	
	private String filtroDescricao;
	private boolean isEditandoPreRequisitos;
	private boolean editandoDetalheDisciplina;
	private DetalheDisciplina detalheSelecionado;
	
	@NotifyChange({"colTurnos", "colGrupoEletivas"})
	@AfterCompose(superclass=true)
	public void init(Component view) {
		
		setColDisciplinas(disciplinas.all());
		setColTurnos(turnos.all());
		
		colGrupoEletivas = new ArrayList<String>();
		
		colGrupoEletivas.add("I-A");
		colGrupoEletivas.add("I-B");
		colGrupoEletivas.add("II");
	}
	
	@NotifyChange("entidadeSelecionada")
	@Command
	public void adicionarPeriodo() {
		
		Periodo periodo = new Periodo();
		
		periodo.setMatrizCurricular(entidadeSelecionada);
		periodo.setDataUltAlteracao(entidadeSelecionada.getDataUltAlteracao());
		periodo.setUsuarioUltAlteracao(entidadeSelecionada.getUsuarioUltAlteracao());
		entidadeSelecionada.getPeriodos().add(periodo);
	}
	
	@NotifyChange("entidadeSelecionada")
	@Command
	public void removerPeriodo() {
		
		if (periodosSelecionados != null && periodosSelecionados.size() > 0) {		
			entidadeSelecionada.getPeriodos().removeAll(periodosSelecionados);
		}
	}
	
	@NotifyChange("entidadeSelecionada")
	@Command
	public void adicionarDisciplina(@BindingParam("periodo") Periodo periodoSelecionado) {
		
		DetalheDisciplina detalheDisciplina = new DetalheDisciplina();
		
		detalheDisciplina.setDataUltAlteracao(entidadeSelecionada.getDataUltAlteracao());
		detalheDisciplina.setUsuarioUltAlteracao(entidadeSelecionada.getUsuarioUltAlteracao());
		detalheDisciplina.setPeriodo(periodoSelecionado);
		
		periodoSelecionado.getDetalhes().add(0, detalheDisciplina);
	}
	
	@NotifyChange("entidadeSelecionada")
	@Command
	public void removerDisciplina(@BindingParam("periodo") Periodo periodoSelecionado) {
		
		if (disciplinasSelecionadas != null && disciplinasSelecionadas.size() > 0) {
			periodoSelecionado.getDetalhes().removeAll(disciplinasSelecionadas);
		}
	}
	
	@NotifyChange({"detalheSelecionado", "editandoDetalheDisciplina"})
	@Command
	public void exibirDetalhesDisciplina(@BindingParam("detalhe") DetalheDisciplina detalheSelecionado) {
		setDetalheSelecionado(detalheSelecionado);
		setEditandoDetalheDisciplina(true);
	}
	
	@NotifyChange({"detalheSelecionado", "editandoDetalheDisciplina"})
	@Command
	public void ocultarDetalhesDisciplina() {
		setDetalheSelecionado(null);
		setEditandoDetalheDisciplina(false);
	}
	
	@NotifyChange({"detalheSelecionado", "editandoPreRequisitos"})
	@Command
	public void adicionarPreRequisito(@BindingParam("detalhe") DetalheDisciplina detalheSelecionado, @BindingParam("disciplina") Disciplina disciplina) {
		
		setEditandoPreRequisitos(true);
		setDetalheSelecionado(detalheSelecionado);
	}
	
	@NotifyChange("entidadeSelecionada")
	@Command
	public void removerPreRequisito(@BindingParam("detalhe") DetalheDisciplina detalheSelecionado) {
		
	}
	
	@NotifyChange({"detalheSelecionado", "editandoPreRequisitos", "entidadeSelecionada", 
				   "preRequisitosSelecionados", "filtroDescricao", "colDisciplinas"})
	@Command
	public void associarPreRequisitos() {
		
		detalheSelecionado.setPreRequisitos(preRequisitosSelecionados);
		
		setPreRequisitosSelecionados(null);
		setEditandoPreRequisitos(false);
		setColDisciplinas(disciplinas.all());
		setFiltroDescricao(null);
	}
	
	@Command
	@NotifyChange("colDisciplinas")
	public void filtrarPreRequisitos() {
		
		colDisciplinas = disciplinas.all();
		
		if (filtroDescricao != null && !filtroDescricao.isEmpty()) {
			colDisciplinas = colDisciplinas
					.stream()
					.filter(disciplina -> disciplina.getDescricao().toUpperCase().contains(filtroDescricao.toUpperCase()))
					.collect(Collectors.toList());	
		}
	}
	
	public void adicionarColPeriodos(int semestres) {
		/*setColPeriodos(IntStream.of(IntStream.rangeClosed(1, semestres).toArray()).boxed().collect(Collectors.toList()));
		getBinder().notifyChange(this, "colPeriodos");*/
	}

	public List<Periodo> getPeriodosSelecionados() {
		return periodosSelecionados;
	}

	public void setPeriodosSelecionados(List<Periodo> periodosSelecionados) {
		this.periodosSelecionados = periodosSelecionados;
	}

	public List<DetalheDisciplina> getDisciplinasSelecionadas() {
		return disciplinasSelecionadas;
	}

	public void setDisciplinasSelecionadas(List<DetalheDisciplina> disciplinasSelecionadas) {
		this.disciplinasSelecionadas = disciplinasSelecionadas;
	}

	public List<Turno> getColTurnos() {
		return colTurnos;
	}

	public void setColTurnos(List<Turno> colTurnos) {
		this.colTurnos = colTurnos;
	}

	public List<String> getColGrupoEletivas() {
		return colGrupoEletivas;
	}

	public void setColGrupoEletivas(List<String> colGrupoEletivas) {
		this.colGrupoEletivas = colGrupoEletivas;
	}

	public List<Disciplina> getColDisciplinas() {
		return colDisciplinas;
	}

	public void setColDisciplinas(List<Disciplina> colDisciplinas) {
		this.colDisciplinas = colDisciplinas;
	}

	public boolean isEditandoPreRequisitos() {
		return isEditandoPreRequisitos;
	}

	public void setEditandoPreRequisitos(boolean isEditandoPreRequisitos) {
		this.isEditandoPreRequisitos = isEditandoPreRequisitos;
	}

	public DetalheDisciplina getDetalheSelecionado() {
		return detalheSelecionado;
	}

	public void setDetalheSelecionado(DetalheDisciplina detalheSelecionado) {
		this.detalheSelecionado = detalheSelecionado;
	}

	public List<Disciplina> getPreRequisitosSelecionados() {
		return preRequisitosSelecionados;
	}

	public void setPreRequisitosSelecionados(List<Disciplina> preRequisitosSelecionados) {
		this.preRequisitosSelecionados = preRequisitosSelecionados;
	}

	public String getFiltroDescricao() {
		return filtroDescricao;
	}

	public void setFiltroDescricao(String filtroDescricao) {
		this.filtroDescricao = filtroDescricao;
	}

	public boolean isEditandoDetalheDisciplina() {
		return editandoDetalheDisciplina;
	}

	public void setEditandoDetalheDisciplina(boolean editandoDetalheDisciplina) {
		this.editandoDetalheDisciplina = editandoDetalheDisciplina;
	}
}
