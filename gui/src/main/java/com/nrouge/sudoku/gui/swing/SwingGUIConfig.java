package com.nrouge.sudoku.gui.swing;

import java.awt.Font;
import java.util.Map;

import com.nrouge.sudoku.gui.common.GUIConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Nicolas Roug�
 */
@Getter
@Setter
public class SwingGUIConfig extends GUIConfig {

	private static final float VALEUR_FONT_SIZE = 24f;
	private static final int VALEUR_FONT_STYLE = Font.BOLD;
	private static final float POSSIBILITE_FONT_SIZE = 10f;
	private static final int POSSIBILITE_FONT_STYLE = Font.PLAIN;

	/**
	 * R�f�rence sur la frame principale
	 */
	private transient SudokuSwingGUI sudokuSwingGUI;
	
	/**
	 * Fonte pour l'affichage des valeurs
	 */
	private final Font valeurFont;
	
	/**
	 * Fonte pour l'affichage des possibilit�s
	 */
	private final Font possibiliteFont;
	
	/**
	 * Mappe de correspondance entre les cases et leur �quivalent graphique, CasePanel
	 */
	private Map casePanelMap;
	
	/**
	 * Indique si la r�solution doit �tre visible par l'utilisateur
	 */
	private boolean visualResolution;

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

}
