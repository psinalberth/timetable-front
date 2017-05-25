package br.edu.ifma.csp.timetable.handler.constraints;

import java.util.List;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

import br.edu.ifma.csp.timetable.model.choco.Timeslot;

/**
 * <b>Restrição Forte:</b> <br>
 *  
 * Um professor não pode ser selecionado para ministrar duas aulas de disciplinas diferentes
 * no mesmo horário. Dado um conjunto de <b>{@code n}</b> horários de um professor, é aplicada a restrição 
 * {@link Model #allDifferent(IntVar[], String)}, a qual mantém um professor com apenas uma ocorrência de horário.
 */
public class HorarioOrdenado {
	
	public static void postConstraint(Model model, List<Timeslot> timeslots) {
		
		for (int i = 0; i < timeslots.size(); i++) {
			
			for (int j = 0; j < timeslots.get(i).getHorarios().size(); j++) {
				
				IntVar d1 = timeslots.get(i).getHorarios().get(j);
				IntVar d2 = null;
				
				if ((j+1) < timeslots.get(i).getHorarios().size()) {
					
					d2 = timeslots.get(i).getHorarios().get(j+1);
					model.arithm(d1, "<", d2).post();
				}
			}
		}
	}
}
