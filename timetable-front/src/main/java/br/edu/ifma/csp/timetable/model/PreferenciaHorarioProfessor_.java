package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-10-07T12:47:10.467-0300")
@StaticMetamodel(PreferenciaHorarioProfessor.class)
public class PreferenciaHorarioProfessor_ extends Entidade_ {
	public static volatile SingularAttribute<PreferenciaHorarioProfessor, Integer> id;
	public static volatile SingularAttribute<PreferenciaHorarioProfessor, Professor> professor;
	public static volatile SingularAttribute<PreferenciaHorarioProfessor, Horario> horario;
}
