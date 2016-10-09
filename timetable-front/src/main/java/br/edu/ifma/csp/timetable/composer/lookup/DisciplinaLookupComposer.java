package br.edu.ifma.csp.timetable.composer.lookup;

import org.zkoss.bind.annotation.Init;

import br.edu.ifma.csp.timetable.dao.DisciplinaDao;
import br.edu.ifma.csp.timetable.model.Disciplina;
import br.edu.ifma.csp.timetable.repository.Disciplinas;
import br.edu.ifma.csp.timetable.util.Lookup;

public class DisciplinaLookupComposer extends LookupComposer<Disciplina> {
	
	private static final long serialVersionUID = -3212074239486472736L;
	
	private Disciplinas disciplinas;
	
	private String descricao;
	
	@Init
	public void init() {
		disciplinas = Lookup.dao(DisciplinaDao.class);
		setCol(disciplinas.all());
		getBinder().notifyChange(this, "*");
	}
	
	public void search() {
		
		boolean like = false;
		
		if (getDescricao() != null && !getDescricao().isEmpty()) {
			setCol(disciplinas.allBy("descricao", getDescricao(), like));
			
		} else {
			setCol(disciplinas.all());
		}
		
		getBinder().notifyChange(this, "*");
	}
	
	public void clear() {
		
		setDescricao(null);
		
		search();
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
