package br.edu.ifma.csp.timetable.dao;

import javax.ejb.Stateless;

import br.edu.ifma.csp.timetable.model.TipoLocal;
import br.edu.ifma.csp.timetable.repository.TiposLocal;

@Stateless
public class TipoLocalDao extends RepositoryDao<TipoLocal> implements TiposLocal {

}
