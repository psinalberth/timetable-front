package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-10-17T20:12:19.971-0300")
@StaticMetamodel(Periodo.class)
public class Periodo_ extends Entidade_ {
	public static volatile SingularAttribute<Periodo, Integer> id;
	public static volatile SingularAttribute<Periodo, Integer> codigo;
	public static volatile SingularAttribute<Periodo, MatrizCurricular> matrizCurricular;
	public static volatile ListAttribute<Periodo, DetalheDisciplina> detalhes;
	public static volatile ListAttribute<Periodo, Turma> turmas;
}
