package br.edu.ifma.csp.timetable.composer;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;

public abstract class Composer extends GenericForwardComposer<Component>{

	private static final long serialVersionUID = 8906495139436799294L;
	
	abstract void init();
	
	public void save() {
		
	}
	
	public void edit() {
		
	}
	
	public void show() {
		
	}
	
	public void delete() {
		
	}

}
