package br.edu.ifma.csp.timetable.handler.constraints;

import java.util.ArrayList;
import java.util.List;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

import br.edu.ifma.csp.timetable.model.choco.Timeslot;

/**
 * <b>Restrição Forte:</b> <br>
 *  
 * As ofertas de aula para disciplinas de mesmo período (turma) não devem estar sobrepostas.
 * A partir do conjunto <i>D</i> de <b>{@code m}</b> de disciplinas selecionadas e outro conjunto <i>H<sub>D</sub></i> de 
 * horários correspondentes, é realizada a associação e agrupamento de todos os horários por turma e, em seguida, aplica-se a 
 * restrição {@link Model #allDifferent(IntVar[], String)}, a qual mantém apenas uma oferta de aula por horário.
 */
public class HorarioUnicoToSemestre {
	
	public static void postConstraint(Model model, IntVar [][] varHorariosPeriodo, List<Timeslot> timeslots, int [][] periodos) {
		
		for (int i = 0, index = 0; i < periodos.length; i++) {
					
			List<IntVar> horarios = new ArrayList<IntVar>();
			
			for (int j = index; j < index + periodos[i].length; j++) {
				horarios.addAll(timeslots.get(j).getHorarios());
			}
			
			varHorariosPeriodo[i] = horarios.toArray(new IntVar[horarios.size()]);
			model.allDifferent(varHorariosPeriodo[i], "NEQS").post();
			index += periodos[i].length;
		}
	}
}
