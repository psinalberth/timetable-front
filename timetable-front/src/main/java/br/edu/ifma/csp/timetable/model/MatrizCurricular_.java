package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-07-26T09:56:25.968-0300")
@StaticMetamodel(MatrizCurricular.class)
public class MatrizCurricular_ extends Entidade_ {
	public static volatile SingularAttribute<MatrizCurricular, Integer> id;
	public static volatile SingularAttribute<MatrizCurricular, Integer> ano;
	public static volatile SingularAttribute<MatrizCurricular, String> turno;
	public static volatile SingularAttribute<MatrizCurricular, Integer> semestres;
	public static volatile SingularAttribute<MatrizCurricular, Curso> curso;
}
