package teste;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.tools.ArrayUtils;

import br.edu.ifma.csp.timetable.dao.DetalheDisciplinaDao;
import br.edu.ifma.csp.timetable.dao.DisciplinaDao;
import br.edu.ifma.csp.timetable.dao.HorarioDao;
import br.edu.ifma.csp.timetable.dao.LocalDao;
import br.edu.ifma.csp.timetable.dao.ProfessorDao;
import br.edu.ifma.csp.timetable.handler.constraints.HorarioConsecutivo;
import br.edu.ifma.csp.timetable.handler.constraints.HorarioIndisponivel;
import br.edu.ifma.csp.timetable.handler.constraints.HorarioIndisponivelProfessor;
import br.edu.ifma.csp.timetable.handler.constraints.HorarioLimiteToSemestre;
import br.edu.ifma.csp.timetable.handler.constraints.HorarioOrdenado;
import br.edu.ifma.csp.timetable.handler.constraints.HorarioUnicoToLocal;
import br.edu.ifma.csp.timetable.handler.constraints.HorarioUnicoToProfessor;
import br.edu.ifma.csp.timetable.handler.constraints.HorarioUnicoToSemestre;
import br.edu.ifma.csp.timetable.handler.constraints.OfertaMinimaConsecutiva;
import br.edu.ifma.csp.timetable.handler.constraints.OfertaUnicaToDisciplina;
import br.edu.ifma.csp.timetable.handler.constraints.SalaEspecialToDisciplina;
import br.edu.ifma.csp.timetable.model.DetalheDisciplina;
import br.edu.ifma.csp.timetable.model.DetalheTimetable;
import br.edu.ifma.csp.timetable.model.Disciplina;
import br.edu.ifma.csp.timetable.model.Entidade;
import br.edu.ifma.csp.timetable.model.Horario;
import br.edu.ifma.csp.timetable.model.Local;
import br.edu.ifma.csp.timetable.model.MatrizCurricular;
import br.edu.ifma.csp.timetable.model.Periodo;
import br.edu.ifma.csp.timetable.model.Professor;
import br.edu.ifma.csp.timetable.model.Timetable;
import br.edu.ifma.csp.timetable.model.choco.Timeslot;
import br.edu.ifma.csp.timetable.repository.DetalhesDisciplina;
import br.edu.ifma.csp.timetable.repository.Disciplinas;
import br.edu.ifma.csp.timetable.repository.Horarios;
import br.edu.ifma.csp.timetable.repository.Locais;
import br.edu.ifma.csp.timetable.repository.Professores;
import br.edu.ifma.csp.timetable.util.Lookup;

/**
 * Modelo de Timetable utilizado na resolução do Problema de Alocação de Horários do IFMA. <br>
 * O modelo a seguir é uma abstração baseada na classe {@code org.chocosolver.samples.AbstractProblem}. <br>
 * <b>Framework: </b> Choco Solver (v. 4.0.0) <br>
 * <b>Flow (CSP): </b> <br>
 * 	<ol>
 * 		<li>Definir modelo</li>
 * 		<li>Construir modelo: </li>
 * 		<ol>
 * 			<li>Definir variáveis </li>
 * 			<li>Definir domínios </li>
 * 			<li>Definir restrições </li>
 * 		</ol>
 * 		<li>Definir estratégia de busca </li>
 * 		<li>Executar busca</li>
 * 		<li>Recuperar resultado(s)</li>
 *  </ol>  
 *  
 * @author inalberth
 * @since 2016
 */
public class Teste {
	
	private Disciplinas disciplinas;
	private Professores professores;
	private Horarios horarios;
	private Locais locais;
	private DetalhesDisciplina detalhesDisciplina;
	
	IntVar [][] varHorariosPeriodo;
	
	List<Professor> colProfessores;
	List<Disciplina> colDisciplinas;
	List<Horario> colHorarios;
	List<DetalheDisciplina> colDetalhes;
	List<Local> colLocais;
	
	List<Timeslot> timeslots;
	List<Timeslot> timeslotsDisciplinasHorarioUnico;
	
