package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-09-28T19:43:45.472-0300")
@StaticMetamodel(Professor.class)
public class Professor_ extends Entidade_ {
	public static volatile SingularAttribute<Professor, Integer> id;
	public static volatile SingularAttribute<Professor, String> nome;
	public static volatile SingularAttribute<Professor, String> endereco;
	public static volatile SingularAttribute<Professor, Departamento> departamento;
	public static volatile SetAttribute<Professor, Aula> aulas;
	public static volatile ListAttribute<Professor, PreferenciaDisciplinaProfessor> preferenciasDisciplina;
	public static volatile ListAttribute<Professor, Horario> horariosIndisponiveis;
	public static volatile ListAttribute<Professor, Disciplina> disciplinasLecionaveis;
}
