package br.edu.ifma.csp.timetable.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-03-09T22:01:04.678-0300")
@StaticMetamodel(DetalheTimetable.class)
public class DetalheTimetable_ extends Entidade_ {
	public static volatile SingularAttribute<DetalheTimetable, Integer> id;
	public static volatile SingularAttribute<DetalheTimetable, TipoDetalheTimetable> tipoDetalheTimetable;
	public static volatile SingularAttribute<DetalheTimetable, TipoCriterioTimetable> tipoCriterioTimetable;
	public static volatile SingularAttribute<DetalheTimetable, Professor> professor;
	public static volatile SingularAttribute<DetalheTimetable, Disciplina> disciplina;
	public static volatile SingularAttribute<DetalheTimetable, Local> local;
	public static volatile SingularAttribute<DetalheTimetable, Periodo> periodo;
	public static volatile SingularAttribute<DetalheTimetable, Date> horario;
	public static volatile SingularAttribute<DetalheTimetable, Timetable> timetable;
}
