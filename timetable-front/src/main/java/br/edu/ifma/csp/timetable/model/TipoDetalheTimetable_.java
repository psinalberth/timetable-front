package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-02-24T20:11:55.507-0300")
@StaticMetamodel(TipoDetalheTimetable.class)
public class TipoDetalheTimetable_ extends Entidade_ {
	public static volatile SingularAttribute<TipoDetalheTimetable, Integer> id;
	public static volatile SingularAttribute<TipoDetalheTimetable, String> descricao;
	public static volatile ListAttribute<TipoDetalheTimetable, TipoCriterioTimetable> tiposCriterioTimetable;
}
