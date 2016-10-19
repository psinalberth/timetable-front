package br.edu.ifma.csp.timetable.composer;

import java.util.List;

import org.zkoss.bind.annotation.Init;

import br.edu.ifma.csp.timetable.dao.TipoLocalDao;
import br.edu.ifma.csp.timetable.model.Local;
import br.edu.ifma.csp.timetable.model.TipoLocal;
import br.edu.ifma.csp.timetable.repository.TiposLocal;
import br.edu.ifma.csp.timetable.util.Lookup;

public class LocalComposer extends Composer<Local> {

	private static final long serialVersionUID = 1105142300393623341L;
	
	private TiposLocal tiposLocal;
	
	private List<TipoLocal> colTiposLocal;

	@Init
	public void init() {
		
		tiposLocal = Lookup.dao(TipoLocalDao.class);
		
		setColTiposLocal(tiposLocal.all());
		
		getBinder().notifyChange(this, "colTiposLocal");
	}
	
	public List<TipoLocal> getColTiposLocal() {
		return colTiposLocal;
	}
	
	public void setColTiposLocal(List<TipoLocal> colTiposLocal) {
		this.colTiposLocal = colTiposLocal;
	}
}
