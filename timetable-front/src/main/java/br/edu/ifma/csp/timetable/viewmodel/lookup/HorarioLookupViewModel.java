package br.edu.ifma.csp.timetable.viewmodel.lookup;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;

import br.edu.ifma.csp.timetable.model.Horario;

public class HorarioLookupViewModel extends LookupViewModel<Horario> {
	
	@AfterCompose(superclass=true)
	public void init() {
		
	}

	@Command
	public void pesquisar() {
		// TODO Auto-generated method stub
		
	}

	@Command
	public void limpar() {
		// TODO Auto-generated method stub
		
	}

}
