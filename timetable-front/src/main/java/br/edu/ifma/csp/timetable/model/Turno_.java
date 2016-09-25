package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-09-24T22:09:05.296-0300")
@StaticMetamodel(Turno.class)
public class Turno_ extends Entidade_ {
	public static volatile SingularAttribute<Turno, Integer> id;
	public static volatile SingularAttribute<Turno, String> codigo;
	public static volatile SingularAttribute<Turno, String> nome;
	public static volatile SingularAttribute<Turno, String> descricao;
	public static volatile SetAttribute<Turno, MatrizCurricular> matrizes;
}
