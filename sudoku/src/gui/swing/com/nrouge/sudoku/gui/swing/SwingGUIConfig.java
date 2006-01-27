package com.nrouge.sudoku.gui.swing;

import java.awt.Font;

import javax.swing.RootPaneContainer;

import com.nrouge.sudoku.gui.common.GUIConfig;
import com.nrouge.sudoku.model.Grille;

/**
 * 
 * @author Nicolas Rougé
 */
public class SwingGUIConfig extends GUIConfig {
	
	private static final float VALEUR_FONT_SIZE = 24f;
	private static final int VALEUR_FONT_STYLE = Font.BOLD;
	private static final float POSSIBILITE_FONT_SIZE = 10f;
	private static final int POSSIBILITE_FONT_STYLE = Font.PLAIN;

	/**
	 * Référence sur la frame principale
	 */
	private final SudokuSwingGUI sudokuSwingGUI;
	
	/**
	 * Fonte pour l'affichage des valeurs
	 */
	private final Font valeurFont;
	
	/**
	 * Fonte pour l'affichage des possibilités
	 */
	private final Font possibiliteFont;
	
	/**
	 * @param puissance
	 */
	public SwingGUIConfig(byte puissance, SudokuSwingGUI sudokuSwingGUI) {
		super(puissance);
		this.sudokuSwingGUI = sudokuSwingGUI;
		final Font font = sudokuSwingGUI.getContentPane().getFont();
		valeurFont = font.deriveFont(VALEUR_FONT_STYLE, VALEUR_FONT_SIZE);
		possibiliteFont = font.deriveFont(POSSIBILITE_FONT_STYLE, POSSIBILITE_FONT_SIZE);
	}

	/**
	 * Returns the sudokuSwingGUI
	 * @return SudokuSwingGUI
	 */
	public SudokuSwingGUI getSudokuSwingGUI() {
		return sudokuSwingGUI;
	}

	/**
	 * Returns the possibiliteFont
	 * @return Font
	 */
	public Font getPossibiliteFont() {
		return possibiliteFont;
	}

	/**
	 * Returns the valeurFont
	 * @return Font
	 */
	public Font getValeurFont() {
		return valeurFont;
	}

}
