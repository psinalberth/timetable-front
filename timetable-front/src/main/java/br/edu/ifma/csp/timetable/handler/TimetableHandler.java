package br.edu.ifma.csp.timetable.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.search.loop.monitors.IMonitorContradiction;
import org.chocosolver.solver.search.loop.monitors.IMonitorSolution;
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
import br.edu.ifma.csp.timetable.handler.constraints.HorarioLimiteToSemestre;
import br.edu.ifma.csp.timetable.handler.constraints.HorarioOrdenado;
import br.edu.ifma.csp.timetable.handler.constraints.HorarioUnicoToLocal;
import br.edu.ifma.csp.timetable.handler.constraints.HorarioUnicoToProfessor;
import br.edu.ifma.csp.timetable.handler.constraints.HorarioUnicoToSemestre;
import br.edu.ifma.csp.timetable.handler.constraints.OfertaMinimaConsecutiva;
import br.edu.ifma.csp.timetable.handler.constraints.OfertaUnicaToDisciplina;
import br.edu.ifma.csp.timetable.handler.constraints.SalaEspecialToDisciplina;
import br.edu.ifma.csp.timetable.model.Aula;
import br.edu.ifma.csp.timetable.model.DetalheDisciplina;
import br.edu.ifma.csp.timetable.model.DetalheTimetable;
import br.edu.ifma.csp.timetable.model.Disciplina;
import br.edu.ifma.csp.timetable.model.Entidade;
import br.edu.ifma.csp.timetable.model.Horario;
import br.edu.ifma.csp.timetable.model.Local;
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
public class TimetableHandler {

	private Timetable timetable;
	
	private Disciplinas disciplinas = Lookup.dao(DisciplinaDao.class);
	private Professores professores = Lookup.dao(ProfessorDao.class);
	private Horarios horarios = Lookup.dao(HorarioDao.class);
	private Locais locais = Lookup.dao(LocalDao.class);
	private DetalhesDisciplina detalhesDisciplina = Lookup.dao(DetalheDisciplinaDao.class);
	
	IntVar [][] varHorariosPeriodo;
	
	List<Professor> colProfessores;
	List<Disciplina> colDisciplinas;
	List<Horario> colHorarios;
	List<DetalheDisciplina> colDetalhes;
	List<Local> colLocais;
	
	List<Timeslot> timeslots;
	
	List<Timeslot> timeslotsDisciplinasHorarioUnico;
	
	int [] disciplinasId;
	int [] professoresId;
	int [] horariosId;
	int [] locaisId;
	int [] aulas;
	
	int [][] periodos;
	
	private Model model;
	private Solver solver;
	
	private List<List<Aula>> colAulas = new ArrayList<List<Aula>>();
	
	private final void init() {
		
		colProfessores = professores.all();
		colDisciplinas = disciplinas.allObrigatoriasByMatrizCurricular(timetable.getMatrizCurricular());
		colDetalhes = detalhesDisciplina.allByMatrizCurricular(timetable.getMatrizCurricular());
		colHorarios = horarios.all();
		colLocais = locais.all();
		
		for (DetalheTimetable detalheTimetable : timetable.getDetalhes()) {
			
			if (detalheTimetable.isTipoCriterioEletiva()) {
				
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
		
		periodos = new int[timetable.getMatrizCurricular().getPeriodos().size()][];
		
		varHorariosPeriodo = new IntVar[periodos.length][];
		
		timeslotsDisciplinasHorarioUnico = new ArrayList<Timeslot>();
		
		for (int i = 0; i < timetable.getMatrizCurricular().getPeriodos().size(); i++) {
			
			Periodo periodo = timetable.getMatrizCurricular().getPeriodos().get(i);
			
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
		
		HorarioIndisponivel.postConstraint(model, timeslots, timetable, colHorarios, horariosId);
	}
	
	private final void configureSearch() {
		
		solver.makeCompleteStrategy(true);
		solver.setSearch(Search.domOverWDegSearch(ArrayUtils.flatten(varHorariosPeriodo)), Search.lastConflict(Search.intVarSearch(ArrayUtils.flatten(varHorariosPeriodo))));
	}
	
	public void execute() {
		
		createSolver();
		buildModel();
		configureSearch();
		setTimeout();
		solve();
	}
	
	private void setTimeout() {
		solver.limitTime(180000);
	}

	private final void solve() {
		
		solver.showStatistics();
		
		int count = 0;
		
		Solution solution = new Solution(getModel());
		
		solver.plugMonitor(new IMonitorContradiction() {
			
			@Override
			public void onContradiction(ContradictionException cex) {
				cex.printStackTrace();
				System.out.println(cex.c);
				
			}
		});
		
		solver.plugMonitor(new IMonitorSolution() {
			
			@Override
			public void onSolution() {
				solution.record();
				prettyOut();
			}
		});
		
		while (count < 3 && solver.solve()) {
			
			getColAulas().add(recuperaAulas());
			
			if (count == 0) {
				timetable.setAulas(getColAulas().get(count));
			}
			
			System.out.println("Solução " + (count + 1));
			
			count += 1;
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
	
	public void prettyOut() {
		
	}
	
	private List<DetalheDisciplina> allByPeriodo(List<DetalheDisciplina> detalhes, Periodo periodo) { 
		return detalhes.stream().filter(detalhe -> detalhe.getPeriodo().getCodigo() == periodo.getCodigo()).collect(Collectors.toList());
	}
	
	public List<Aula> recuperaAulas() {
		
		List<Aula> aulas = new ArrayList<Aula>();
		
		for (Timeslot timeslot : timeslots) {
			
			for (int i = 0; i < timeslot.getHorarios().size(); i++) {
				
				Aula aula = new Aula();
				aula.setDisciplina(getDisciplina(timeslot.getDisciplina().getValue()));
				aula.setProfessor(getProfessor(timeslot.getProfessor().getValue()));
				aula.setLocal(getLocal(timeslot.getLocais().get(i).getValue()));
				aula.setHorario(getHorario(timeslot.getHorarios().get(i).getValue()));
				aula.setTimetable(getTimetable());
				aula.setPeriodo(getPeriodoDisciplina(timeslot.getDisciplina().getValue()));
				
				aula.setUsuarioUltAlteracao(timetable.getUsuarioUltAlteracao());
				aula.setDataUltAlteracao(timetable.getDataUltAlteracao());
				
				aulas.add(aula);
			}
		}
		
		return aulas;
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
	
	private int getPeriodoDisciplina(int disciplina) {
		
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

	public void setTimetable(Timetable timetable) {	
		this.timetable = timetable;
	}
	
	public Timetable getTimetable() {
		return timetable;
	}

	public List<List<Aula>> getColAulas() {
		return colAulas;
	}

	public void setColAulas(List<List<Aula>> colAulas) {
		this.colAulas = colAulas;
	}
}