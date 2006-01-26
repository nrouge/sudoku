package com.nrouge.sudoku.gui.swing;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * 
 * @author Nicolas Rougé
 */
public class SudokuSwingGUI extends JFrame {
	
	private SwingGUIConfig config = new SwingGUIConfig((byte) 3, this);
	
	private SudokuSwingGUI() {
		super("Sudoku");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setJMenuBar(new SudokuMenuBar(config));
		setContentPane(new GrillePanel(config));
		//setContentPane(new GrillePanel(SudokuFileUtils.importResource("gen34.sdk")));
		pack();
		setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new SudokuSwingGUI();
			}
		});
	}	
	
}