	List<IntVar> totalPenalizacoes;
	
	private MatrizCurricular matrizCurricular;
	
	private Timetable timetable;
	
	int [] disciplinasId;
	int [] professoresId;
	int [] horariosId;
	int [] locaisId;
	int [] aulas;
	
	private int numeroPeriodos;
	
	int [][] periodos;
	
	private Model model;
	private Solver solver;
	
	private final void init() {
		
		disciplinas = Lookup.dao(DisciplinaDao.class);
		professores = Lookup.dao(ProfessorDao.class);
		horarios = Lookup.dao(HorarioDao.class);
		locais = Lookup.dao(LocalDao.class);
		detalhesDisciplina = Lookup.dao(DetalheDisciplinaDao.class);
		
		colProfessores = professores.all();
		colDisciplinas = allObrigatoriasAtePeriodo(getMatrizCurricular(), getNumeroPeriodos());
		colDetalhes = detalhesDisciplina.allByMatrizCurricular(getMatrizCurricular());
		colHorarios = horarios.all();
		colLocais = locais.all();
		
		for (DetalheTimetable detalheTimetable : timetable.getDetalhes()) {
			
			if (detalheTimetable.isTipoCriterioEletiva() && detalheTimetable.getPeriodo().getCodigo() <= getNumeroPeriodos()) {
				
				DetalheDisciplina detalheEletiva = 
				detalhesDisciplina.byMatrizCurricularDisciplina(timetable.getMatrizCurricular(), detalheTimetable.getDisciplina());
				
				if (detalheEletiva != null) {
					
					detalheEletiva.getPeriodo().setCodigo(detalheTimetable.getPeriodo().getCodigo());
					adicionarEletiva(colDetalhes, detalheTimetable.getPeriodo(), detalheEletiva);
				}
			}
		}
		
		aulas = extrairCreditos(colDetalhes);
		disciplinasId = extrairIds(colDisciplinas);
		professoresId  = extrairIds(colProfessores);
		horariosId = extrairIds(colHorarios);
		locaisId = extrairIds(colLocais);
	}

	
	private final void createSolver() {
		
		model = new Model("Timetable");
		
		solver = model.getSolver();
	}
	
