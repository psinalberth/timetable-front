package br.edu.ifma.csp.timetable.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.constraints.nary.alldifferent.AllDifferent;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.variables.IntVar;

import br.edu.ifma.csp.timetable.dao.DisciplinaDao;
import br.edu.ifma.csp.timetable.dao.HorarioDao;
import br.edu.ifma.csp.timetable.dao.LocalDao;
import br.edu.ifma.csp.timetable.dao.ProfessorDao;
import br.edu.ifma.csp.timetable.model.DetalheDisciplina;
import br.edu.ifma.csp.timetable.model.Disciplina;
import br.edu.ifma.csp.timetable.model.Entidade;
import br.edu.ifma.csp.timetable.model.Horario;
import br.edu.ifma.csp.timetable.model.Local;
import br.edu.ifma.csp.timetable.model.MatrizCurricular;
import br.edu.ifma.csp.timetable.model.Periodo;
import br.edu.ifma.csp.timetable.model.Professor;
import br.edu.ifma.csp.timetable.model.Timeslot;
import br.edu.ifma.csp.timetable.repository.Disciplinas;
import br.edu.ifma.csp.timetable.repository.Horarios;
import br.edu.ifma.csp.timetable.repository.Locais;
import br.edu.ifma.csp.timetable.repository.Professores;
import br.edu.ifma.csp.timetable.util.Lookup;

public class TimetableHandler {
	
	private MatrizCurricular matrizCurricular;
	
	private Disciplinas disciplinas2;
	private Professores professores2;
	private Horarios horarios;
	private Locais locais;
	
	IntVar [] disciplinas;
	IntVar [] professores;
	IntVar [][] horariosPeriodo;
	IntVar [][] horariosProfessor;
	IntVar [][] horariosDisciplina;
	IntVar [][] horariosLocal;
	
	List<Professor> listProfessores;
	List<Disciplina> listDisciplina;
	List<Horario> listHorarios;
	List<DetalheDisciplina> listDetalhes;
	List<Local> listLocais;
	
	List<Timeslot> timeslots;
	
	int [] disciplinasId;
	int [] professoresId;
	int [] horariosId;
	int [] locaisId;
	int [] aulas;
	
	int [][] periodos;
	
	Model model;
	
	private void init() {
		
		disciplinas2 = Lookup.dao(DisciplinaDao.class);
		professores2 = Lookup.dao(ProfessorDao.class);
		horarios = Lookup.dao(HorarioDao.class);
		locais = Lookup.dao(LocalDao.class);
		
		listProfessores = professores2.allByMatrizCurricular(getMatrizCurricular());
		listDisciplina = disciplinas2.allByMatrizCurricular(getMatrizCurricular());
		listDetalhes = allByMatrizCurricular(getMatrizCurricular());
		listHorarios = horarios.all();
		listLocais = locais.all();
		
		disciplinasId = extrairIds(listDisciplina);
		professoresId  = extrairIds(listProfessores);
		horariosId = extrairIds(listHorarios);
		locaisId = extrairIds(listLocais);
		aulas = extrairCreditos(listDetalhes);
	}

	
	public void createSolver() {
		
	}

	
	public void buildModel() {
		
		init();
		
		model = new Model("Timetable");
		
		timeslots = new ArrayList<Timeslot>();
		
		professores = model.intVarArray("P", disciplinasId.length, professoresId);
		disciplinas = new IntVar[disciplinasId.length];
		
		horariosDisciplina = new IntVar[disciplinas.length][];
		horariosLocal = new IntVar[disciplinas.length][];
		
		periodos = new int[getMatrizCurricular().getPeriodos().size()][];
		
		for (int i = 0; i < getMatrizCurricular().getPeriodos().size(); i++) {
			
			Periodo periodo = getMatrizCurricular().getPeriodos().get(i);
			
			List<DetalheDisciplina> detalhes = allObrigatoriasByPeriodo(periodo);
			
			periodos[i] = new int[detalhes.size()];
			
			for (int j = 0; j < detalhes.size(); j++) {
				periodos[i][j] = detalhes.get(j).getDisciplina().getId();
			}
		}
		
		for (int i = 0; i < professores.length; i++) {
			
			disciplinas[i] = model.intVar("D[" + i + "]", disciplinasId[i]);
			
			Timeslot timeslot = new Timeslot();
			timeslot.addProfessor(professores[i]);
			timeslot.addDisciplina(disciplinas[i]);
			
			horariosDisciplina[i] = new IntVar[aulas[i]];
			horariosLocal[i] = new IntVar[aulas[i]];
			
			for (int j = 0; j < aulas[i]; j++) {
				
				IntVar horario = model.intVar(disciplinas[i].getName() + "_" + "H" + (j+1), horariosId);
				timeslot.addHorario(horario);
				
				IntVar local = model.intVar(disciplinas[i].getName() + "_" + "L" + (j+1), locaisId);
				timeslot.addLocal(local);
				
				horariosDisciplina[i][j] = horario;
				horariosLocal[i][j] = local;
			}
			
			timeslots.add(timeslot);
		}
		
		manterProfessorQuePossuiPreferenciaConstraint();
		
		manterProfessoresComHorarioUnicoConstraint();
		
		//manterLocaisComHorarioUnicoConstraint();
		
		manterDisciplinasComHorarioUnicoConstraint();
	}

	
	public void configureSearch() {
		
	}

	
	public void solve() {
		
		Solver solver = model.getSolver();
		solver.makeCompleteStrategy(true);
		solver.setSearch(Search.minDomLBSearch(disciplinas));
		solver.showStatistics();
		
		if (solver.solve()) {
			
			for (Timeslot timeslot : timeslots) {
				
				System.out.print(timeslot.getDisciplina().getName() + " = " + getDisciplina(timeslot.getDisciplina().getValue()) + ", ");
				System.out.println(timeslot.getProfessor().getName() + " = " + getProfessor(timeslot.getProfessor().getValue()));
				
				for (int i = 0; i < timeslot.getLocais().size(); i++) {
					
					IntVar local = timeslot.getLocais().get(i);
					IntVar horario = timeslot.getHorarios().get(i);
					
					System.out.println(local.getName() + " = " + getLocal(local.getValue()) + ", " + horario.getName() + " = " + getHorario(horario.getValue()));
				}
			}
		}
	}
	
