package br.edu.ifma.csp.timetable.handler.constraints;

import java.util.List;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

import br.edu.ifma.csp.timetable.model.DetalheTimetable;
import br.edu.ifma.csp.timetable.model.Timetable;
import br.edu.ifma.csp.timetable.model.TipoCriterioTimetable;
import br.edu.ifma.csp.timetable.model.choco.Timeslot;

public class OfertaUnicaToDisciplina {
	
	public static Model postConstraint(Model model, Timetable timetable, List<Timeslot> timeslots, List<Timeslot> timeslotsDisciplinasHorarioUnico) {
		
		for (DetalheTimetable detalhe : timetable.getDetalhes()) {
			
			if (detalhe.getTipoCriterioTimetable().getId() == TipoCriterioTimetable.OFERTA_SEMANAL_UNICA) {
				
				Timeslot timeslot = getTimeslotDisciplina(timeslots, detalhe.getDisciplina().getId());
				
				timeslotsDisciplinasHorarioUnico.add(timeslot);
				
				if (timeslot != null) {
					
					for (int i = 0; i < timeslot.getHorarios().size(); i++) {
						
						IntVar h1 = timeslot.getHorarios().get(i);
						IntVar l1 = timeslot.getLocais().get(i);
						IntVar h2 = null;
						IntVar l2 = null;
						
						if ((i + 1) < timeslot.getHorarios().size()) {
							
							h2 = timeslot.getHorarios().get(i + 1);
							l2 = timeslot.getLocais().get(i + 1);
							
							model.arithm(h2, "-", h1, "=", 1).post();
							model.arithm(l2, "=", l1).post();
						}
					}
				}
			}
		}
		
		return model;
	}
	
	private static Timeslot getTimeslotDisciplina(List<Timeslot> timeslots, int disciplina) {
		
		for (Timeslot timeslot : timeslots) {
			
			if (timeslot.getDisciplina().getValue() == disciplina)
				return timeslot;
		}
		
		return null;
	}
}