	private final void buildModel() {
		
		init();
		
		timeslots = new ArrayList<Timeslot>();
		
		totalPenalizacoes = new ArrayList<IntVar>();
		
		periodos = new int[numeroPeriodos][];
		
		varHorariosPeriodo = new IntVar[periodos.length][];
		
		timeslotsDisciplinasHorarioUnico = new ArrayList<Timeslot>();
		
		for (int i = 0; i < numeroPeriodos; i++) {
			
			Periodo periodo = getMatrizCurricular().getPeriodos().get(i);
			
			List<DetalheDisciplina> detalhes = allByPeriodo(colDetalhes, periodo);
			
			periodos[i] = new int[detalhes.size()];
			
			for (int j = 0; j < detalhes.size(); j++) {
				periodos[i][j] = detalhes.get(j).getDisciplina().getId();
			}
		}
		
		for (int i = 0; i < disciplinasId.length; i++) {
			
			IntVar disciplina = model.intVar("D[" + i + "]", disciplinasId[i]);
			Disciplina d = disciplinas.byId(disciplinasId[i]);
			
			IntVar professor = model.intVar("P[" + i + "]", extrairIds(professores.allByPreferenciaDisciplina(d)));
			
			Timeslot timeslot = new Timeslot();
			timeslot.addProfessor(professor);
			timeslot.addDisciplina(disciplina);
			
			for (int j = 0; j < aulas[i]; j++) {
				
				IntVar horario = model.intVar(disciplina.getName() + "_" + "H" + (j+1), 0, horariosId.length - 1);
				timeslot.addHorario(horario);
				
				IntVar local = model.intVar(disciplina.getName() + "_" + "L" + (j+1), 0, locaisId.length - 1);
				timeslot.addLocal(local);
			}
			
			timeslots.add(timeslot);
		}
		
		OfertaUnicaToDisciplina.postConstraint(model, timetable, timeslots, timeslotsDisciplinasHorarioUnico);
		
		HorarioUnicoToProfessor.postConstraint(model, timeslots, professoresId);
		
		HorarioUnicoToLocal.postConstraint(model, timeslots);
		
		HorarioUnicoToSemestre.postConstraint(model, varHorariosPeriodo, timeslots, periodos);
		
		HorarioOrdenado.postConstraint(model, timeslots);
		
		OfertaMinimaConsecutiva.postConstraint(model, timeslots, timeslotsDisciplinasHorarioUnico, timetable, aulas);
		
		HorarioConsecutivo.postConstraint(model, timeslots);
		
		HorarioLimiteToSemestre.postConstraint(model, timeslots, timetable, colHorarios, periodos);
		
		SalaEspecialToDisciplina.postConstraint(model, timeslots, colDetalhes, timetable);
		
		if (timetable.isHorariosIndisponiveisPermitidos()) {
			HorarioIndisponivelProfessor.postConstraint(model, timeslots, timetable, colHorarios, horariosId, totalPenalizacoes);
			
			IntVar penalty = model.intVar("penalty", 0, 100, false);
			model.sum(totalPenalizacoes.toArray(new IntVar[totalPenalizacoes.size()]), "<", penalty).post();
			model.setObjective(Model.MINIMIZE, penalty);
			
		} else {
			HorarioIndisponivel.postConstraint(model, timeslots, timetable, colHorarios, horariosId);
		}
	}

	
	private final void configureSearch() {
		
		solver.makeCompleteStrategy(true);
		solver.setSearch(Search.domOverWDegSearch(ArrayUtils.flatten(varHorariosPeriodo)), Search.lastConflict(Search.intVarSearch(ArrayUtils.flatten(varHorariosPeriodo))));
	}
	
	
	public void execute() {
		
		createSolver();
		buildModel();
		configureSearch();
		//solve();
	}

	
	public final void solve() {
		
		solver.showStatistics();
		
		if (solver.solve()) {
			prettyOut();
		}
	}
	
	public Disciplina getDisciplina(int disciplina) {
		
		for (Disciplina d : colDisciplinas) {
			
			if (d.getId() == disciplina)
				return d;
		}
		
		return null;
	}
	
	public Professor getProfessor(int professor) {
		
		for (Professor p : colProfessores) {
			
			if (p.getId() == professor)
				return p;
		}
		
		return null;
	}
	
	public Local getLocal(int local) {
		
		int index = locaisId[local];
		
		for (Local l : colLocais) {
			
			if (l.getId() == index)
				return l;
		}
		
		return null;
	}
	
	public Horario getHorario(int horario) {
		
		int index = horariosId[horario];
		
		for (Horario h : colHorarios) {
			
			if (h.getId() == index)
				return h;
		}
		
		return null;
	}
	
	private void adicionarEletiva(List<DetalheDisciplina> detalhes, Periodo periodo, DetalheDisciplina detalhe) {
		
		int cont = -1;
		
		for (int i = 0; i < detalhes.size(); i++) {
			
			cont += 1;
			
			if (detalhes.get(i).getPeriodo() != null && detalhes.get(i).getPeriodo().getCodigo() == periodo.getCodigo()) {
				
				if ((cont + 1) >= detalhes.size()) {
					detalhes.add(i+1, detalhe);
					colDisciplinas.add(i+1, detalhe.getDisciplina());
					break;
				}
				
				if (detalhes.get(i+1) != null) {
					
					if (detalhes.get(i+1).getPeriodo().getCodigo() != periodo.getCodigo()) {
						detalhes.add(i+1, detalhe);
						colDisciplinas.add(i+1, detalhe.getDisciplina());
						break;
					}
				}
			}
		}
	}
	
