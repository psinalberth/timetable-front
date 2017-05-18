package br.edu.ifma.csp.timetable.dao;

import javax.ejb.Stateless;

import br.edu.ifma.csp.timetable.model.TipoRelatorio;
import br.edu.ifma.csp.timetable.repository.TiposRelatorio;

@Stateless
public class TipoRelatorioDao extends RepositoryDao<TipoRelatorio> implements TiposRelatorio {

}
