package br.edu.ifma.csp.timetable.handler.constraints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

import br.edu.ifma.csp.timetable.dao.DisciplinaDao;
import br.edu.ifma.csp.timetable.dao.ProfessorDao;
import br.edu.ifma.csp.timetable.model.Horario;
import br.edu.ifma.csp.timetable.model.Professor;
import br.edu.ifma.csp.timetable.model.Timetable;
import br.edu.ifma.csp.timetable.model.choco.Timeslot;
import br.edu.ifma.csp.timetable.repository.Disciplinas;
import br.edu.ifma.csp.timetable.repository.Professores;
import br.edu.ifma.csp.timetable.util.Lookup;

public class HorarioIndisponivelProfessor {
	
	public static void postConstraint(Model model, List<Timeslot> timeslots, Timetable timetable, List<Horario> colHorarios, int [] horariosId, List<IntVar> totalPenalizacoes) {
		
		Disciplinas disciplinas = Lookup.dao(DisciplinaDao.class);
		Professores professores = Lookup.dao(ProfessorDao.class);
		
	//	List<IntVar> totalPenalizacoes = new ArrayList<IntVar>();
		
		for (int i = 0; i < timeslots.size(); i++) {
			
			Timeslot timeslot = timeslots.get(i);
			
			List<Professor> professoresDisciplina = professores.allByPreferenciaDisciplina(disciplinas.byId(timeslot.getDisciplina().getValue()));
			
			for (Professor professor : professoresDisciplina) {
				
				if (professor.getHorariosIndisponiveis() != null && professor.getHorariosIndisponiveis().size() > 0) {
				
					int [] horariosIndisponiveis = getHorariosIndisponiveisProfessor(professor, colHorarios);
					int [] penalizacoes = IntStream.generate(() -> 0).limit(colHorarios.size()).toArray();
					
					IntVar [] penalizacoesProfessor = model.intVarArray("p" + professor.getNome(), timeslot.getHorarios().size(), new int [] {0, 1});
					
					for (int j = 0; j < horariosIndisponiveis.length; j++) {
						penalizacoes[horariosIndisponiveis[j]] = 5;
					}
					
					for (int j = 0; j < timeslot.getHorarios().size(); j++) {
						
						IntVar horario = timeslot.getHorarios().get(j);
						
						//IntVar pe = model.intVar("p" + professor.getNome() + "j" +j, 0, 10);
						
						/*model.ifThen(model.and(model.arithm(timeslot.getProfessor(), "=", professor.getId()), 
											   model.member(horario, horariosIndisponiveis)), model.arithm(pe, "=", 10));*/
						
						/*model.ifThen(model.arithm(timeslot.getProfessor(), "=", professor.getId()), 
								     model.element(pe, penalizacoes, horario));*/
						
						model.ifThen(model.arithm(timeslot.getProfessor(), "=", professor.getId()), model.element(penalizacoesProfessor[j], penalizacoes, horario));
						//model.element(penalizacoesProfessor[j], penalizacoes, horario).post();
						//totalPenalizacoes.add(pe);
					}
					
					totalPenalizacoes.addAll(Arrays.asList(penalizacoesProfessor));
				}
			}
		}
	}
	
	private static int [] getHorariosIndisponiveisProfessor(Professor professor, List<Horario> colHorarios) {
		
		List<Integer> lista = new ArrayList<Integer>();
		
		for (int i = 0; i < colHorarios.size(); i++) {
			
			for (Horario horario : professor.getHorariosIndisponiveis()) {
				
				if (horario.getId() == colHorarios.get(i).getId()) {
					lista.add(i);
				}
			}
		}
		
		return Arrays.stream(lista.toArray(new Integer[lista.size()])).mapToInt(Integer::intValue).toArray();
	}
	
	
	private static int [] getHorariosDisponiveisProfessor(Professor professor, List<Horario> colHorarios, int [] horariosId) {
		
		List<Integer> lista = new ArrayList<Integer>(Arrays.asList(IntStream.rangeClosed(0, horariosId.length - 1).boxed().toArray(Integer[]::new)));
		
		int [] listaIndisponiveis = getHorariosIndisponiveisProfessor(professor, colHorarios);
		
		List<Integer> temp = new ArrayList<Integer>(IntStream.of(listaIndisponiveis).boxed().collect(Collectors.toList()));
		
		lista.removeAll(temp);
		
		return Arrays.stream(lista.toArray(new Integer[lista.size()])).mapToInt(Integer::intValue).toArray();
	}
}