	public void prettyOut() {
		
		printOut();
		
		/*for (Timeslot timeslot : timeslots) {
			
			System.out.print(timeslot.getDisciplina().getName() + " = " + getDisciplina(timeslot.getDisciplina().getValue()) + ", ");
			System.out.println(timeslot.getProfessor().getName() + " = " + getProfessor(timeslot.getProfessor().getValue()));
			
			for (int i = 0; i < timeslot.getLocais().size(); i++) {
				
				IntVar local = timeslot.getLocais().get(i);
				IntVar horario = timeslot.getHorarios().get(i);
				
				System.out.println(local.getName() + " = " + getLocal(local.getValue()) + ", " + horario.getName() + " = " + getHorario(horario.getValue()));
			}
		}*/
	}
	
	private List<DetalheDisciplina> allByPeriodo(List<DetalheDisciplina> detalhes, Periodo periodo) { 
		return detalhes.stream().filter(detalhe -> detalhe.getPeriodo().getCodigo() == periodo.getCodigo()).collect(Collectors.toList());
	}
	
	private List<Disciplina> allObrigatoriasAtePeriodo(MatrizCurricular matriz, int periodo) {
		
		List<Disciplina> listDisciplinas = new ArrayList<Disciplina>();
		
		for (int i = 0; i < periodo; i++) {
			
			for (Periodo p : matriz.getPeriodos()) {
				
				if (p.getCodigo() == (i+1)) {
					
					for (DetalheDisciplina detalheDisciplina : p.getDetalhes()) {
						
						if (detalheDisciplina.isObrigatoria() && professores.allByPreferenciaDisciplina(detalheDisciplina.getDisciplina()).size() > 0) {
							
							listDisciplinas.add(detalheDisciplina.getDisciplina());
						}
					}
				}
			}
		}
		
		return listDisciplinas;
	}
	
	private int [] getDisciplinasPorPeriodo(int periodo) {
		return periodos[periodo-1];
	}
	
	public List<Timeslot> getTimeslotsPeriodo(int periodo) {
		
		int disciplinasPeriodo[] = getDisciplinasPorPeriodo(periodo);
		
		List<Timeslot> list = new ArrayList<Timeslot>(disciplinasPeriodo.length);
		
		for (int i = 0; i < disciplinasPeriodo.length; i++) {
			
			Timeslot timeslot = getTimeslotDisciplina(disciplinasPeriodo[i]);
			
			list.add(timeslot);
		}
		
		return list;
	}
	
	private Timeslot getTimeslotDisciplina(int disciplina) {
		
		for (Timeslot timeslot : timeslots) {
			
			if (timeslot.getDisciplina().getValue() == disciplina)
				return timeslot;
		}
		
		return null;
	}
	
	public int getPeriodoDisciplina(int disciplina) {
		
		for (int i = 0; i < periodos.length; i++) {
			
			for (int j = 0; j < periodos[i].length; j++) {
				
				if (periodos[i][j] == disciplina)
					return (i+1);
			}
		}
		
		return 0;
	}
	
	private int [] extrairCreditos(List<DetalheDisciplina> detalhes) {
		
		List<Integer> lista = detalhes.stream().map(entidade -> entidade.getCreditos()).collect(Collectors.toList());
		
		return Arrays.stream(lista.toArray(new Integer[lista.size()])).mapToInt(Integer::intValue).toArray();
	}
	
	private int [] extrairIds(List<? extends Entidade> list) {
		
		List<Integer> listaIds = list.stream().map(entidade -> entidade.getId()).collect(Collectors.toList());
		
		return Arrays.stream(listaIds.toArray(new Integer[listaIds.size()])).mapToInt(Integer::intValue).toArray();
	}
	
	public Model getModel() {
		return model;
	}
	
	public Solver getSolver() {
		return solver;
	}


	public MatrizCurricular getMatrizCurricular() {
		return matrizCurricular;
	}


	public void setMatrizCurricular(MatrizCurricular matrizCurricular) {
		this.matrizCurricular = matrizCurricular;
	}
	
