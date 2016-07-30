package br.edu.ifma.csp.timetable.composer;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.util.Clients;

import br.edu.ifma.csp.timetable.model.Professor;
import br.edu.ifma.csp.timetable.repository.ProfessorRepository;

public class ProfessorComposer extends Composer<Professor> {
	
	private ProfessorRepository repository;

	private static final long serialVersionUID = -2980484980640308048L;

	@Init
	public void init() {
		
//		entidade = new Professor();
		
		try {
			repository = InitialContext.doLookup("java:module/ProfessorDao");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void save() {
		
		Clients.showNotification(entidade.getNome(), "warning", getBinder().getView(), "middle_center", 2000);
		//repository.save(type);
		//super.save();
	}
}
