package br.edu.ifma.csp.timetable.viewmodel;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.zk.ui.Component;

import br.edu.ifma.csp.timetable.model.Disciplina;

public class DisciplinaViewModel extends ViewModel<Disciplina> {

	@AfterCompose(superclass=true)
	public void init(Component view) {
		
	}
}
