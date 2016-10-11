package br.edu.ifma.csp.timetable.composer;

import java.util.List;

import org.zkoss.bind.annotation.Init;

import br.edu.ifma.csp.timetable.model.MatrizCurricular;
import br.edu.ifma.csp.timetable.model.Periodo;

public class MatrizCurricularComposer extends Composer<MatrizCurricular> {

	private static final long serialVersionUID = 20928881137239658L;
	
	private List<Periodo> periodosSelecionados;

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
	
	public List<Periodo> getPeriodosSelecionados() {
		return periodosSelecionados;
	}
	
	public void setPeriodosSelecionados(List<Periodo> periodosSelecionados) {
		this.periodosSelecionados = periodosSelecionados;
	}
}