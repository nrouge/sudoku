package com.nrouge.sudoku.solver;

import com.nrouge.sudoku.model.Grille;

/**
 * Thread pour résolution multi-threadée
 * @author Nicolas Rougé
 */
public final class SolverThread extends Thread {
	
	//private static final Log log = LogFactory.getLog(SolverThread.class);
	
	//paramètres d'entrée du solver
	private final Grille grille;
	private final int level;
	private final ISolver solver;
	
	//résultat du traitement effectué par le thread
	private Boolean result;
	private SolverException solverException;
	private boolean finished = false;
	
	
	public SolverThread(ThreadGroup threadGroup, Grille grille, int level, ISolver solver, String name) {
		super(threadGroup, Thread.currentThread().getName()+"->"+name);
		this.grille = grille;
		this.level = level;
		this.solver = solver;
	}
	
	/**
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		try {
			result = Boolean.valueOf(solver.solve(grille, level));
			/*if (log.isInfoEnabled()) {
				log.info("termine avec "+result);
				if (result.booleanValue()) log.debug("Grille résolue :\n"+grille);
			}*/
		} catch (SolverException se) {
			this.solverException = se;
			//if (log.isWarnEnabled()) log.info("ERREUR "+se.getMessage());
		} catch (Throwable t) {
			t.printStackTrace(System.err);
		} finally {
			finished = true;
		}
	}

	/**
	 * Returns the grille
	 * @return Grille
	 */
	public Grille getGrille() {
		return grille;
	}

	/**
	 * Returns the result
	 * @return Boolean
	 */
	public Boolean getResult() {
		return result;
	}

	/**
	 * Returns the finished
	 * @return boolean
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * Returns the solverException
	 * @return SolverException
	 */
	public SolverException getSolverException() {
		return solverException;
	}

}
