package br.edu.ifma.csp.timetable.viewmodel;

import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;

import br.edu.ifma.csp.timetable.dao.TipoLocalDao;
import br.edu.ifma.csp.timetable.model.Local;
import br.edu.ifma.csp.timetable.model.TipoLocal;
import br.edu.ifma.csp.timetable.repository.TiposLocal;
import br.edu.ifma.csp.timetable.util.Lookup;

public class LocalViewModel extends ViewModel<Local> {
	
	private TiposLocal tiposLocal;
	
	private List<TipoLocal> colTiposLocal;

	@NotifyChange("colTiposLocal")
	@AfterCompose(superclass=true)
	public void init(Component view) {
		
		tiposLocal = Lookup.dao(TipoLocalDao.class);
		setColTiposLocal(tiposLocal.all());
	}
	
	public List<TipoLocal> getColTiposLocal() {
		return colTiposLocal;
	}
	
	public void setColTiposLocal(List<TipoLocal> colTiposLocal) {
		this.colTiposLocal = colTiposLocal;
	}
}
