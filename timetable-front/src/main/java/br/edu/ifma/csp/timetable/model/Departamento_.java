package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-09-24T22:03:50.768-0300")
@StaticMetamodel(Departamento.class)
public class Departamento_ extends Entidade_ {
	public static volatile SingularAttribute<Departamento, Integer> id;
	public static volatile SingularAttribute<Departamento, String> codigo;
	public static volatile SingularAttribute<Departamento, String> descricao;
	public static volatile SingularAttribute<Departamento, String> nome;
	public static volatile SetAttribute<Departamento, Professor> professores;
	public static volatile SetAttribute<Departamento, Curso> cursos;
}
