package br.edu.ifma.csp.timetable.viewmodel;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;

import br.edu.ifma.csp.timetable.handler.HorarioHandler;
import br.edu.ifma.csp.timetable.model.Horario;

public class HorarioViewModel extends ViewModel<Horario> {
	
	private List<String> colDias;
	
	@NotifyChange("colDias")
	@AfterCompose(superclass=true)
	public void init(Component view) {
		colDias = new ArrayList<String>(Arrays.asList(DateFormatSymbols.getInstance().getWeekdays()));
		colDias.remove(0);
	}
	
	@Command
	public void salvar() {
		
		HorarioHandler handler = new HorarioHandler();
		handler.metodo(entidadeSelecionada);
	}
	
	public List<String> getColDias() {
		return colDias;
	}
	
	public void setColDias(List<String> colDias) {
		this.colDias = colDias;
	}
}
