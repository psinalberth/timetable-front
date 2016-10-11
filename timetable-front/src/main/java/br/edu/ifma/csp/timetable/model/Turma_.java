package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-10-10T21:14:53.999-0300")
@StaticMetamodel(Turma.class)
public class Turma_ extends Entidade_ {
	public static volatile SingularAttribute<Turma, Integer> id;
	public static volatile SingularAttribute<Turma, Periodo> periodo;
	public static volatile SingularAttribute<Turma, Integer> ano;
	public static volatile SingularAttribute<Turma, Integer> semestre;
	public static volatile SingularAttribute<Turma, Integer> qtdAlunos;
}
