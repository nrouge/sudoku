package com.nrouge.sudoku.gui.swing;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * 
 * @author Nicolas Rougé
 */
public class SudokuSwingGUI extends JFrame {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3056763261165076856L;
	
	private SwingGUIConfig config;
	
	private SudokuSwingGUI() {
		super("Sudoku");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		SwingGUIConfig context = ContextUtils.loadConfig();
		if (context == null) {
			config = new SwingGUIConfig((byte) 3, this);
		} else {
			config = context;
			config.setSudokuSwingGUI(this);
		}
		
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
