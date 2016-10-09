package br.edu.ifma.csp.timetable.composer;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.WrongValuesException;
import org.zkoss.zk.ui.util.Clients;

import br.edu.ifma.csp.timetable.handler.HorarioHandler;
import br.edu.ifma.csp.timetable.model.Horario;
import br.edu.ifma.csp.timetable.util.Validations;

public class HorarioComposer extends Composer<Horario> {

	private static final long serialVersionUID = -7798917021986489377L;
	
	private List<String> colDias;

	@Init
	public void init() {
		
		colDias = new ArrayList<String>(Arrays.asList(DateFormatSymbols.getInstance().getWeekdays()));
		colDias.remove(0);
	}
	
	public List<String> getColDias() {
		return colDias;
	}
	
	public void setColDias(List<String> colDias) {
		this.colDias = colDias;
	}
	
	@Override
	public void save() {
		
		HorarioHandler handler = new HorarioHandler();
		
		getBinder().notifyChange(this, "*");
		
		Validations.validate(getBinder(), entidade, repository);
		
		try {
			
			handler.metodo(entidade);
			
			Clients.showNotification("Informações salvas com sucesso.", "info", null, "middle_center", 1000);
			
			list();
			
		} catch (WrongValuesException ex) {	
			ex.printStackTrace();
		}
		
	}
}
