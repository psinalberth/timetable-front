package br.edu.ifma.csp.timetable.dao;

import java.util.List;

import javax.ejb.Stateless;

import br.edu.ifma.csp.timetable.model.Horario;
import br.edu.ifma.csp.timetable.repository.Horarios;

@Stateless
public class HorarioDao extends RepositoryDao<Horario> implements Horarios {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Horario> all() {
		
		String sql = 
		
		"select * from HORARIO order by field(DIA, 'SEGUNDA', 'TERCA', 'QUARTA', 'QUINTA', 'SEXTA'), HORA_INICIO";
		
		return this.manager.createNativeQuery(sql, Horario.class).getResultList();
	}
}
