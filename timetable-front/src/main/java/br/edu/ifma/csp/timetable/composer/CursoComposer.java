package br.edu.ifma.csp.timetable.composer;

import java.util.List;

import org.zkoss.bind.annotation.Init;

import br.edu.ifma.csp.timetable.dao.DepartamentoDao;
import br.edu.ifma.csp.timetable.model.Curso;
import br.edu.ifma.csp.timetable.model.Departamento;
import br.edu.ifma.csp.timetable.repository.Departamentos;
import br.edu.ifma.csp.timetable.util.Lookup;

public class CursoComposer extends Composer<Curso> {

	private static final long serialVersionUID = 3975369613789993308L;
	
	private Departamentos departamentos;
	
	private List<Departamento> colDepartamentos;

	@Init
	public void init() {
		
		departamentos = Lookup.dao(DepartamentoDao.class);
		setColDepartamentos(departamentos.all());
		getBinder().notifyChange(this, "*");
	}
	
	public List<Departamento> getColDepartamentos() {
		return colDepartamentos;
	}
	
	public void setColDepartamentos(List<Departamento> colDepartamentos) {
		this.colDepartamentos = colDepartamentos;
	}
}
