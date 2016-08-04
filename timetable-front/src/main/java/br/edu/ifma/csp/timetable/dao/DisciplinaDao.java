package br.edu.ifma.csp.timetable.dao;

import javax.ejb.Stateless;

import br.edu.ifma.csp.timetable.model.Disciplina;
import br.edu.ifma.csp.timetable.repository.Disciplinas;

@Stateless
public class DisciplinaDao extends RepositoryDao<Disciplina> implements Disciplinas {

}
