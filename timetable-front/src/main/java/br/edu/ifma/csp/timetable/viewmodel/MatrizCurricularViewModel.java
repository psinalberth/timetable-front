package br.edu.ifma.csp.timetable.viewmodel;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.impl.ReferenceBindingImpl;
import org.zkoss.zk.ui.Component;

import br.edu.ifma.csp.timetable.dao.TurnoDao;
import br.edu.ifma.csp.timetable.model.DetalheDisciplina;
import br.edu.ifma.csp.timetable.model.MatrizCurricular;
import br.edu.ifma.csp.timetable.model.Periodo;
import br.edu.ifma.csp.timetable.model.Turno;
import br.edu.ifma.csp.timetable.repository.Turnos;
import br.edu.ifma.csp.timetable.util.Lookup;

public class MatrizCurricularViewModel extends ViewModel<MatrizCurricular> {
	
	private Turnos turnos;
	
	private List<Periodo> periodosSelecionados;
	private List<DetalheDisciplina> disciplinasSelecionadas;
	private List<Turno> colTurnos;
	private List<String> colGrupoEletivas;
	
	@NotifyChange({"colTurnos", "colGrupoEletivas"})
	@AfterCompose(superclass=true)
	public void init(Component view) {
		
		turnos = Lookup.dao(TurnoDao.class);
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
		entidadeSelecionada.getPeriodos().add(periodo);
	}
	
	@NotifyChange("entidadeSelecionada")
	@Command
	public void removerPeriodo() {
		entidadeSelecionada.getPeriodos().removeAll(periodosSelecionados);
	}
	
	@NotifyChange("entidadeSelecionada")
	@Command
	public void adicionarDisciplina(ReferenceBindingImpl periodo) {
		
		Periodo periodoSelecionado = (Periodo) periodo.getValue(null);
		
		DetalheDisciplina detalheDisciplina = new DetalheDisciplina();
		detalheDisciplina.setPeriodo(periodoSelecionado);
		
		periodoSelecionado.getDetalhes().add(0, detalheDisciplina);
	}
	
	@NotifyChange("entidadeSelecionada")
	@Command
	public void removerDisciplina(ReferenceBindingImpl periodo) {
		
		Periodo periodoSelecionado = (Periodo) periodo.getValue(null);
		
		periodoSelecionado.getDetalhes().removeAll(disciplinasSelecionadas);
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
}
