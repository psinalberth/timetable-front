package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-10-10T21:14:49.694-0300")
@StaticMetamodel(Periodo.class)
public class Periodo_ extends Entidade_ {
	public static volatile SingularAttribute<Periodo, Integer> id;
	public static volatile SingularAttribute<Periodo, String> codigo;
	public static volatile SingularAttribute<Periodo, MatrizCurricular> matrizCurricular;
	public static volatile SetAttribute<Periodo, DetalheDisciplina> detalhes;
	public static volatile SetAttribute<Periodo, Turma> turmas;
}
