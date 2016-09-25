package br.edu.ifma.csp.timetable.composer.lookup;

import java.util.List;

import org.zkoss.bind.BindComposer;
import org.zkoss.zk.ui.Component;

import br.edu.ifma.csp.timetable.model.Entidade;

public class LookupComposer<T extends Entidade> extends BindComposer<Component> {
	
	private static final long serialVersionUID = -1570231282181951997L;
	
	private List<T> col;
	
	private T entidade;
	
	public List<T> getCol() {
		return col;
	}
	
	public void setCol(List<T> col) {
		this.col = col;
	}
	
	public T getEntidade() {
		return entidade;
	}
	
	public void setEntidade(T entidade) {
		this.entidade = entidade;
	}
}
