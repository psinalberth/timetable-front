package br.edu.ifma.csp.timetable.composer;

import org.zkoss.bind.annotation.Init;

import br.edu.ifma.csp.timetable.handler.TimetableHandler;
import br.edu.ifma.csp.timetable.model.Timetable;

public class TimetableComposer extends Composer<Timetable> {
	
	private static final long serialVersionUID = -5412250576930148325L;

	@Init
	public void init() {
		
	}
	
	@Override
	public void save() {
		
		TimetableHandler handler = new TimetableHandler();
		handler.setMatrizCurricular(getEntidade().getMatrizCurricular());
		handler.buildModel();
		handler.solve();
	}
	
	public boolean isCriterioProfessor() {
		return true;
	}
	
	public boolean isCriterioDisciplina() {
		return true;
	}
}
