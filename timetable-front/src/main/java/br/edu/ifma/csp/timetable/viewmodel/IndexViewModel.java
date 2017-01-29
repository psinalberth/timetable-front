package br.edu.ifma.csp.timetable.viewmodel;

import java.util.Calendar;

import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

import br.edu.ifma.csp.timetable.model.Usuario;

public class IndexViewModel {
	
	private String greeting;
	
	@Init
	@NotifyChange("greeting")
	public void init() {
		
		Usuario usuario = (Usuario) Executions.getCurrent().getSession().getAttribute("usuario");
		
		if (usuario != null) {
			
			setGreeting(getTimeGreeting() + ", " + usuario.getNome().split(" ")[0]);
		}
	}
	
	private String getTimeGreeting() {
		
		Calendar cal = Calendar.getInstance();
		
		int hora = cal.get(Calendar.HOUR_OF_DAY);
		
		if (hora >= 12 && hora < 18)
			return "Boa tarde";
		
		if (hora >= 4 && hora < 12)
			return "Bom dia";
			
		
		return "Boa noite";
	}
	
	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}
	
	public String getGreeting() {
		return greeting;
	}
}
