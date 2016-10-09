package br.edu.ifma.csp.timetable.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-10-09T11:10:37.145-0300")
@StaticMetamodel(Horario.class)
public class Horario_ extends Entidade_ {
	public static volatile SingularAttribute<Horario, Integer> id;
	public static volatile SingularAttribute<Horario, Dia> dia;
	public static volatile SingularAttribute<Horario, Date> horaInicio;
	public static volatile SingularAttribute<Horario, Date> horaFim;
}
