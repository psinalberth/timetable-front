package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-01-28T08:24:52.165-0300")
@StaticMetamodel(Perfil.class)
public class Perfil_ extends Entidade_ {
	public static volatile SingularAttribute<Perfil, Integer> id;
	public static volatile SingularAttribute<Perfil, String> nome;
	public static volatile SingularAttribute<Perfil, String> descricao;
	public static volatile ListAttribute<Perfil, Transacao> transacoes;
}
