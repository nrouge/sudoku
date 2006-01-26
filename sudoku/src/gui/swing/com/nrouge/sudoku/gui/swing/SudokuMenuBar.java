package com.nrouge.sudoku.gui.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.nrouge.sudoku.gui.common.GUIConfig;
import com.nrouge.sudoku.util.SudokuFileUtils;

/**
 * 
 * @author Nicolas Rougé
 */
public class SudokuMenuBar extends JMenuBar {

	public SudokuMenuBar(SwingGUIConfig config) {
		add(new FichierMenu(config));
	}
	
	private static abstract class AbstractMenuItem extends JMenuItem implements ActionListener {
		SwingGUIConfig config;
		private AbstractMenuItem(SwingGUIConfig config, String text) {
			super(text);
			this.config = config;
			addActionListener(this);
		}
	}
	
	private static class FichierMenu extends JMenu {
		private FichierMenu(SwingGUIConfig config) {
			super("Fichier");
			setMnemonic(KeyEvent.VK_F);
			add(new OuvrirMenuItem(config));
			add(new EnregistrerMenuItem(config));
			addSeparator();
			add(new QuitterMenuItem(config));
		}
		private static class OuvrirMenuItem extends AbstractMenuItem {
			private OuvrirMenuItem(SwingGUIConfig config) {
				super(config, "Ouvrir");
				setMnemonic(KeyEvent.VK_O);
			}
			public void actionPerformed(ActionEvent ae) {
				System.out.println("TODO:Ouvrir");
				config.setGrille(SudokuFileUtils.importResource("gen44.sdk"));
				GrillePanel gp = new GrillePanel(config);
				config.getSudokuSwingGUI().setContentPane(gp);
				config.getSudokuSwingGUI().pack();
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
				System.out.println("TODO:Quitter");
			}
		}
	}
}
