package com.nrouge.sudoku.gui.swing;

import java.util.Iterator;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.nrouge.sudoku.model.Case;
import com.nrouge.sudoku.solver.ICaseChangeListener;
import com.nrouge.sudoku.solver.ISolver;
import com.nrouge.sudoku.solver.MultipleSolutionException;
import com.nrouge.sudoku.solver.SolverFactory;
import com.nrouge.sudoku.solver.UndeterminedSolutionException;
import com.nrouge.sudoku.solver.UnsolvableCaseException;

/**
 * 
 * @author Nicolas Rougé
 */
public final class ResolutionUtils {
	
	private ResolutionUtils() { }
	
	public static void resoudre(SwingGUIConfig config) {
		//debug
		config.setVisualResolution(true);

		config.setShowPossibilites(true);
		
		if (config.isVisualResolution()) {
			new ResolutionThread(config).start();
		} else {
			doSolve(config, null);
		}
	}
	
	private static final void doSolve(SwingGUIConfig config, ICaseChangeListener listener) {
		Map casePanelMap = config.getCasePanelMap();
		SudokuSwingGUI sudokuSwingGUI = config.getSudokuSwingGUI();
		if (listener != null) {
			// en mode de résolution visuel, on fige les données saisies par l'utilisateur ...
			for (Iterator it = casePanelMap.keySet().iterator(); it.hasNext(); ) {
				Case c = (Case) it.next();
				CasePanel cp = (CasePanel) casePanelMap.get(c);
				cp.setSauvegardePossibilites(c.getPossibilites());
				cp.setImmutable(c.isSolved());
			}
			// ... et on repaint tout
			sudokuSwingGUI.repaint();
		}
		// on lance la résolution
		ISolver solver = SolverFactory.createDefaultSolver();
		try {
			solver.solve(config.getGrille(), config.getResolutionLevel(), listener);
		} catch (UnsolvableCaseException uce) {
			JOptionPane.showMessageDialog(sudokuSwingGUI, uce.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		} catch (MultipleSolutionException mse) {
			JOptionPane.showMessageDialog(sudokuSwingGUI, mse.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		} catch (UndeterminedSolutionException e) {
		}
		//on fige les classes résolues et on resauvegarde les possibilités
		for (Iterator it = casePanelMap.keySet().iterator(); it.hasNext(); ) {
			Case c = (Case) it.next();
			CasePanel cp = (CasePanel) casePanelMap.get(c);
			cp.setSauvegardePossibilites(c.getPossibilites());
			cp.setImmutable(c.isSolved());
		}
		// on repaint tout
		sudokuSwingGUI.repaint();
	}
	
	private static final class ResolutionThread extends Thread {
		private final SwingGUIConfig config;
		private ResolutionThread(SwingGUIConfig config) {
			this.config = config;
		}
		public void run() {
			JDialog dialog = new JDialog(config.getSudokuSwingGUI(), "Résolution", true);
			dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			JPanel panel = new JPanel();
			panel.add(new JLabel("Résolution en cours..."));
			dialog.setContentPane(panel);
			dialog.pack();
			SolverThread st = new SolverThread(config, dialog);
			st.start();
			dialog.setVisible(true);
			try { st.join(); }
			catch (InterruptedException ie) { }
		}
	}
	private static final class SolverThread extends Thread {
		private final SwingGUIConfig config;
		private final JDialog dialog;
		private SolverThread(SwingGUIConfig config, JDialog dialog) {
			this.config = config;
			this.dialog = dialog;
		}
		public void run() {
			doSolve(config, new SwingCaseChangeListener(config.getCasePanelMap(), 100));
			dialog.setVisible(false);
		}
	}
	
	private static final class SwingCaseChangeListener implements ICaseChangeListener {
		
		private final Map casePanelMap;
		private final long sleepTime;
		
		public SwingCaseChangeListener(Map casePanelMap, long sleepTime) {
			this.casePanelMap = casePanelMap;
			this.sleepTime = sleepTime;
		}

		@Override
		public void caseHasChanged(Case c) {
			sleep();
			CasePanel cp = (CasePanel) casePanelMap.get(c);
			cp.setImmutable(c.isSolved());
			cp.repaint();
		}
		
		private void sleep() {
			if (sleepTime == 0) return;
			try { Thread.sleep(sleepTime); }
			catch (InterruptedException ie) { }
		}

	}


}