	public Disciplina getDisciplina(int disciplina) {
		
		for (Disciplina d : listDisciplina) {
			
			if (d.getId() == disciplina)
				return d;
		}
		
		return null;
	}
	
	public Professor getProfessor(int professor) {
		
		for (Professor p : listProfessores) {
			
			if (p.getId() == professor)
				return p;
		}
		
		return null;
	}
	
	public Local getLocal(int local) {
		
		for (Local l : listLocais) {
			
			if (l.getId() == local)
				return l;
		}
		
		return null;
	}
	
	public Horario getHorario(int horario) {
		
		for (Horario h : listHorarios) {
			
			if (h.getId() == horario)
				return h;
		}
		
		return null;
	}
	
	public void prettyOut() {

	}
	
	/**
	 * <b>Restrição Forte:</b> <br>
	 *  
	 * Um professor só poderá ser selecionado para ministrar apenas as disciplinas para as quais tenha preferência. 
	 * Dado um um professor <i>P<sub>i<sub></i> e um conjunto <i>D<sub>j<sub></i> disciplinas disponíveis, 
	 * é utilizada a restrição {@link IntConstraintFactory #member(IntVar, int[])}, a qual é responsável por selecionar
	 * para uma variável um valor dentre os disponíveis na coleção.
	 */
	
	private void manterProfessorQuePossuiPreferenciaConstraint() {
		
		Tuples tuples = new Tuples(true);
		
		for (int i = 0; i < professores.length; i++) {
			
			int [] professoresId = extrairIds(professores2.allByPreferenciaDisciplina(disciplinas2.byId(disciplinasId[i])));
			
			for (int j = 0; j < professoresId.length; j++) {
				tuples.add(disciplinasId[i], professoresId[j]);
			}
		}
		
		for (int i = 0; i < professores.length; i++) {			
			model.table(disciplinas[i], professores[i], tuples).post();
		}
	}
	
	/**
	 * <b>Restrição Forte:</b> <br>
	 *  
	 * Um professor não pode ser selecionado para ministrar duas aulas de disciplinas diferentes
	 * no mesmo horário. Dado um conjunto de <b>{@code n}</b> horários de um professor, é aplicada a restrição 
	 * {@link AllDifferent}, a qual mantém um professor com apenas uma ocorrência de horário.
	 */

	private void manterProfessoresComHorarioUnicoConstraint() {
		
		horariosProfessor = new IntVar[professoresId.length][];
		
		for (int i = 0; i < professoresId.length; i++) {
			
			List<IntVar> horarios = new ArrayList<IntVar>();
			
			int [] disciplinasProfessorId = extrairIds(disciplinas2.allByPreferenciaProfessor(professores2.byId(professoresId[i])));
			
			for (int j = 0; j < disciplinasProfessorId.length; j++) {
				
				if (getTimeslotDisciplina(disciplinasProfessorId[j]) != null) {
				
					horarios.addAll(getTimeslotDisciplina(disciplinasProfessorId[j]).getHorarios());
				}
			}
			
			horariosProfessor[i] = horarios.toArray(new IntVar[horarios.size()]);
			model.allDifferent(horariosProfessor[i], "NEQS").post();
		}
	}
	
