package br.edu.ifma.csp.timetable.component;

import org.zkoss.zk.ui.IdSpace;
import org.zkoss.zul.Bandbox;

/**
 * @author inalberth
 *
 */
public class LookupBox extends Bandbox implements IdSpace {

	private static final long serialVersionUID = -100982316182135654L;
	
	@Override
	public String getZclass() {
		return _zclass == null ? "z-lookupbox" : _zclass;
	}
	
}
