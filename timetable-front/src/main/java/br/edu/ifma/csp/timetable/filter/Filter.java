package br.edu.ifma.csp.timetable.filter;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import br.edu.ifma.csp.timetable.model.Entidade;

public abstract class Filter<T extends Entidade> {
	
	protected T entidadeSelecionada;
	
	private List<T> col;
	
	public T getEntidadeSelecionada() {
		return entidadeSelecionada;
	}
	
	public void setEntidadeSelecionada(T entidadeSelecionada) {
		this.entidadeSelecionada = entidadeSelecionada;
	}
	
	@Command
	@NotifyChange("col")
	public abstract void filtrar();
	
	public void setCol(List<T> col) {
		this.col = col;
	}
	
	public List<T> getCol() {
		return col;
	}
}
