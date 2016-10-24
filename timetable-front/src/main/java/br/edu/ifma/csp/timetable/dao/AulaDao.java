package br.edu.ifma.csp.timetable.dao;

import javax.ejb.Stateless;

import br.edu.ifma.csp.timetable.model.Aula;
import br.edu.ifma.csp.timetable.repository.Aulas;

@Stateless
public class AulaDao extends RepositoryDao<Aula> implements Aulas {

}
