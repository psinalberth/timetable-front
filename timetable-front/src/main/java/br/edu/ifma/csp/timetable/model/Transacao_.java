package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-01-28T16:58:13.972-0300")
@StaticMetamodel(Transacao.class)
public class Transacao_ extends Entidade_ {
	public static volatile SingularAttribute<Transacao, Integer> id;
	public static volatile SingularAttribute<Transacao, String> icone;
	public static volatile SingularAttribute<Transacao, String> path;
	public static volatile SingularAttribute<Transacao, String> descricao;
	public static volatile ListAttribute<Transacao, Perfil> perfis;
}
