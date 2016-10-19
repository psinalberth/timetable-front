package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-10-19T19:04:16.896-0300")
@StaticMetamodel(Local.class)
public class Local_ extends Entidade_ {
	public static volatile SingularAttribute<Local, Integer> id;
	public static volatile SingularAttribute<Local, String> nome;
	public static volatile SingularAttribute<Local, Integer> capacidade;
	public static volatile SetAttribute<Local, Aula> aulas;
	public static volatile SingularAttribute<Local, TipoLocal> tipoLocal;
	public static volatile SingularAttribute<Local, Departamento> departamento;
}
