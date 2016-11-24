package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-11-18T16:08:52.318-0300")
@StaticMetamodel(Timetable.class)
public class Timetable_ extends Entidade_ {
	public static volatile SingularAttribute<Timetable, Integer> id;
	public static volatile SingularAttribute<Timetable, MatrizCurricular> matrizCurricular;
	public static volatile SingularAttribute<Timetable, Integer> ano;
	public static volatile SingularAttribute<Timetable, Integer> semestre;
	public static volatile ListAttribute<Timetable, DetalheTimetable> detalhes;
}
