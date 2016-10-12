package br.edu.ifma.csp.timetable.composer;

import java.util.List;

import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.impl.ReferenceBindingImpl;

import br.edu.ifma.csp.timetable.model.DetalheDisciplina;
import br.edu.ifma.csp.timetable.model.MatrizCurricular;
import br.edu.ifma.csp.timetable.model.Periodo;

public class MatrizCurricularComposer extends Composer<MatrizCurricular> {

	private static final long serialVersionUID = 20928881137239658L;
	
	private List<Periodo> periodosSelecionados;
	private List<DetalheDisciplina> disciplinasSelecionadas;

	@Init
	public void init() {
		
		getBinder().notifyChange(this, "*");
	}
	
	
	public void adicionarPeriodo() {
		
		Periodo periodo = new Periodo();
		periodo.setMatrizCurricular(entidade);
		entidade.getPeriodos().add(periodo);
		
		getBinder().notifyChange(entidade, "periodos");
	}
	
	public void removerPeriodo() {
		
		entidade.getPeriodos().removeAll(periodosSelecionados);
		
		getBinder().notifyChange(entidade, "periodos");
	}
	
	public void adicionarDisciplina(ReferenceBindingImpl periodo) {
		
		Periodo periodoSelecionado = (Periodo) periodo.getValue(null);
		
		DetalheDisciplina detalheDisciplina = new DetalheDisciplina();
		detalheDisciplina.setPeriodo(periodoSelecionado);
		
		periodoSelecionado.getDetalhes().add(detalheDisciplina);
		
		getBinder().notifyChange(periodoSelecionado, "detalhes");
	}
	
	public void removerDisciplina(ReferenceBindingImpl periodo) {
		
		Periodo periodoSelecionado = (Periodo) periodo.getValue(null);
		
		periodoSelecionado.getDetalhes().removeAll(disciplinasSelecionadas);
		
		getBinder().notifyChange(periodoSelecionado, "detalhes");
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
}