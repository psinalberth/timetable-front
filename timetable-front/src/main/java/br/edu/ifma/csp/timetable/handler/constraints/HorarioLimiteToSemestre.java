package br.edu.ifma.csp.timetable.handler.constraints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.extension.Tuples;

import br.edu.ifma.csp.timetable.model.DetalheTimetable;
import br.edu.ifma.csp.timetable.model.Horario;
import br.edu.ifma.csp.timetable.model.Periodo;
import br.edu.ifma.csp.timetable.model.Timetable;
import br.edu.ifma.csp.timetable.model.TipoCriterioTimetable;
import br.edu.ifma.csp.timetable.model.choco.Timeslot;

public class HorarioLimiteToSemestre {
	
	public static void postConstraint(Model model, List<Timeslot> timeslots, Timetable timetable, List<Horario> colHorarios, int [][] periodos) {
		
		for (DetalheTimetable detalhe : timetable.getDetalhes()) {
			
			Tuples tuples = new Tuples(true);
			
			if (detalhe.isTipoCriterioHorario()) {
				
				Periodo periodo = detalhe.getPeriodo();
				Date horario = detalhe.getHorario();
				
				int horarios [] = recuperaHorarios(detalhe.getTipoCriterioTimetable(), horario, colHorarios);
				
				List<Timeslot> list = getTimeslotsPeriodo(timeslots, periodos, periodo.getCodigo());
				
				for (int i = 0; i < list.size(); i++) {
					
					Timeslot timeslot = list.get(i);
					
					for (int j = 0; j < horarios.length; j++) {						
						tuples.add(timeslot.getDisciplina().getValue(), horarios[j]);
					}
				}
				
				for (int i = 0; i < list.size(); i++) {
					
					Timeslot timeslot = list.get(i);
					
					for (int j = 0; j < timeslot.getHorarios().size(); j++) {						
						model.table(timeslot.getDisciplina(), timeslot.getHorarios().get(j), tuples).post();
					}
				}
			}
		}
	}
	
	private static int [] recuperaHorarios(TipoCriterioTimetable tipoCriterio, Date horario, List<Horario> colHorarios) {
		
		List<Integer> list = new ArrayList<Integer>();
		
		for (int i = 0; i < colHorarios.size(); i++) {
			
			Horario h = colHorarios.get(i);
			
			if (tipoCriterio.getId() == TipoCriterioTimetable.HORARIO_DE_INICIO_ATE) {
				
				if (h.getHoraInicio().before(horario)) {
					list.add(i);
				}
				
			} else if (tipoCriterio.getId() == TipoCriterioTimetable.HORARIO_DE_INICIO_APOS) {
				
				if (h.getHoraInicio().after(horario)) {
					list.add(i);
				}
			}
		}
		
		return Arrays.stream(list.toArray(new Integer[list.size()])).mapToInt(Integer::intValue).toArray();
	}
	
	private static List<Timeslot> getTimeslotsPeriodo(List<Timeslot> timeslots, int [][] periodos, int periodo) {
		
		int disciplinasPeriodo[] = getDisciplinasPorPeriodo(periodos, periodo);
		
		List<Timeslot> list = new ArrayList<Timeslot>(disciplinasPeriodo.length);
		
		for (int i = 0; i < disciplinasPeriodo.length; i++) {
			
			Timeslot timeslot = getTimeslotDisciplina(timeslots, disciplinasPeriodo[i]);
			
			list.add(timeslot);
		}
		
		return list;
	}
	
	private static Timeslot getTimeslotDisciplina(List<Timeslot> timeslots, int disciplina) {
		
		for (Timeslot timeslot : timeslots) {
			
			if (timeslot.getDisciplina().getValue() == disciplina)
				return timeslot;
		}
		
		return null;
	}
	
	private static int [] getDisciplinasPorPeriodo(int[][] periodos, int periodo) {
		return periodos[periodo-1];
	}
}
