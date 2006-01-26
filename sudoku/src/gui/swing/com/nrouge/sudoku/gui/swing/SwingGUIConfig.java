package com.nrouge.sudoku.gui.swing;

import javax.swing.RootPaneContainer;

import com.nrouge.sudoku.gui.common.GUIConfig;
import com.nrouge.sudoku.model.Grille;

/**
 * 
 * @author Nicolas Rougé
 */
public class SwingGUIConfig extends GUIConfig {
	
	private final SudokuSwingGUI sudokuSwingGUI;
	
	/**
	 * @param puissance
	 */
	public SwingGUIConfig(byte puissance, SudokuSwingGUI sudokuSwingGUI) {
		super(puissance);
		this.sudokuSwingGUI = sudokuSwingGUI;
	}

	/**
	 * Returns the sudokuSwingGUI
	 * @return SudokuSwingGUI
	 */
	public SudokuSwingGUI getSudokuSwingGUI() {
		return sudokuSwingGUI;
	}

}
