package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-08-11T10:41:24.074-0300")
@StaticMetamodel(DetalheDisciplina.class)
public class DetalheDisciplina_ extends Entidade_ {
	public static volatile SingularAttribute<DetalheDisciplina, Integer> id;
	public static volatile SingularAttribute<DetalheDisciplina, Integer> creditos;
	public static volatile SingularAttribute<DetalheDisciplina, Integer> cargaHoraria;
	public static volatile SingularAttribute<DetalheDisciplina, MatrizCurricular> matrizCurricular;
	public static volatile SingularAttribute<DetalheDisciplina, Turma> periodo;
	public static volatile SingularAttribute<DetalheDisciplina, Disciplina> disciplina;
	public static volatile SingularAttribute<DetalheDisciplina, Boolean> obrigatoria;
	public static volatile SingularAttribute<DetalheDisciplina, String> codigo;
}
