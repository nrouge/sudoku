package com.nrouge.sudoku.gui.swing;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.nrouge.sudoku.generator.IGeneratorProgressionListener;
import com.nrouge.sudoku.generator.impl.JavaGenerator;
import com.nrouge.sudoku.model.Grille;

/**
 * 
 * @author Nicolas Rougé
 */
public class GenerationUtils {
	
	private GenerationUtils() { }

	/**
	 * @param config
	 */
	public static Grille generer(SwingGUIConfig config) {
		GeneratorDialog dialog = new GeneratorDialog(config);
		GeneratorThread gt = new GeneratorThread(config, dialog);
		gt.start();
		dialog.setVisible(true);
		try { gt.join(); }
		catch (InterruptedException ie) { }
		System.out.println("Grille générée :\n" + gt.getGrille());
		return gt.getGrille();
	}
	
	private static final class GeneratorThread extends Thread {
		private final SwingGUIConfig config;
		private final GeneratorDialog dialog;
		private Grille grille;
		private GeneratorThread(SwingGUIConfig config, GeneratorDialog dialog) {
			this.config = config;
			this.dialog = dialog;
		}
		public void run() {
			grille = new JavaGenerator()
			.generate(
				config.getPuissance(),
				config.getGenerationLevel(),
				new SwingGeneratorProgressionListener(
					config.getPuissance() * config.getPuissance(),
					dialog.getProgressBar()));
			dialog.setVisible(false);
		}
		private Grille getGrille() {
			return grille;
		}
	}
	
	private static final class SwingGeneratorProgressionListener implements IGeneratorProgressionListener {
		private final int length;
		private final JProgressBar progressBar;
		private int caseCreated;
		private int caseDeleted;
		private SwingGeneratorProgressionListener(int length, JProgressBar progressBar) {
			this.length = length;
			this.progressBar = progressBar;
		}
		public void caseCreated() {
			progressBar.setValue(caseCreated++);
		}
		public void caseDeleted() {
			progressBar.setValue(length * length + caseDeleted++);
		}
		public void reset() {
			caseCreated = 0;
			caseDeleted = 0;
		}
	}
	
	private static final class GeneratorDialog extends JDialog {
		private final JProgressBar progressBar;
		private GeneratorDialog(SwingGUIConfig config) {
			super(config.getSudokuSwingGUI(), "Génération", true);
			setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			JPanel panel = new JPanel();
			panel.add(new JLabel("Génération en cours..."));
			final int length = config.getPuissance() * config.getPuissance();
			progressBar = new JProgressBar(0, 2 * length * length);
			panel.add(progressBar);
			setContentPane(panel);
			pack();
		}
		private JProgressBar getProgressBar() {
			return progressBar;
		}
	}

}
