package br.edu.ifma.csp.timetable.dao;

import javax.ejb.Stateless;

import br.edu.ifma.csp.timetable.model.Turma;
import br.edu.ifma.csp.timetable.repository.Turmas;

@Stateless
public class TurmaDao extends RepositoryDao<Turma> implements Turmas {

}
