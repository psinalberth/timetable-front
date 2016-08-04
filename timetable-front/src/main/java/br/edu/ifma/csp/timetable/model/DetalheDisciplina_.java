package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-07-26T09:55:18.461-0300")
@StaticMetamodel(DetalheDisciplina.class)
public class DetalheDisciplina_ extends Entidade_ {
	public static volatile SingularAttribute<DetalheDisciplina, Integer> id;
	public static volatile SingularAttribute<DetalheDisciplina, Integer> creditos;
	public static volatile SingularAttribute<DetalheDisciplina, Integer> cargaHoraria;
	public static volatile SingularAttribute<DetalheDisciplina, MatrizCurricular> matrizCurricular;
	public static volatile SingularAttribute<DetalheDisciplina, Turma> periodo;
	public static volatile SingularAttribute<DetalheDisciplina, Disciplina> disciplina;
}
