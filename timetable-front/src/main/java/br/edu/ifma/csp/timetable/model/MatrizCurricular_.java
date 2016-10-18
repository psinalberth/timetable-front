package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-10-17T20:13:09.465-0300")
@StaticMetamodel(MatrizCurricular.class)
public class MatrizCurricular_ extends Entidade_ {
	public static volatile SingularAttribute<MatrizCurricular, Integer> id;
	public static volatile SingularAttribute<MatrizCurricular, Integer> ano;
	public static volatile SingularAttribute<MatrizCurricular, Integer> semestres;
	public static volatile SingularAttribute<MatrizCurricular, Curso> curso;
	public static volatile SingularAttribute<MatrizCurricular, Turno> turno;
	public static volatile ListAttribute<MatrizCurricular, Periodo> periodos;
}
