package br.edu.ifma.csp.timetable.component;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.IdSpace;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Listbox;

/**
 * @author inalberth
 *
 */
public class LookupBox extends Bandbox {

	private static final long serialVersionUID = -100982316182135654L;
	
	@Wire
	private Listbox listbox;
	
	public LookupBox() {
		
		Executions.createComponents("/partials/zul/custom/lookupbox.zul", this, null);
		Selectors.wireComponents(this, this, false);
		Selectors.wireEventListeners(this, this);
	}
	
	@Override
	public String getZclass() {
		return _zclass == null ? "z-lookupbox" : _zclass;
	}
	
}
