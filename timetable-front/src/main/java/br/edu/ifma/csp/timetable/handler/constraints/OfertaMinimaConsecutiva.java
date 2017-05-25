package br.edu.ifma.csp.timetable.handler.constraints;

import java.util.List;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

import br.edu.ifma.csp.timetable.handler.TimetableHandler;
import br.edu.ifma.csp.timetable.model.Timetable;
import br.edu.ifma.csp.timetable.model.choco.Timeslot;

/**
 * <b>Restrição Forte (Pedagógica):</b> <br>
 * 
 * Restrição complementar à {@link TimetableHandler #manterHorariosConsecutivosConstraint()}.
 * Deve haver um intervalo mínimo de dias entre as ofertas consecutivas de aula de uma disciplina. Para o domínio apresentado,
 * o intevalo mínimo é de 1 dia. Para satisfazê-la, dado um conjunto de <b>{@code n}</b> horários de uma disciplina <i>D<sub>i</sub></i>, 
 * aplica-se a restrição {@link Model #arithm(IntVar, String, IntVar, String, int)}, a qual mantém entre os
 * horários <i>H<sub>i1</sub></i> e <i>H<sub>i2</sub></i>, a diferença de valor resultante em, no mínimo, um dia .
 */
public class OfertaMinimaConsecutiva {
	
	public static void postConstraint(Model model, List<Timeslot> timeslots, List<Timeslot> timeslotsDisciplinasHorarioUnico, Timetable timetable, int [] aulas) {
		
		for (int i = 0; i < timeslots.size(); i++) {
			
			Timeslot timeslot = timeslots.get(i);
			
			if (timeslotsDisciplinasHorarioUnico != null) {
				
				if (timeslotsDisciplinasHorarioUnico.contains(timeslot))
					continue;
			}
			
			int aula = aulas[i];
			
			if (aula == 6) {
				
				IntVar horario1 = timeslot.getHorarios().get(0);
				IntVar horario2 = timeslot.getHorarios().get(1);
				IntVar horario3 = timeslot.getHorarios().get(2);
				IntVar horario4 = timeslot.getHorarios().get(3);
				IntVar horario5 = timeslot.getHorarios().get(4);
				IntVar horario6 = timeslot.getHorarios().get(5);
				
				IntVar local1 = timeslot.getLocais().get(0);
				IntVar local2 = timeslot.getLocais().get(1);
				IntVar local3 = timeslot.getLocais().get(2);
				IntVar local4 = timeslot.getLocais().get(3);
				IntVar local5 = timeslot.getLocais().get(4);
				IntVar local6 = timeslot.getLocais().get(5);
				
				model.arithm(local1, "=", local2).post();
				model.arithm(local5, "=", local6).post();
				
				if (timetable.isMesmoLocalDisciplina()) {
					model.arithm(local2, "=", local3).post();
					model.arithm(local3, "=", local4).post();
					model.arithm(local4, "=", local5).post();
				}
				
				model.or(
						model.and(
								model.arithm(horario3, "-", horario1, "=", 2),
								model.arithm(horario4, "-", horario3, timetable.isMesmoHorarioDisciplina() ? "=" : ">=", 16),
								model.arithm(horario6, "-", horario4, "=", 2),
								model.arithm(local2, "=", local3),
								model.arithm(local4, "=", local5)),
						
						model.and(
								model.arithm(horario3, "-", horario2, timetable.isMesmoHorarioDisciplina() ? "=" : ">=", 17),
								model.arithm(horario5, "-", horario4, timetable.isMesmoHorarioDisciplina() ? "=" : ">=", 17),
								model.arithm(local3, "=", local4)
								)
						).post();
				
			
			} else if (aula == 5) {
				
				IntVar horario1 = timeslot.getHorarios().get(0);
				IntVar horario3 = timeslot.getHorarios().get(2);
				IntVar horario4 = timeslot.getHorarios().get(3);
				IntVar horario5 = timeslot.getHorarios().get(4);
				
				IntVar local1 = timeslot.getLocais().get(0);
				IntVar local2 = timeslot.getLocais().get(1);
				IntVar local3 = timeslot.getLocais().get(2);
				IntVar local4 = timeslot.getLocais().get(3);
				IntVar local5 = timeslot.getLocais().get(4);
				
				model.arithm(horario3, "-", horario1, "=", 2).post();
				model.notMember(horario3, new int [] {0, 9, 18, 27, 36}).post();
				model.arithm(horario4, "-", horario3, timetable.isMesmoHorarioDisciplina() ? "=" : ">=", 16).post();
				model.arithm(horario5, "-", horario4, "=", 1).post();
				
				model.arithm(local1, "=", local2).post();
				model.arithm(local2, "=", local3).post();
				model.arithm(local4, "=", local5).post();
				
				if (timetable.isMesmoLocalDisciplina()) {
					model.arithm(local3, "=", local4).post();
				}
								
			} else if (aula == 4) {
				
				IntVar horario1 = timeslot.getHorarios().get(0);
				IntVar horario2 = timeslot.getHorarios().get(1);
				IntVar horario3 = timeslot.getHorarios().get(2);
				IntVar horario4 = timeslot.getHorarios().get(3);
				
				IntVar local1 = timeslot.getLocais().get(0);
				IntVar local2 = timeslot.getLocais().get(1);
				IntVar local3 = timeslot.getLocais().get(2);
				IntVar local4 = timeslot.getLocais().get(3);
				
				model.arithm(horario2, "-", horario1, "=", 1).post();
				model.arithm(horario3, "-", horario2,  timetable.isMesmoHorarioDisciplina() ? "=" : ">=", 17).post();
				model.arithm(horario4, "-", horario3, "=", 1).post();
				
				model.arithm(local1, "=", local2).post();
				model.arithm(local3, "=", local4).post();
				
				if (timetable.isMesmoLocalDisciplina()) {
					model.arithm(local2, "=", local3).post();
				}
				
			} else if (aula == 3) {
				
				IntVar horario1 = timeslot.getHorarios().get(0);
				IntVar horario2 = timeslot.getHorarios().get(1);
				IntVar horario3 = timeslot.getHorarios().get(2);
				
				IntVar local1 = timeslot.getLocais().get(0);
				IntVar local2 = timeslot.getLocais().get(1);
				IntVar local3 = timeslot.getLocais().get(2);
				
				model.arithm(horario2, "-", horario1, "=", 1).post();
				model.arithm(horario3, "-", horario2, "=", 1).post();
				
				model.arithm(local1, "=", local2).post();
				model.arithm(local2, "=", local3).post();
				
				model.notMember(horario3, new int [] {0, 9, 18, 27, 36}).post();
				model.notMember(horario1, new int [] {8, 17, 24, 32, 40}).post();
				
			} else if (aula == 2) {
				
				IntVar horario1 = timeslot.getHorarios().get(0);
				IntVar horario2 = timeslot.getHorarios().get(1);
				
				IntVar local1 = timeslot.getLocais().get(0);
				IntVar local2 = timeslot.getLocais().get(1);
				
				model.arithm(horario2, "-", horario1, "=", 1).post();
				model.arithm(local1, "=", local2).post();
			}
		}
	}
}
