package br.edu.ifma.csp.timetable.handler.constraints;

import java.util.ArrayList;
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
public class HorarioUnicoToProfessor {
	
	public static Model postConstraint(Model model, List<Timeslot> timeslots, int professoresId[]) {
		
		List<IntVar> listaHorarios = new ArrayList<IntVar>();
		List<IntVar> listaProfessores = new ArrayList<IntVar>();
		
		int CARGA_HORARIA_MAXIMA = 14;
		
		for (int i = 0; i < timeslots.size(); i++) {
			
			for (int j = 0; j < timeslots.get(i).getHorarios().size(); j++) {
				
				IntVar horario = timeslots.get(i).getHorarios().get(j);
				IntVar professor = timeslots.get(i).getProfessor();
				
				IntVar produtoProfessorHorario = model.intVar("produtoProfessorHorario", 0, 100000);
				
				listaProfessores.add(professor);
				
				model.sum(new IntVar[]{model.intScaleView(horario, 1000), professor}, "=", produtoProfessorHorario).post();
				
				listaHorarios.add(produtoProfessorHorario);
			}
		}
		
		for (int i = 0; i < professoresId.length; i++) {
			model.count(professoresId[i], listaProfessores.toArray(new IntVar[listaProfessores.size()]), model.intVar(0, CARGA_HORARIA_MAXIMA)).post();;
		}
		
		model.allDifferent(listaHorarios.toArray(new IntVar[listaHorarios.size()]), "NEQS").post();
		
		return model;
	}
	
}
