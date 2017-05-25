package br.edu.ifma.csp.timetable.handler.constraints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.variables.IntVar;

import br.edu.ifma.csp.timetable.dao.DisciplinaDao;
import br.edu.ifma.csp.timetable.dao.ProfessorDao;
import br.edu.ifma.csp.timetable.model.DetalheTimetable;
import br.edu.ifma.csp.timetable.model.Horario;
import br.edu.ifma.csp.timetable.model.Professor;
import br.edu.ifma.csp.timetable.model.Timetable;
import br.edu.ifma.csp.timetable.model.TipoCriterioTimetable;
import br.edu.ifma.csp.timetable.model.choco.Timeslot;
import br.edu.ifma.csp.timetable.repository.Disciplinas;
import br.edu.ifma.csp.timetable.repository.Professores;
import br.edu.ifma.csp.timetable.util.Lookup;

public class HorarioIndisponivel {
	
	public static void postConstraint(Model model, List<Timeslot> timeslots, Timetable timetable, List<Horario> colHorarios, int [] horariosId) {
		
		Disciplinas disciplinas = Lookup.dao(DisciplinaDao.class);
		Professores professores = Lookup.dao(ProfessorDao.class);
		
		for (int i = 0; i < timeslots.size(); i ++) {
			
			Tuples tuples = new Tuples(true);
			
			Timeslot timeslot = timeslots.get(i);
			
			List<DetalheTimetable> detalhes = getDetalhesCriterioDisciplina(timetable, timeslot.getDisciplina().getValue());
			List<Professor> professoresDisciplina = professores.allByPreferenciaDisciplina(disciplinas.byId(timeslot.getDisciplina().getValue()));
			
			if (detalhes.size() > 0) {
				
				for (DetalheTimetable detalhe : detalhes) {
					
					if (TipoCriterioTimetable.DISCIPLINA_LECIONADA == detalhe.getTipoCriterioTimetable().getId()) {
						
						professoresDisciplina.removeIf(new Predicate<Professor>() {

							@Override
							public boolean test(Professor professor) {
								return professor.getId() != detalhe.getProfessor().getId();
							}
						});
						
					} else if (TipoCriterioTimetable.DISCIPLINA_NAO_LECIONADA == detalhe.getTipoCriterioTimetable().getId()) {
						
						professoresDisciplina.removeIf(new Predicate<Professor>() {

							@Override
							public boolean test(Professor professor) {
								return professor.getId() == detalhe.getProfessor().getId();
							}
						});
					}
				}
			}
			
			for (Professor professor : professoresDisciplina) {
				
				if (professor.getHorariosIndisponiveis() != null && professor.getHorariosIndisponiveis().size() > 0) {
					
					int [] horariosDisponiveis = getHorariosDisponiveisProfessor(professor, colHorarios, horariosId);
						
					for (int j = 0; j < horariosDisponiveis.length; j++) {
						tuples.add(professor.getId(), horariosDisponiveis[j]);
					}
					
					if (timetable.isHorariosIndisponiveisPermitidos()) {
						
						int [] horariosIndisponiveis = getHorariosIndisponiveisProfessor(professor, colHorarios);
						
						for (int j = 0; j < horariosIndisponiveis.length; j++) {
							tuples.add(professor.getId(), horariosIndisponiveis[j]);
						}	
					}

				} else {
					
					for (int j = 0; j < horariosId.length; j++) {
						tuples.add(professor.getId(), j);
					}
				}
			}
			
			for (int j = 0; j < timeslot.getHorarios().size(); j++) {
				
				IntVar horario = timeslot.getHorarios().get(j);
				
				model.table(new IntVar[] {timeslot.getProfessor(), horario}, tuples).post();
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
	
	private static List<DetalheTimetable> getDetalhesCriterioDisciplina(Timetable timetable, int disciplinaId) {
		
		List<DetalheTimetable> list = new ArrayList<DetalheTimetable>();
		
		for (DetalheTimetable detalhe : timetable.getDetalhes()) {
			
			if (detalhe.isTipoDetalheDisciplina()) {
				
				if (detalhe.getDisciplina().getId() == disciplinaId) {
					list.add(detalhe);
				}
			}
		}
		
		return list;
	}
}
