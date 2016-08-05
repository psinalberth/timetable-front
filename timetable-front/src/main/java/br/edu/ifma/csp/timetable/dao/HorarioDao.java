package br.edu.ifma.csp.timetable.dao;

import javax.ejb.Stateless;

import br.edu.ifma.csp.timetable.model.Horario;
import br.edu.ifma.csp.timetable.repository.Horarios;

@Stateless
public class HorarioDao extends RepositoryDao<Horario> implements Horarios {

}
