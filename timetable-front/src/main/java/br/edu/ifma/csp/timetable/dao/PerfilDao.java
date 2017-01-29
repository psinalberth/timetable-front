package br.edu.ifma.csp.timetable.dao;

import javax.ejb.Stateless;

import br.edu.ifma.csp.timetable.model.Perfil;
import br.edu.ifma.csp.timetable.repository.Perfis;

@Stateless
public class PerfilDao extends RepositoryDao<Perfil> implements Perfis {

}