	private void printOut() {
		
		String [] dias = {"Segunda-Feira", "Terça-Feira", "Quarta-Feira", "Quinta-Feira", "Sexta-Feira"};
		String [] horas = {"15:10", "16:00", "16:50", "17:40", "18:30", "19:20", "20:10", "21:00", "21:50"};
			 
		Grade [] grades = new Grade[periodos.length];
		
		int count = 0;
		
		int indexDisciplina = 0;
		 
		for (int k = 0; k < periodos.length; k++) { 
			grades[k] = new Grade(horas.length, dias.length);
		 }
			 
		for (int i = 0; i < timeslots.size(); i++) {
				
			int k = getPeriodoDisciplina(timeslots.get(i).getDisciplina().getValue()) - 1;
				
			for (int j = 0; j < aulas[i]; j++) {
				
				int [] tokens = getTokens(timeslots.get(i).getHorarios().get(j));	
				grades[k].getGrade()[tokens[1]][tokens[0]] = getDisciplina(timeslots.get(i).getDisciplina().getValue()).getSigla() + " " + getLocal(timeslots.get(i).getLocais().get(j).getValue());
			}
		}
		
		for (Grade grade : grades) {
			
			System.out.println("Período: SI." + (count + 1));
			
			System.out.print("\n+-----+-------------------------+-------------------------+-------------------------+");
			System.out.println("-------------------------+-------------------------+");
			System.out.format("%2s", "|HORAS|");
			
			for (int i = 0; i < dias.length; i++) {
				
				System.out.format("%-25s", dias[i]);
				
				if ((i+1) < dias.length) {
					System.out.print("|");
				}
			}
			
			System.out.print("|");
			System.out.print("\n+-----+-------------------------+-------------------------+-------------------------+");
			System.out.println("-------------------------+-------------------------+");
			
			for (int i = 0; i < grade.getGrade().length; i++) {
				
				for (int j = 0; j < grade.getGrade()[i].length; j++) {
					
					if (j == 0) {
						System.out.format("%2s" , "|" + horas[i] + "|");
					}
					
					System.out.format("%-25s", grade.getGrade()[i][j] != null ? grade.getGrade()[i][j] : "");
					
					if ((j+1) < grade.getGrade()[i].length) {
						System.out.print("|");
					}
				}
				
				System.out.print("|");
				System.out.print("\n+-----+-------------------------+-------------------------+-------------------------+");
				System.out.println("-------------------------+-------------------------+");
			}
			
			System.out.println();
			
			for (int i = 0; i < periodos[count].length; i++) {
				
				System.out.print(getDisciplina(periodos[count][i]).getSigla() + " = " + getProfessor(timeslots.get(indexDisciplina).getProfessor().getValue()));
				
				if ((i+1) < periodos[count].length) {
					System.out.print(", ");
				}
				
				if (i == 4)
					System.out.println();
				
				indexDisciplina += 1;
			}
			
			System.out.println("\n");
			
			count += 1;
			
		}
		
		System.out.println();
	}

	private int [] getTokens(IntVar horario) {
	
		int [] tokens = new int[2];
		
		String [] dias = {"Segunda-Feira", "Terça-Feira", "Quarta-Feira", "Quinta-Feira", "Sexta-Feira"};
		String [] horas = {"15:10", "16:00", "16:50", "17:40", "18:30", "19:20", "20:10", "21:00", "21:50"};
		
		String str = getHorario(horario.getValue()).toString();
		
		for (int i = 0; i < dias.length; i++) {
			
			if (str.split(" ")[0].equals(dias[i])) {
				tokens[0] = i;
			}
		}
		
		for (int i = 0; i < horas.length; i++) {
			if (str.split(" ")[1].contains(horas[i])) {
				tokens[1] = i;
			}
		}
		
		return tokens;
	}


	public int getNumeroPeriodos() {
		return numeroPeriodos;
	}


	public void setNumeroPeriodos(int numeroPeriodos) {
		this.numeroPeriodos = numeroPeriodos;
	}


	public Timetable getTimetable() {
		return timetable;
	}


	public void setTimetable(Timetable timetable) {
		this.timetable = timetable;
	}
}