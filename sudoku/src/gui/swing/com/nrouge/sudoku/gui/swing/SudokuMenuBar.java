package com.nrouge.sudoku.gui.swing;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.filechooser.FileFilter;

import com.nrouge.sudoku.gui.common.GUIConfig;
import com.nrouge.sudoku.model.Case;
import com.nrouge.sudoku.model.Grille;
import com.nrouge.sudoku.solver.ISolver;
import com.nrouge.sudoku.solver.MultipleSolutionException;
import com.nrouge.sudoku.solver.SolverException;
import com.nrouge.sudoku.solver.SolverFactory;
import com.nrouge.sudoku.solver.SolverThread;
import com.nrouge.sudoku.solver.UndeterminedSolutionException;
import com.nrouge.sudoku.solver.UnsolvableCaseException;
import com.nrouge.sudoku.util.SudokuFileUtils;

/**
 * 
 * @author Nicolas Rougé
 */
public class SudokuMenuBar extends JMenuBar {
	
	private static final String[] NIVEAUX_DIFFICULTES = {
		"Trivial",
		"Facile",
		"Moyen",
		"Difficile",
		"Démoniaque"
	};
		
	
	public SudokuMenuBar(SwingGUIConfig config) {
		add(new SudokuMenu(config));
		add(new ResolutionMenu(config));
		add(new GenerationMenu(config));
	}
	
	private static abstract class AbstractMenuItem extends JMenuItem implements ActionListener {
		SwingGUIConfig config;
		private AbstractMenuItem(SwingGUIConfig config, String text) {
			super(text);
			this.config = config;
			addActionListener(this);
		}
	}
	private static abstract class AbstractSubRadioButtonMenu extends JMenu implements ActionListener {
		SwingGUIConfig config;
		ButtonGroup group = new ButtonGroup();
		private AbstractSubRadioButtonMenu(SwingGUIConfig config, String text) {
			super(text);
			this.config = config;
		}
		protected void addRadioButton(String text) {
			JRadioButtonMenuItem rbmi = new JRadioButtonMenuItem(text);
			rbmi.setSelected(isSelected(text));
			rbmi.addActionListener(this);
			group.add(rbmi);
			add(rbmi);
		}
		protected abstract boolean isSelected(String text);
		public void paint(Graphics g) {
			for (Enumeration e = group.getElements(); e.hasMoreElements(); ) {
				JRadioButtonMenuItem rbmi = (JRadioButtonMenuItem) e.nextElement();
				rbmi.setSelected(isSelected(rbmi.getText()));
			}
			super.paint(g);
		}
	}
	private static class SudokuMenu extends JMenu {
		private SudokuMenu(SwingGUIConfig config) {
			super("Sudoku");
			setMnemonic(KeyEvent.VK_S);
			add(new NouveauMenuItem(config));
			add(new PuissanceSubMenu(config));
			addSeparator();
			add(new OuvrirMenuItem(config));
			add(new EnregistrerMenuItem(config));
			addSeparator();
			add(new QuitterMenuItem(config));
		}
		private static class NouveauMenuItem extends AbstractMenuItem {
			private NouveauMenuItem(SwingGUIConfig config) {
				super(config, "Nouveau");
				setMnemonic(KeyEvent.VK_N);
			}
			public void actionPerformed(ActionEvent ae) {
				config.reset();
				recreerContenu(config);
			}
		}
		private static class PuissanceSubMenu extends AbstractSubRadioButtonMenu {
			private PuissanceSubMenu(SwingGUIConfig config) {
				super(config, "Puissance");
				setMnemonic(KeyEvent.VK_P);
				for (int i = 2; i <= 5; i++) addRadioButton(Integer.toString(i));
			}
			public void actionPerformed(ActionEvent ae) {
				config.setPuissance(Byte.valueOf(ae.getActionCommand()).byteValue());
			}
			protected boolean isSelected(String text) {
				return text.equals(Byte.toString(config.getPuissance()));
			}
		}
		private static class OuvrirMenuItem extends AbstractMenuItem {
			private JFileChooser fc = new JFileChooser();
			private OuvrirMenuItem(SwingGUIConfig config) {
				super(config, "Ouvrir");
				setMnemonic(KeyEvent.VK_O);
				fc.addChoosableFileFilter(new FileFilter(){
					public boolean accept(File f) {
						return f.isDirectory() || f.getName().endsWith(".sdk");
					}
					public String getDescription() {
						return "SDK Files";
					} });
			}
			public void actionPerformed(ActionEvent ae) {
				SudokuSwingGUI frame = config.getSudokuSwingGUI();
				if (fc.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) return;
				Grille grille;
				try {
					grille = SudokuFileUtils.importFromFile(fc.getSelectedFile());
				} catch (IOException ioe) {
					JOptionPane.showMessageDialog(frame, ioe, "Erreur", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (grille == null) {
					JOptionPane.showMessageDialog(frame, "Le fichier n'est pas au bon format", "Erreur", JOptionPane.ERROR_MESSAGE);
					return;
				}
				config.reset(grille);
				recreerContenu(config);
			}
		}
		private static class EnregistrerMenuItem extends AbstractMenuItem {
			private EnregistrerMenuItem(SwingGUIConfig config) {
				super(config, "Enregistrer");
				setMnemonic(KeyEvent.VK_E);
			}
			public void actionPerformed(ActionEvent ae) {
				System.out.println("TODO:Enregistrer");
			}
		}
		private static class QuitterMenuItem extends AbstractMenuItem {
			private QuitterMenuItem(SwingGUIConfig config) {
				super(config, "Quitter");
				setMnemonic(KeyEvent.VK_Q);
			}
			public void actionPerformed(ActionEvent ae) {
				System.exit(0);
			}
		}
	}
	
