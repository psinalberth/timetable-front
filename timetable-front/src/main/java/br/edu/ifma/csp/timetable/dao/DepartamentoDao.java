package br.edu.ifma.csp.timetable.dao;

import javax.ejb.Stateless;

import br.edu.ifma.csp.timetable.model.Departamento;
import br.edu.ifma.csp.timetable.repository.Departamentos;

@Stateless
public class DepartamentoDao extends RepositoryDao<Departamento> implements Departamentos {

}
