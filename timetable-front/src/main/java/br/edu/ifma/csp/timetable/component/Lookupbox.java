package br.edu.ifma.csp.timetable.component;

import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;

public class Lookupbox extends Bandbox {

	private static final long serialVersionUID = 5780997619297491961L;

	private Listbox listbox;	
	private String columns;
	
	@NotifyChange({"listbox", "columns"})
	private void init() {
		
		listbox = new Listbox();
		
		Listhead listhead = new Listhead();
		
		if (columns != null) {
			
			String [] column = columns.split(",");
			
			for (String col : column) {
				listhead.appendChild(new Listheader(col));
			}
		}
		
		listbox.appendChild(listhead);
		
		Bandpopup bandpopup = new Bandpopup();
		bandpopup.appendChild(listbox);
		
		this.appendChild(bandpopup);
	}
	
	public Lookupbox() {
		
		init();
	}
	
	public String getZclass() {
		return _zclass == null ? "z-lookupbox" : _zclass;
	}
	
	public Listbox getListbox() {
		return listbox;
	}

	public String getColumns() {
		return columns;
	}
	
	@NotifyChange("columns")
	public void setColumns(String columns) {
		this.columns = columns;
	}
}
