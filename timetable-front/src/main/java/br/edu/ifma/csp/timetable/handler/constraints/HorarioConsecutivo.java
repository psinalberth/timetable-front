package br.edu.ifma.csp.timetable.handler.constraints;

import java.util.List;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

import br.edu.ifma.csp.timetable.model.choco.Timeslot;

/**
 * <b>Restrição Forte:</b> <br>
 * 
 * Deve haver uma quantidade mínima de ofertas de aula consecutivas para uma disciplina. Para o domínio apresentado, 
 * o mínimo de aulas é {@code 2}. A partir do conjunto <i>H<sub>i</sub></i> de horários de uma disciplina <i>D</i>, é aplicada
 * a restrição {@link Model #arithm(IntVar, String, IntVar, String, int)}, a qual mantém entre os
 * horários <i>H<sub>i1</sub></i> e <i>H<sub>i2</sub></i>, a diferença de valor em 1, ou seja, consecutivos.
 */
public class HorarioConsecutivo {
	
	public static void postConstraint(Model model, List<Timeslot> timeslots) {
		
		for (int i = 0; i < timeslots.size(); i++) {
			
			Timeslot timeslot = timeslots.get(i);
			
			for (int j = 0; j < timeslot.getHorarios().size(); j++) {
				
				IntVar horario2 = null;
				
				if ((j+1) < timeslot.getHorarios().size()) {
					
					horario2 = timeslot.getHorarios().get(++j);
					
					if (j % 2 != 0) {
						model.notMember(horario2, new int[]{0, 9, 18, 27, 36}).post();
					}
				}
			}
		}
	}
}
