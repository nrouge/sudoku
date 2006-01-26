package com.nrouge.sudoku.gui.swing;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.nrouge.sudoku.gui.common.GUIConfig;
import com.nrouge.sudoku.model.Grille;

/**
 * 
 * @author Nicolas Rougé
 */
public class GrillePanel extends JPanel {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8356229247659064982L;
	
	private final GUIConfig config;
	
	private final Grille grille;
	
	public GrillePanel(GUIConfig config) {
		this.config = config;
		grille = config.getGrille();
		final byte puissance = grille.getPuissance();
		final int length = grille.getLength();
		setLayout(new GridLayout(puissance, puissance, 2, 2));
		setBackground(Color.black);
		setOpaque(true);
		for (int a = 0; a < puissance; a++) {
			for (int b = 0; b < puissance; b++) {
				JPanel carre = new JPanel(new GridLayout(puissance, puissance, 1, 1));
				carre.setBackground(Color.black);
				for (int k = 0; k < length; k++) {
					int i = a * puissance + k / puissance;
					int j = b * puissance + k % puissance;
					carre.add(new CasePanel(config, grille.getCase(i, j)));
				}
				add(carre);
			}
		}
	}

}
