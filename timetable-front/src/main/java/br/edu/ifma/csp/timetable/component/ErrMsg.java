package br.edu.ifma.csp.timetable.component;

import java.io.IOException;

import org.zkoss.zk.ui.sys.ContentRenderer;
import org.zkoss.zul.impl.XulElement;

public class ErrMsg extends XulElement {

	private static final long serialVersionUID = -6635821161669097190L;
	
	private String _msg;
	
	public String getMsg() {
		return _msg;
	}
	
	public void setMsg(String msg) {
		this._msg = msg;
		
		smartUpdate("msg", _msg);
	}
	
	@Override
	public String getZclass() {
		return _zclass == null ? "z-errmsg" : _zclass;
	}
	
	@Override
	protected void renderProperties(ContentRenderer renderer) throws IOException {
		super.renderProperties(renderer);
		
		if (_msg != null) {
			render(renderer, "msg", _msg);
		}
	}
}
