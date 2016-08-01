package br.edu.ifma.csp.timetable.dao;

import javax.ejb.Stateless;

import br.edu.ifma.csp.timetable.model.Curso;
import br.edu.ifma.csp.timetable.repository.CursoRepository;

@Stateless
public class CursoDao extends RepositoryDao<Curso> implements CursoRepository {

}
