package br.edu.ifma.csp.timetable.dao;

import java.util.List;

import javax.ejb.Stateless;

import br.edu.ifma.csp.timetable.model.Departamento;
import br.edu.ifma.csp.timetable.model.Local;
import br.edu.ifma.csp.timetable.model.TipoLocal;
import br.edu.ifma.csp.timetable.repository.Locais;

@Stateless
public class LocalDao extends RepositoryDao<Local> implements Locais {

	@SuppressWarnings("unchecked")
	@Override
	public List<Local> allByDepartamento(Departamento departamento) {
		
		String sql = 
		
		"select loc.* from LOCAL loc " +
			"inner join DEPARTAMENTO dep on " +
				"dep.ID_DEPARTAMENTO = loc.ID_DEPARTAMENTO and " +
				"dep.ID_DEPARTAMENTO = :departamentoId";
		
		return this.manager.createNativeQuery(sql, Local.class).setParameter("departamentoId", departamento.getId()).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Local> allByTipoLocal(TipoLocal tipoLocal) {
		
		String sql = 
		
		"select loc.* from LOCAL loc " +
				"inner join TIPO_LOCAL tipo on " +
					"tipo.ID_TIPO_LOCAL = loc.ID_TIPO_LOCAL and " +
					"tipo.ID_TIPO_LOCAL = :tipoLocalId";
			
		return this.manager.createNativeQuery(sql, Local.class).setParameter("tipoLocalId", tipoLocal.getId()).getResultList();
	}
}
