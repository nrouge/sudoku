package com.nrouge.sudoku.solver;

import com.nrouge.sudoku.model.Grille;

/**
 * Thread pour résolution
 * @author Nicolas Rougé
 */
public final class SolverThread extends Thread {
	
	//instance du solver utilisé
	private final ISolver solver;
	
	//paramètres d'entrée du solver
	private final Grille grille;
	private final int level;
	private final ICaseChangeListener caseChangeListener;
	
	//résultat du traitement effectué par le thread
	private Boolean result;
	private UnsolvableCaseException unsolvableCaseException;
	private MultipleSolutionException multipleSolutionException;
	private UndeterminedSolutionException undeterminedSolutionException;
	
	public SolverThread(ThreadGroup threadGroup, String threadName, ISolver solver, Grille grille, int level, ICaseChangeListener caseChangeListener) {
		super(threadGroup, Thread.currentThread().getName()+"->"+threadName);
		this.grille = grille;
		this.level = level;
		this.solver = solver;
		this.caseChangeListener = caseChangeListener;
	}
	
	public SolverThread(String threadName, ISolver solver, Grille grille, int level, ICaseChangeListener caseChangeListener) {
		this(Thread.currentThread().getThreadGroup(), threadName, solver, grille, level, caseChangeListener);
	}
	
	public SolverThread(ThreadGroup threadGroup, ISolver solver, Grille grille, int level, ICaseChangeListener caseChangeListener) {
		this(threadGroup, solver.getClass().getName(), solver, grille, level, caseChangeListener);
	}
	
	public SolverThread(ISolver solver, Grille grille, int level, ICaseChangeListener caseChangeListener) {
		this(solver.getClass().getName(), solver, grille, level, caseChangeListener);
	}
	
	public SolverThread(ThreadGroup threadGroup, String threadName, ISolver solver, Grille grille, int level) {
		this(threadGroup, threadName, solver, grille, level, null);
	}
	
	public SolverThread(String threadName, ISolver solver, Grille grille, int level) {
		this(Thread.currentThread().getThreadGroup(), threadName, solver, grille, level);
	}
	
	public SolverThread(ThreadGroup threadGroup, ISolver solver, Grille grille, int level) {
		this(threadGroup, solver.getClass().getName(), solver, grille, level);
	}
	
	public SolverThread(ISolver solver, Grille grille, int level) {
		this(solver.getClass().getName(), solver, grille, level);
	}
	
	/**
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		try {
			result = Boolean.valueOf(solver.solve(grille, level, caseChangeListener));
		} catch (UnsolvableCaseException uce) {
			unsolvableCaseException = uce;
		} catch (MultipleSolutionException mse) {
			multipleSolutionException = mse;
		} catch (UndeterminedSolutionException use) {
			undeterminedSolutionException = use;
		}
	}

	/**
	 * Returns the result
	 * @return Boolean
	 */
	public Boolean getResult() {
		return result;
	}

	/**
	 * Returns the multipleSolutionException
	 * @return MultipleSolutionException
	 */
	public MultipleSolutionException getMultipleSolutionException() {
		return multipleSolutionException;
	}

	/**
	 * Returns the undeterminedSolutionException
	 * @return UndeterminedSolutionException
	 */
	public UndeterminedSolutionException getUndeterminedSolutionException() {
		return undeterminedSolutionException;
	}

	/**
	 * Returns the unsolvableCaseException
	 * @return UnsolvableCaseException
	 */
	public UnsolvableCaseException getUnsolvableCaseException() {
		return unsolvableCaseException;
	}

}
