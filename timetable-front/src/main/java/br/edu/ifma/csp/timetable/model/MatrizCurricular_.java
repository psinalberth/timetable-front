package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-10-12T14:02:39.281-0300")
@StaticMetamodel(MatrizCurricular.class)
public class MatrizCurricular_ extends Entidade_ {
	public static volatile SingularAttribute<MatrizCurricular, Integer> id;
	public static volatile SingularAttribute<MatrizCurricular, Integer> ano;
	public static volatile SingularAttribute<MatrizCurricular, Integer> semestres;
	public static volatile SingularAttribute<MatrizCurricular, Curso> curso;
	public static volatile SingularAttribute<MatrizCurricular, Turno> turno;
	public static volatile SetAttribute<MatrizCurricular, Periodo> periodos;
}
