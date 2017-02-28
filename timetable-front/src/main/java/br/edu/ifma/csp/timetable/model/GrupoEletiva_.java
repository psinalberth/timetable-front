package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-02-12T12:44:47.012-0300")
@StaticMetamodel(GrupoEletiva.class)
public class GrupoEletiva_ extends Entidade_ {
	public static volatile SingularAttribute<GrupoEletiva, Integer> id;
	public static volatile SingularAttribute<GrupoEletiva, String> codigo;
	public static volatile SingularAttribute<GrupoEletiva, Curso> curso;
}
