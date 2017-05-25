package br.edu.ifma.csp.timetable.handler.constraints;

import java.util.ArrayList;
import java.util.List;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

import br.edu.ifma.csp.timetable.model.choco.Timeslot;

/**
 * <b>Restrição Forte:</b> <br>
 * 
 * Deve haver um local e um horário para cada oferta de aula gerada, uma vez que deve ser atendida a restrição de que o mesmo 
 * local não pode ser alocado no mesmo horário para mais de uma oferta.
 * Para assegurar que esta restrição seja atendida define-se para cada <i>timeslot</i> gerado na modelagem inicial:
 * 
 * <ul>
 * 	<li>Uma variável {@code z}<sub>i</sub>, a qual varia entre {@code 0} e {@code 100000};</li>
 * 	<li>Uma <i>view</i>, na forma {@code z}<sub>i</sub>{@code = x}<sub>i</sub> &times {@code 1000 + y}<sub>i</sub>;</li>
 * </ul>
 * 
 * Ao final, para cada variável {@code z}<sub>i</sub> criada, é aplicada a restrição {@link Model #allDifferent(IntVar[], String)}, 
 * a qual é responsável por assegurar que os valores de uma coleção de variáveis sejam diferentes entre si.
 */
public class HorarioUnicoToLocal {
	
	public static void postConstraint(Model model, List<Timeslot> timeslots) {
		
		List<IntVar> list = new ArrayList<IntVar>();
				
		for (int i = 0; i < timeslots.size(); i++) {
			
			for (int j = 0; j < timeslots.get(i).getHorarios().size(); j++) {
				
				IntVar horario = timeslots.get(i).getHorarios().get(j);
				IntVar local = timeslots.get(i).getLocais().get(j);
				
				IntVar z = model.intVar("z", 0, 100000);
				
				model.sum(new IntVar[]{model.intScaleView(horario, 1000), local}, "=", z).post();
				
				list.add(z);
			}
		}
		
		model.allDifferent(list.toArray(new IntVar[list.size()]), "NEQS").post();
	}
}
