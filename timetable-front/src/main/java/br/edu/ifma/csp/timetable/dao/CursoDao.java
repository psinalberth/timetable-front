package br.edu.ifma.csp.timetable.dao;

import javax.ejb.Stateful;

import br.edu.ifma.csp.timetable.model.Curso;
import br.edu.ifma.csp.timetable.repository.Cursos;

@Stateful
public class CursoDao extends RepositoryDao<Curso> implements Cursos {

}
