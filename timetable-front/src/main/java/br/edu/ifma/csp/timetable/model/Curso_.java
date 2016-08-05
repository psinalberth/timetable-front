package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-08-05T07:58:21.864-0300")
@StaticMetamodel(Curso.class)
public class Curso_ extends Entidade_ {
	public static volatile SingularAttribute<Curso, Integer> id;
	public static volatile SingularAttribute<Curso, String> codigo;
	public static volatile SingularAttribute<Curso, String> nome;
	public static volatile SingularAttribute<Curso, String> descricao;
	public static volatile SetAttribute<Curso, Turma> periodos;
	public static volatile SetAttribute<Curso, MatrizCurricular> matrizes;
}
