package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-10-21T19:54:59.785-0300")
@StaticMetamodel(Aula.class)
public class Aula_ extends Entidade_ {
	public static volatile SingularAttribute<Aula, Integer> id;
	public static volatile SingularAttribute<Aula, Professor> professor;
	public static volatile SingularAttribute<Aula, Disciplina> disciplina;
	public static volatile SingularAttribute<Aula, Local> local;
	public static volatile SingularAttribute<Aula, Horario> horario;
	public static volatile SingularAttribute<Aula, Timetable> timetable;
}