	/**
	 * <b>Restrição Forte:</b> <br>
	 *  
	 * As ofertas de aula para disciplinas de mesmo período (turma) não devem estar sobrepostas.
	 * A partir do conjunto <i>D</i> de <b>{@code m}</b> de disciplinas selecionadas e outro conjunto <i>H<sub>D</sub></i> de 
	 * horários correspondentes, é realizada a associação e agrupamento de todos os horários por turma e, em seguida, aplica-se a 
	 * restrição {@link AllDifferent}, a qual mantém apenas uma oferta de aula por horário.
	 */

	private void manterDisciplinasComHorarioUnicoConstraint() {
		
		horariosPeriodo = new IntVar[periodos.length][];
		
		for (int i = 0, index = 0; i < periodos.length; i++) {
			
			List<IntVar> horarios = new ArrayList<IntVar>();
			
			for (int j = index; j < index + periodos[i].length; j++) {
				horarios.addAll(timeslots.get(j).getHorarios());
			}
			
			horariosPeriodo[i] = horarios.toArray(new IntVar[horarios.size()]);
			model.allDifferent(horariosPeriodo[i], "NEQS").post();
			index += periodos[i].length;
		}		
	}
	
	
	private void manterLocaisComHorarioUnicoConstraint() {
		
		List<IntVar> list = new ArrayList<IntVar>();
		
		for (int i = 0; i < timeslots.size(); i++) {
			
			for (int j = 0; j < timeslots.get(i).getHorarios().size(); j++) {
				
				IntVar horario = timeslots.get(i).getHorarios().get(j);
				IntVar local = timeslots.get(i).getLocais().get(j);
				
				IntVar z = model.intVar("z", 310000, 540000);
				
				model.sum(new IntVar[]{model.intScaleView(horario, 1000), local}, "=", z).post();
				
				list.add(z);
			}
		}
		
		model.allDifferent(list.toArray(new IntVar[list.size()]), "NEQS").post();
	}
	
	private List<DetalheDisciplina> allObrigatoriasByPeriodo(Periodo periodo) {
		
		List<DetalheDisciplina> list = new ArrayList<DetalheDisciplina>();
		
		for (DetalheDisciplina detalhe : periodo.getDetalhes()) {
			
			if (detalhe.isObrigatoria()) {
				list.add(detalhe);
			}
		}
		
		return list;
	}
	
	private List<DetalheDisciplina> allByMatrizCurricular(MatrizCurricular matriz) {
		
		List<DetalheDisciplina> listaDetalhes = new ArrayList<DetalheDisciplina>();
		
		for (Disciplina disciplina : listDisciplina) {
			
			for (DetalheDisciplina detalhe : disciplina.getDetalhes()) {
				
				if (detalhe.getPeriodo().getMatrizCurricular().getId() == matriz.getId()) {
					listaDetalhes.add(detalhe);
				}
			}
		}
		
		return listaDetalhes;
	}
	
	private Timeslot getTimeslotDisciplina(int disciplina) {
		
		for (Timeslot timeslot : timeslots) {
			
			if (timeslot.getDisciplina().getValue() == disciplina)
				return timeslot;
		}
		
		return null;
	}
	
	private int [] extrairCreditos(List<DetalheDisciplina> detalhes) {
		
		List<Integer> lista = new ArrayList<Integer>();
		
		for (DetalheDisciplina detalhe : detalhes) {
			lista.add(detalhe.getCreditos());
		}
		
		return Arrays.stream(lista.toArray(new Integer[lista.size()])).mapToInt(Integer::intValue).toArray();
	}
	
	private int [] extrairIds(List<? extends Entidade> list) {
		
		List<Integer> listaIds = new ArrayList<Integer>();
		
		for (Entidade obj : list) {
			listaIds.add(obj.getId());
		}
		
		return Arrays.stream(listaIds.toArray(new Integer[listaIds.size()])).mapToInt(Integer::intValue).toArray();
	}
	
	public MatrizCurricular getMatrizCurricular() {
		return matrizCurricular;
	}
	
	public void setMatrizCurricular(MatrizCurricular matrizCurricular) {
		this.matrizCurricular = matrizCurricular;
	}
}