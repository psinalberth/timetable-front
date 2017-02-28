package br.edu.ifma.csp.timetable.dao;

import javax.ejb.Stateless;

import br.edu.ifma.csp.timetable.model.TipoDetalheTimetable;
import br.edu.ifma.csp.timetable.repository.TiposDetalheTimetable;

@Stateless
public class TipoDetalheTimetableDao extends RepositoryDao<TipoDetalheTimetable> implements TiposDetalheTimetable {

}