	private static class ResolutionMenu extends JMenu {
		private ResolutionMenu(SwingGUIConfig config) {
			super("Résolution");
			setMnemonic(KeyEvent.VK_R);
			add(new ResoudreMenuItem(config));
			addSeparator();
			add(new NiveauSubMenu(config));
		}
		private static class ResoudreMenuItem extends AbstractMenuItem {
			private ResoudreMenuItem(SwingGUIConfig config) {
				super(config, "Résoudre");
				setMnemonic(KeyEvent.VK_R);
			}
			public void actionPerformed(ActionEvent ae) {
				ISolver solver = SolverFactory.createDefaultSolver();
				SolverThread st = new SolverThread(solver, config.getGrille(), config.getResolutionLevel(), new SwingCaseChangeListener(config.getCasePanelMap(), 100));
				st.start();
				try { st.join();
				} catch (InterruptedException ie) { }
				if (st.getResult() == null) {
					SolverException se = st.getMultipleSolutionException();
					if (se == null) se = st.getUnsolvableCaseException();
					if (se != null) {
						JOptionPane.showMessageDialog(config.getSudokuSwingGUI(), se.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}
				//on fige les valeurs saisies par l'utilisateur, et on sauvegarde les possibilités
				Map casePanelMap = config.getCasePanelMap();
				for (Iterator it = casePanelMap.keySet().iterator(); it.hasNext(); ) {
					Case c = (Case) it.next();
					CasePanel cp = (CasePanel) casePanelMap.get(c);
					cp.setSauvegardePossibilites(c.getPossibilites());
					cp.setImmutable(c.isSolved());
				}
				//l'affichage montre les possibilités et on repaint tout
				config.setShowPossibilites(true);
				config.getSudokuSwingGUI().repaint();
			}
		}
		private static class NiveauSubMenu extends AbstractSubRadioButtonMenu {
			private NiveauSubMenu(SwingGUIConfig config) {
				super(config, "Niveau");
				setMnemonic(KeyEvent.VK_N);
				for (int i = 0; i < NIVEAUX_DIFFICULTES.length; i++) addRadioButton(NIVEAUX_DIFFICULTES[i]);
			}
			public void actionPerformed(ActionEvent ae) {
				config.setResolutionLevel(getNiveau(ae.getActionCommand()));
			}
			protected boolean isSelected(String text) {
				return text.equals(NIVEAUX_DIFFICULTES[config.getResolutionLevel()]);
			}
		}
	}
	
	private static class GenerationMenu extends JMenu {
		private GenerationMenu(SwingGUIConfig config) {
			super("Génération");
			setMnemonic(KeyEvent.VK_G);
			add(new GenererMenuItem(config));
			addSeparator();
			add(new NiveauSubMenu(config));
		}
		private static class GenererMenuItem extends AbstractMenuItem {
			private GenererMenuItem(SwingGUIConfig config) {
				super(config, "Générer");
				setMnemonic(KeyEvent.VK_G);
			}
			public void actionPerformed(ActionEvent ae) {
				//TODO
			}
		}
		private static class NiveauSubMenu extends AbstractSubRadioButtonMenu {
			private NiveauSubMenu(SwingGUIConfig config) {
				super(config, "Niveau");
				setMnemonic(KeyEvent.VK_N);
				for (int i = 0; i < NIVEAUX_DIFFICULTES.length; i++) addRadioButton(NIVEAUX_DIFFICULTES[i]);
			}
			public void actionPerformed(ActionEvent ae) {
				config.setGenerationLevel(getNiveau(ae.getActionCommand()));
			}
			protected boolean isSelected(String text) {
				return text.equals(NIVEAUX_DIFFICULTES[config.getGenerationLevel()]);
			}
		}
	}
	
	private static void recreerContenu(SwingGUIConfig config) {
		SudokuSwingGUI frame = config.getSudokuSwingGUI();
		GrillePanel gp = new GrillePanel(config);
		frame.setContentPane(gp);
		frame.pack();
	}

	private static int getNiveau(String niv) {
		final int length = NIVEAUX_DIFFICULTES.length;
		for (int i = 0; i < length; i++) {
			if (NIVEAUX_DIFFICULTES[i].equals(niv)) return i;
		}
		return 0;
	}

}
