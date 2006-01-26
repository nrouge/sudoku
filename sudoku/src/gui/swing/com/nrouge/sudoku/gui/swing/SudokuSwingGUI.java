package com.nrouge.sudoku.gui.swing;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.nrouge.sudoku.gui.common.GUIConfig;
import com.nrouge.sudoku.model.Grille;
import com.nrouge.sudoku.util.SudokuFileUtils;

/**
 * 
 * @author Nicolas Rougé
 */
public class SudokuSwingGUI extends JFrame {
	
	private SwingGUIConfig config = new SwingGUIConfig((byte) 3, this);
	
	private SudokuSwingGUI() {
		super("Sudoku");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

	/**
	 * @see java.awt.Container#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		System.out.println("SudokuSwingGUI:paint");
		super.paint(g);
	}
	
	
}
