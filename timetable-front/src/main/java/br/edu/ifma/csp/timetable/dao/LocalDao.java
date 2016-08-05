package br.edu.ifma.csp.timetable.dao;

import javax.ejb.Stateless;

import br.edu.ifma.csp.timetable.model.Local;
import br.edu.ifma.csp.timetable.repository.Locais;

@Stateless
public class LocalDao extends RepositoryDao<Local> implements Locais {

}
