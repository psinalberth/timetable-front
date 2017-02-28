package br.edu.ifma.csp.timetable.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-02-12T12:44:47.070-0300")
@StaticMetamodel(TipoCriterioTimetable.class)
public class TipoCriterioTimetable_ extends Entidade_ {
	public static volatile SingularAttribute<TipoCriterioTimetable, Integer> id;
	public static volatile SingularAttribute<TipoCriterioTimetable, String> descricao;
	public static volatile SingularAttribute<TipoCriterioTimetable, TipoDetalheTimetable> tipoDetalheTimetable;
}
