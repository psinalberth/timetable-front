package br.edu.ifma.csp.timetable.dao;

import javax.ejb.Stateless;

import br.edu.ifma.csp.timetable.model.Professor;
import br.edu.ifma.csp.timetable.repository.Professores;

@Stateless
public class ProfessorDao extends RepositoryDao<Professor> implements Professores {

}
