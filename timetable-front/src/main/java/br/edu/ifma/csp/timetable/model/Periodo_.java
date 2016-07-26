package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-07-26T09:55:18.293-0300")
@StaticMetamodel(Periodo.class)
public class Periodo_ extends Entidade_ {
	public static volatile SingularAttribute<Periodo, Integer> id;
	public static volatile SingularAttribute<Periodo, String> codigo;
	public static volatile SingularAttribute<Periodo, Integer> qtdAlunos;
	public static volatile SingularAttribute<Periodo, Curso> curso;
	public static volatile SetAttribute<Periodo, DetalheDisciplina> detalhes;
}
