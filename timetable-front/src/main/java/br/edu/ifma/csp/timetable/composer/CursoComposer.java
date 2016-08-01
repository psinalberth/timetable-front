package br.edu.ifma.csp.timetable.composer;

import java.util.List;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.WrongValuesException;
import org.zkoss.zk.ui.util.Clients;

import br.edu.ifma.csp.timetable.dao.CursoDao;
import br.edu.ifma.csp.timetable.model.Curso;
import br.edu.ifma.csp.timetable.repository.CursoRepository;
import br.edu.ifma.csp.timetable.util.Lookup;
import br.edu.ifma.csp.timetable.util.Validations;

public class CursoComposer extends Composer<Curso> {

	private static final long serialVersionUID = 3975369613789993308L;
	
	private CursoRepository cursos;
	
	private List<Curso> col;

	@Init
	public void init() {
		
		cursos = Lookup.dao(CursoDao.class);
		
		col = cursos.all();
	}
	
	@Override
	public void save() {
		
		Validations.validate(getBinder(), entidade);
				
		try {
			
			
			
			this.cursos.save(entidade);
			
			Clients.showNotification("Informações salvas com sucesso.", "info", null, "middle_center", 2000);
			
		} catch (WrongValuesException ex) {	
			ex.printStackTrace();
		}
	}
	
	public void setCol(List<Curso> col) {
		this.col = col;
	}
	
	public List<Curso> getCol() {
		return col;
	}
}
