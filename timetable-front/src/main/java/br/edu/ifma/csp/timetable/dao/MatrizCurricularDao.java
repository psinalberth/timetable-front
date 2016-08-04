package br.edu.ifma.csp.timetable.dao;

import javax.ejb.Stateless;

import br.edu.ifma.csp.timetable.model.MatrizCurricular;
import br.edu.ifma.csp.timetable.repository.MatrizesCurriculares;

@Stateless
public class MatrizCurricularDao extends RepositoryDao<MatrizCurricular> implements MatrizesCurriculares {

}
