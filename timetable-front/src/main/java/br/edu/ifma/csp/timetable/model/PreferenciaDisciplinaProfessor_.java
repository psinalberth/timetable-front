package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-07-26T09:55:18.294-0300")
@StaticMetamodel(PreferenciaDisciplinaProfessor.class)
public class PreferenciaDisciplinaProfessor_ extends Entidade_ {
	public static volatile SingularAttribute<PreferenciaDisciplinaProfessor, Integer> id;
	public static volatile SingularAttribute<PreferenciaDisciplinaProfessor, Professor> professor;
	public static volatile SingularAttribute<PreferenciaDisciplinaProfessor, Disciplina> disciplina;
}
