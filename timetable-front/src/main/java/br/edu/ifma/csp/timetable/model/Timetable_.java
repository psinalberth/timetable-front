package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-10-22T08:55:22.623-0300")
@StaticMetamodel(Timetable.class)
public class Timetable_ extends Entidade_ {
	public static volatile SingularAttribute<Timetable, Integer> id;
	public static volatile SingularAttribute<Timetable, MatrizCurricular> matrizCurricular;
	public static volatile SingularAttribute<Timetable, Integer> ano;
	public static volatile SingularAttribute<Timetable, Integer> semestre;
	public static volatile ListAttribute<Timetable, Aula> aulas;
	public static volatile ListAttribute<Timetable, DetalheTimetable> detalhes;
}
