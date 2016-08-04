package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-08-04T12:28:07.853-0300")
@StaticMetamodel(Turma.class)
public class Turma_ extends Entidade_ {
	public static volatile SingularAttribute<Turma, Integer> id;
	public static volatile SingularAttribute<Turma, String> codigo;
	public static volatile SingularAttribute<Turma, Integer> qtdAlunos;
	public static volatile SingularAttribute<Turma, Curso> curso;
	public static volatile SetAttribute<Turma, DetalheDisciplina> detalhes;
}
