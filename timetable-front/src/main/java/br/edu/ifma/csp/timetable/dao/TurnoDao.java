package br.edu.ifma.csp.timetable.dao;

import javax.ejb.Stateless;

import br.edu.ifma.csp.timetable.model.Turno;
import br.edu.ifma.csp.timetable.repository.Turnos;

@Stateless
public class TurnoDao extends RepositoryDao<Turno> implements Turnos {

}
