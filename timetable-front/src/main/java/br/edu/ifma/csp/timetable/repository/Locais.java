package br.edu.ifma.csp.timetable.repository;

import java.util.List;

import br.edu.ifma.csp.timetable.model.Departamento;
import br.edu.ifma.csp.timetable.model.Local;
import br.edu.ifma.csp.timetable.model.TipoLocal;

public interface Locais extends Repository<Local> {
	
	public List<Local> allByDepartamento(Departamento departamento);
	
	public List<Local> allByTipoLocal(TipoLocal tipoLocal);
}
