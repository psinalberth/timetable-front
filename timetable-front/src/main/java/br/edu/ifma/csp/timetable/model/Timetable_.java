package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-04-10T19:50:24.206-0300")
@StaticMetamodel(Timetable.class)
public class Timetable_ extends Entidade_ {
	public static volatile SingularAttribute<Timetable, Integer> id;
	public static volatile SingularAttribute<Timetable, MatrizCurricular> matrizCurricular;
	public static volatile SingularAttribute<Timetable, Integer> ano;
	public static volatile SingularAttribute<Timetable, Integer> semestre;
	public static volatile SingularAttribute<Timetable, Boolean> mesmoHorarioDisciplina;
	public static volatile SingularAttribute<Timetable, Boolean> mesmoLocalDisciplina;
	public static volatile ListAttribute<Timetable, DetalheTimetable> detalhes;
	public static volatile SingularAttribute<Timetable, Boolean> horariosIndisponiveisPermitidos;
}
