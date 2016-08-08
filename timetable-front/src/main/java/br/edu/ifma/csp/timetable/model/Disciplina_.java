package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-08-07T12:38:52.131-0300")
@StaticMetamodel(Disciplina.class)
public class Disciplina_ extends Entidade_ {
	public static volatile SingularAttribute<Disciplina, Integer> id;
	public static volatile SingularAttribute<Disciplina, String> descricao;
	public static volatile SetAttribute<Disciplina, Aula> aulas;
	public static volatile SetAttribute<Disciplina, PreferenciaDisciplinaProfessor> preferencias;
	public static volatile SetAttribute<Disciplina, DetalheDisciplina> detalhes;
}
