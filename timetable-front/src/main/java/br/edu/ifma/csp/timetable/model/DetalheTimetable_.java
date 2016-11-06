package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-11-06T13:52:31.382-0300")
@StaticMetamodel(DetalheTimetable.class)
public class DetalheTimetable_ extends Entidade_ {
	public static volatile SingularAttribute<DetalheTimetable, Integer> id;
	public static volatile SingularAttribute<DetalheTimetable, String> entidade;
	public static volatile SingularAttribute<DetalheTimetable, Professor> professor;
	public static volatile SingularAttribute<DetalheTimetable, String> criterio;
	public static volatile SingularAttribute<DetalheTimetable, Disciplina> disciplina;
	public static volatile SingularAttribute<DetalheTimetable, Local> local;
	public static volatile SingularAttribute<DetalheTimetable, Periodo> periodo;
	public static volatile SingularAttribute<DetalheTimetable, Horario> horario;
	public static volatile SingularAttribute<DetalheTimetable, String> horarioInicio;
	public static volatile SingularAttribute<DetalheTimetable, Timetable> timetable;
}
