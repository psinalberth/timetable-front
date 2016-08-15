package br.edu.ifma.csp.timetable.util;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Bandbox;

public class LookupDialog extends Bandbox {
	
	public LookupDialog() {
		Executions.createComponents("../partials/zul/lookupdialog.zul", null, null);
	}
}
