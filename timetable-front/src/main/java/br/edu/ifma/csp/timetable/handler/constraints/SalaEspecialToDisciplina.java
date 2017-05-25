package br.edu.ifma.csp.timetable.handler.constraints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.extension.Tuples;

import br.edu.ifma.csp.timetable.dao.LocalDao;
import br.edu.ifma.csp.timetable.dao.TipoLocalDao;
import br.edu.ifma.csp.timetable.model.Departamento;
import br.edu.ifma.csp.timetable.model.DetalheDisciplina;
import br.edu.ifma.csp.timetable.model.Local;
import br.edu.ifma.csp.timetable.model.Timetable;
import br.edu.ifma.csp.timetable.model.TipoLocal;
import br.edu.ifma.csp.timetable.model.choco.Timeslot;
import br.edu.ifma.csp.timetable.repository.Locais;
import br.edu.ifma.csp.timetable.repository.TiposLocal;
import br.edu.ifma.csp.timetable.util.Lookup;

public class SalaEspecialToDisciplina {
	
	public static void postConstraint(Model model, List<Timeslot> timeslots, List<DetalheDisciplina> colDetalhes, Timetable timetable) {
		
		TiposLocal tiposLocal = Lookup.dao(TipoLocalDao.class);
		Locais locais = Lookup.dao(LocalDao.class);
		
		List<Local> colLocais = locais.all();
		
		Tuples tuples = new Tuples(true);
		
		TipoLocal salaAula = tiposLocal.byId(2);
		
		int [] laboratoriosId = allByDepartamento(colLocais, timetable.getMatrizCurricular().getCurso().getDepartamento());
		int [] salasId = allByTipoLocal(colLocais, salaAula);
		
		for (DetalheDisciplina detalhe : colDetalhes) {
			
			if (detalhe.isDisciplinaLaboratorio()) {
				
				for (int i = 0; i < laboratoriosId.length; i++) {
					tuples.add(detalhe.getDisciplina().getId(), laboratoriosId[i]);
				}
				
				for (int i = 0; i < salasId.length; i++) {
					tuples.add(detalhe.getDisciplina().getId(), salasId[i]);
				} 
				
			} else {
				
				for (int i = 0; i < salasId.length; i++) {
					tuples.add(detalhe.getDisciplina().getId(), salasId[i]);
				}
			}
		}
		
		for (int i = 0; i < timeslots.size(); i++) {
			
			Timeslot timeslot = timeslots.get(i);
			
			for (int j = 0; j < timeslot.getHorarios().size(); j++) {						
				model.table(timeslot.getDisciplina(), timeslot.getLocais().get(j), tuples).post();
			}
		}
	}
	
	private static int [] allByDepartamento(List<Local> colLocais, Departamento departamento) {
		
		List<Integer> locaisId = new ArrayList<Integer>();
		
		for (Local local : colLocais) {
			
			if (local.getDepartamento() != null && local.getDepartamento().getId() == departamento.getId()) {
				locaisId.add(colLocais.indexOf(local));
			}
		}
		
		return Arrays.stream(locaisId.toArray(new Integer[locaisId.size()])).mapToInt(Integer::intValue).toArray();
	}
	
	private static int [] allByTipoLocal(List<Local> colLocais, TipoLocal tipoLocal) {
		
		List<Integer> locaisId = new ArrayList<Integer>();
		
		for (Local local : colLocais) {
			
			if (local.getTipoLocal().getId() == tipoLocal.getId()) {
				locaisId.add(colLocais.indexOf(local));
			}
		}
		
		return Arrays.stream(locaisId.toArray(new Integer[locaisId.size()])).mapToInt(Integer::intValue).toArray();
	}
}
