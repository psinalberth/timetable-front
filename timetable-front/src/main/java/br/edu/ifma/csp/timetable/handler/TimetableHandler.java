package br.edu.ifma.csp.timetable.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.extension.Tuples;
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
import br.edu.ifma.csp.timetable.dao.TipoLocalDao;
import br.edu.ifma.csp.timetable.model.Aula;
import br.edu.ifma.csp.timetable.model.Departamento;
import br.edu.ifma.csp.timetable.model.DetalheDisciplina;
import br.edu.ifma.csp.timetable.model.DetalheTimetable;
import br.edu.ifma.csp.timetable.model.Disciplina;
import br.edu.ifma.csp.timetable.model.Entidade;
import br.edu.ifma.csp.timetable.model.Horario;
import br.edu.ifma.csp.timetable.model.Local;
import br.edu.ifma.csp.timetable.model.Periodo;
import br.edu.ifma.csp.timetable.model.Professor;
import br.edu.ifma.csp.timetable.model.Timetable;
import br.edu.ifma.csp.timetable.model.TipoCriterioTimetable;
import br.edu.ifma.csp.timetable.model.TipoLocal;
import br.edu.ifma.csp.timetable.model.choco.Timeslot;
import br.edu.ifma.csp.timetable.repository.DetalhesDisciplina;
import br.edu.ifma.csp.timetable.repository.Disciplinas;
import br.edu.ifma.csp.timetable.repository.Horarios;
import br.edu.ifma.csp.timetable.repository.Locais;
import br.edu.ifma.csp.timetable.repository.Professores;
import br.edu.ifma.csp.timetable.repository.TiposLocal;
import br.edu.ifma.csp.timetable.util.Lookup;
import teste.Grade;

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
	private TiposLocal tiposLocal = Lookup.dao(TipoLocalDao.class);
	private DetalhesDisciplina detalhesDisciplina = Lookup.dao(DetalheDisciplinaDao.class);
	
	IntVar [][] varHorariosPeriodo;
	
	List<Professor> colProfessores;
	List<Disciplina> colDisciplinas;
	List<Horario> colHorarios;
	List<DetalheDisciplina> colDetalhes;
	List<Local> colLocais;
	
	List<Timeslot> timeslots;
	
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
		
		manterProfessoresComHorarioUnicoConstraint();
		
		manterLocaisComHorarioUnicoConstraint();
		
		manterDisciplinasComHorarioUnicoConstraint();
		
		manterHorariosOrdenadosConstraint();
		
		manterHorariosAlternadosConstraint();
		
		manterHorariosConsecutivosConstraint();
		
		manterHorariosPeriodoEntreConstraint();
		
		manterDisciplinasComAulaLaboratorioConstraint();
		
		manterHorariosIndisponiveisProfessor();
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
			
		//	prettyOut();
			
			count += 1;
		}
		
		/*for (int i = 0; i < 2; i++) {
			
			if (solver.solve()) {
				
				
				
				prettyOut();
				//timetable.setAulas(recuperaAulas());
			}
		}*/
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
		printOut();
	}
	
	private void manterHorariosIndisponiveisProfessor() {
		
		for (int i = 0; i < timeslots.size(); i ++) {
			
			Tuples tuples = new Tuples(true);
			
			Timeslot timeslot = timeslots.get(i);
			
			List<DetalheTimetable> detalhes = getDetalhesCriterioDisciplina(timeslot.getDisciplina().getValue());
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
					
					int [] horariosDisponiveis = getHorariosDisponiveisProfessor(professor);
						
					for (int j = 0; j < horariosDisponiveis.length; j++) {
						tuples.add(professor.getId(), horariosDisponiveis[j]);
					}
					
					if (getTimetable().isHorariosIndisponiveisPermitidos()) {
						
						int [] horariosIndisponiveis = getHorariosIndisponiveisProfessor(professor);
						
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
	
	private List<DetalheTimetable> getDetalhesCriterioDisciplina(int disciplinaId) {
		
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
	
	/**
	 * <b>Restrição Forte:</b> <br>
	 *  
	 * Um professor não pode ser selecionado para ministrar duas aulas de disciplinas diferentes
	 * no mesmo horário. Dado um conjunto de <b>{@code n}</b> horários de um professor, é aplicada a restrição 
	 * {@link Model #allDifferent(IntVar[], String)}, a qual mantém um professor com apenas uma ocorrência de horário.
	 */

	private void manterProfessoresComHorarioUnicoConstraint() {
		
		List<IntVar> list = new ArrayList<IntVar>();
		
		List<IntVar> l2 = new ArrayList<IntVar>();
		
		for (int i = 0; i < timeslots.size(); i++) {
			
			for (int j = 0; j < timeslots.get(i).getHorarios().size(); j++) {
				
				IntVar horario = timeslots.get(i).getHorarios().get(j);
				IntVar professor = timeslots.get(i).getProfessor();
				
				IntVar xy = model.intVar("xy", 0, 100000);
				
				l2.add(professor);
				
				model.sum(new IntVar[]{model.intScaleView(horario, 1000), professor}, "=", xy).post();
				
				list.add(xy);
			}
		}
		
		for (int i = 0; i < professoresId.length; i++) {
			model.count(professoresId[i], l2.toArray(new IntVar[l2.size()]), model.intVar(0, 12)).post();;
		}
		
		model.allDifferent(list.toArray(new IntVar[list.size()]), "NEQS").post();
	}
	
	/**
	 * <b>Restrição Forte:</b> <br>
	 *  
	 * As ofertas de aula para disciplinas de mesmo período (turma) não devem estar sobrepostas.
	 * A partir do conjunto <i>D</i> de <b>{@code m}</b> de disciplinas selecionadas e outro conjunto <i>H<sub>D</sub></i> de 
	 * horários correspondentes, é realizada a associação e agrupamento de todos os horários por turma e, em seguida, aplica-se a 
	 * restrição {@link Model #allDifferent(IntVar[], String)}, a qual mantém apenas uma oferta de aula por horário.
	 */

	private void manterDisciplinasComHorarioUnicoConstraint() {
		
		varHorariosPeriodo = new IntVar[periodos.length][];
		
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
	
	private void manterHorariosPeriodoEntreConstraint() {
		
		for (DetalheTimetable detalhe : timetable.getDetalhes()) {
			
			Tuples tuples = new Tuples(true);
			
			if (detalhe.isTipoCriterioHorario()) {
				
				Periodo periodo = detalhe.getPeriodo();
				Date horario = detalhe.getHorario();
				
				int horarios [] = recuperaHorarios(detalhe.getTipoCriterioTimetable(), horario);
				
				List<Timeslot> list = getTimeslotsPeriodo(periodo.getCodigo());
				
				for (int i = 0; i < list.size(); i++) {
					
					Timeslot timeslot = list.get(i);
					
					for (int j = 0; j < horarios.length; j++) {						
						tuples.add(timeslot.getDisciplina().getValue(), horarios[j]);
					}
				}
				
				for (int i = 0; i < list.size(); i++) {
					
					Timeslot timeslot = list.get(i);
					
					for (int j = 0; j < timeslot.getHorarios().size(); j++) {						
						model.table(timeslot.getDisciplina(), timeslot.getHorarios().get(j), tuples).post();
					}
				}
			}
		}
	}
	
	/**
	 * <b>Restrição Forte:</b> <br>
	 *  
	 * Um professor não pode ser selecionado para ministrar duas aulas de disciplinas diferentes
	 * no mesmo horário. Dado um conjunto de <b>{@code n}</b> horários de um professor, é aplicada a restrição 
	 * {@link Model #allDifferent(IntVar[], String)}, a qual mantém um professor com apenas uma ocorrência de horário.
	 */

	private void manterHorariosOrdenadosConstraint() {
		
		for (int i = 0; i < timeslots.size(); i++) {
		
			for (int j = 0; j < timeslots.get(i).getHorarios().size(); j++) {
				
				IntVar d1 = timeslots.get(i).getHorarios().get(j);
				IntVar d2 = null;
				
				if ((j+1) < timeslots.get(i).getHorarios().size()) {
					
					d2 = timeslots.get(i).getHorarios().get(j+1);
					model.arithm(d1, "<", d2).post();
				}
			}
		}
	}
	
	/**
	 * <b>Restrição Forte (Pedagógica):</b> <br>
	 * 
	 * Restrição complementar à {@link TimetableHandler #manterHorariosConsecutivosConstraint()}.
	 * Deve haver um intervalo mínimo de dias entre as ofertas consecutivas de aula de uma disciplina. Para o domínio apresentado,
	 * o intevalo mínimo é de 1 dia. Para satisfazê-la, dado um conjunto de <b>{@code n}</b> horários de uma disciplina <i>D<sub>i</sub></i>, 
	 * aplica-se a restrição {@link Model #arithm(IntVar, String, IntVar, String, int)}, a qual mantém entre os
	 * horários <i>H<sub>i1</sub></i> e <i>H<sub>i2</sub></i>, a diferença de valor resultante em, no mínimo, um dia .
	 */
	
	private void manterHorariosAlternadosConstraint() {
		
		for (int i = 0; i < timeslots.size(); i++) {
			
			Timeslot timeslot = timeslots.get(i);
			
			int aula = aulas[i];
			
			if (aula == 6) {
				
				IntVar horario1 = timeslot.getHorarios().get(0);
				IntVar horario2 = timeslot.getHorarios().get(1);
				IntVar horario3 = timeslot.getHorarios().get(2);
				IntVar horario4 = timeslot.getHorarios().get(3);
				IntVar horario5 = timeslot.getHorarios().get(4);
				IntVar horario6 = timeslot.getHorarios().get(5);
				
				IntVar local1 = timeslot.getLocais().get(0);
				IntVar local2 = timeslot.getLocais().get(1);
				IntVar local3 = timeslot.getLocais().get(2);
				IntVar local4 = timeslot.getLocais().get(3);
				IntVar local5 = timeslot.getLocais().get(4);
				IntVar local6 = timeslot.getLocais().get(5);
				
				model.arithm(local1, "=", local2).post();
				model.arithm(local5, "=", local6).post();
				
				if (getTimetable().isMesmoLocalDisciplina()) {
					model.arithm(local2, "=", local3).post();
					model.arithm(local3, "=", local4).post();
					model.arithm(local4, "=", local5).post();
				}
				
				model.or(
						model.and(
								model.arithm(horario3, "-", horario1, "=", 2),
								model.arithm(horario4, "-", horario3, getTimetable().isMesmoHorarioDisciplina() ? "=" : ">=", 16),
								model.arithm(horario6, "-", horario4, "=", 2),
								model.arithm(local2, "=", local3),
								model.arithm(local4, "=", local5)),
						
						model.and(
								model.arithm(horario3, "-", horario2, getTimetable().isMesmoHorarioDisciplina() ? "=" : ">=", 17),
								model.arithm(horario5, "-", horario4, getTimetable().isMesmoHorarioDisciplina() ? "=" : ">=", 17),
								model.arithm(local3, "=", local4)
								)
						).post();
				
			
			} else if (aula == 5) {
				
				IntVar horario1 = timeslot.getHorarios().get(0);
				IntVar horario3 = timeslot.getHorarios().get(2);
				IntVar horario4 = timeslot.getHorarios().get(3);
				IntVar horario5 = timeslot.getHorarios().get(4);
				
				IntVar local1 = timeslot.getLocais().get(0);
				IntVar local2 = timeslot.getLocais().get(1);
				IntVar local3 = timeslot.getLocais().get(2);
				IntVar local4 = timeslot.getLocais().get(3);
				IntVar local5 = timeslot.getLocais().get(4);
				
				model.arithm(horario3, "-", horario1, "=", 2).post();
				model.notMember(horario3, new int [] {0, 9, 18, 27, 36}).post();
				model.arithm(horario4, "-", horario3, getTimetable().isMesmoHorarioDisciplina() ? "=" : ">=", 16).post();
				model.arithm(horario5, "-", horario4, "=", 1).post();
				
				model.arithm(local1, "=", local2).post();
				model.arithm(local2, "=", local3).post();
				model.arithm(local4, "=", local5).post();
				
				if (getTimetable().isMesmoLocalDisciplina()) {
					model.arithm(local3, "=", local4).post();
				}
								
			} else if (aula == 4) {
				
				IntVar horario1 = timeslot.getHorarios().get(0);
				IntVar horario2 = timeslot.getHorarios().get(1);
				IntVar horario3 = timeslot.getHorarios().get(2);
				IntVar horario4 = timeslot.getHorarios().get(3);
				
				IntVar local1 = timeslot.getLocais().get(0);
				IntVar local2 = timeslot.getLocais().get(1);
				IntVar local3 = timeslot.getLocais().get(2);
				IntVar local4 = timeslot.getLocais().get(3);
				
				model.arithm(horario2, "-", horario1, "=", 1).post();
				model.arithm(horario3, "-", horario2,  getTimetable().isMesmoHorarioDisciplina() ? "=" : ">=", 17).post();
				model.arithm(horario4, "-", horario3, "=", 1).post();
				
				model.arithm(local1, "=", local2).post();
				model.arithm(local3, "=", local4).post();
				
				if (getTimetable().isMesmoLocalDisciplina()) {
					model.arithm(local2, "=", local3).post();
				}
				
			} else if (aula == 3) {
				
				IntVar horario1 = timeslot.getHorarios().get(0);
				IntVar horario2 = timeslot.getHorarios().get(1);
				IntVar horario3 = timeslot.getHorarios().get(2);
				
				IntVar local1 = timeslot.getLocais().get(0);
				IntVar local2 = timeslot.getLocais().get(1);
				IntVar local3 = timeslot.getLocais().get(2);
				
				model.arithm(horario2, "-", horario1, "=", 1).post();
				model.arithm(horario3, "-", horario2, "=", 1).post();
				
				model.arithm(local1, "=", local2).post();
				model.arithm(local2, "=", local3).post();
				
				model.notMember(horario3, new int [] {0, 9, 18, 27, 36}).post();
				model.notMember(horario1, new int [] {8, 17, 24, 32, 40}).post();
				
			} else if (aula == 2) {
				
				IntVar horario1 = timeslot.getHorarios().get(0);
				IntVar horario2 = timeslot.getHorarios().get(1);
				
				IntVar local1 = timeslot.getLocais().get(0);
				IntVar local2 = timeslot.getLocais().get(1);
				
				model.arithm(horario2, "-", horario1, "=", 1).post();
				model.arithm(local1, "=", local2).post();
			}
		}
	}
	
	/**
	 * <b>Restrição Forte:</b> <br>
	 * 
	 * Deve haver uma quantidade mínima de ofertas de aula consecutivas para uma disciplina. Para o domínio apresentado, 
	 * o mínimo de aulas é {@code 2}. A partir do conjunto <i>H<sub>i</sub></i> de horários de uma disciplina <i>D</i>, é aplicada
	 * a restrição {@link Model #arithm(IntVar, String, IntVar, String, int)}, a qual mantém entre os
	 * horários <i>H<sub>i1</sub></i> e <i>H<sub>i2</sub></i>, a diferença de valor em 1, ou seja, consecutivos.
	 */

	private void manterHorariosConsecutivosConstraint() {
		
		for (int i = 0; i < timeslots.size(); i++) {
			
			Timeslot timeslot = timeslots.get(i);
			
			for (int j = 0; j < timeslot.getHorarios().size(); j++) {
				
				IntVar horario2 = null;
				
				if ((j+1) < timeslot.getHorarios().size()) {
					
					horario2 = timeslot.getHorarios().get(++j);
					
					if (j % 2 != 0) {
						model.notMember(horario2, new int[]{0, 9, 18, 27, 36}).post();
					}
				}
			}
		}
	}
	
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
	
	
	private void manterLocaisComHorarioUnicoConstraint() {
		
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
	
	private void manterDisciplinasComAulaLaboratorioConstraint() {
		
		Tuples tuples = new Tuples(true);
		
		TipoLocal salaAula = tiposLocal.byId(2);
		
		int [] laboratoriosId = allByDepartamento(timetable.getMatrizCurricular().getCurso().getDepartamento());
		int [] salasId = allByTipoLocal(salaAula);
		
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
	
	private int [] allByTipoLocal(TipoLocal tipoLocal) {
		
		List<Integer> locaisId = new ArrayList<Integer>();
		
		for (Local local : colLocais) {
			
			if (local.getTipoLocal().getId() == tipoLocal.getId()) {
				locaisId.add(colLocais.indexOf(local));
			}
		}
		
		return Arrays.stream(locaisId.toArray(new Integer[locaisId.size()])).mapToInt(Integer::intValue).toArray();
	}
	
	private int [] allByDepartamento(Departamento departamento) {
		
		List<Integer> locaisId = new ArrayList<Integer>();
		
		for (Local local : colLocais) {
			
			if (local.getDepartamento() != null && local.getDepartamento().getId() == departamento.getId()) {
				locaisId.add(colLocais.indexOf(local));
			}
		}
		
		return Arrays.stream(locaisId.toArray(new Integer[locaisId.size()])).mapToInt(Integer::intValue).toArray();
		
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
	
	private int [] getHorariosIndisponiveisProfessor(Professor professor) {
		
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
	
	private int [] getHorariosDisponiveisProfessor(Professor professor) {
		
		List<Integer> lista = new ArrayList<Integer>(Arrays.asList(IntStream.rangeClosed(0, horariosId.length - 1).boxed().toArray(Integer[]::new)));
		
		int [] listaIndisponiveis = getHorariosIndisponiveisProfessor(professor);
		
		List<Integer> temp = new ArrayList<Integer>(IntStream.of(listaIndisponiveis).boxed().collect(Collectors.toList()));
		
		lista.removeAll(temp);
		
		return Arrays.stream(lista.toArray(new Integer[lista.size()])).mapToInt(Integer::intValue).toArray();
	}
	
	private int [] getDisciplinasPorPeriodo(int periodo) {
		return periodos[periodo-1];
	}
	
	private List<Timeslot> getTimeslotsPeriodo(int periodo) {
		
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
	
	@SuppressWarnings("unused")
	private List<DetalheDisciplina> recuperaEletivas(Periodo periodo) {
		
		List<DetalheDisciplina> detalhes = new ArrayList<DetalheDisciplina>();
		
		for (DetalheTimetable detalhe : timetable.getDetalhes()) {
			
			if (detalhe.isTipoCriterioEletiva() && detalhe.getPeriodo().getId() == periodo.getId()) {
				
				DetalheDisciplina detalheDisciplina = recuperaDetalheMatrizCurricular(periodo, detalhe.getDisciplina());
				
				if (detalheDisciplina != null) {				
					detalhes.add(detalheDisciplina);
				}
			}
		}
		
		return detalhes;
	}
	
	private DetalheDisciplina recuperaDetalheMatrizCurricular(Periodo periodo, Disciplina disciplina) {
		
		for (DetalheDisciplina detalhe : periodo.getDetalhes()) {
			
			if (detalhe.getDisciplina().getId() == disciplina.getId())
				return detalhe;
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
	
	private int [] recuperaHorarios(TipoCriterioTimetable tipoCriterio, Date horario) {
		
		List<Integer> list = new ArrayList<Integer>();
		
		for (int i = 0; i < colHorarios.size(); i++) {
			
			Horario h = colHorarios.get(i);
			
			if (tipoCriterio.getId() == TipoCriterioTimetable.HORARIO_DE_INICIO_ATE) {
				
				if (h.getHoraInicio().before(horario)) {
					list.add(i);
				}
				
			} else if (tipoCriterio.getId() == TipoCriterioTimetable.HORARIO_DE_INICIO_APOS) {
				
				if (h.getHoraInicio().after(horario)) {
					list.add(i);
				}
			}
		}
		
		return Arrays.stream(list.toArray(new Integer[list.size()])).mapToInt(Integer::intValue).toArray();
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