package br.edu.ifma.csp.timetable.dao;

import javax.ejb.Stateless;

import br.edu.ifma.csp.timetable.model.Timetable;
import br.edu.ifma.csp.timetable.repository.Timetables;

@Stateless
public class TimetableDao extends RepositoryDao<Timetable> implements Timetables {

}
