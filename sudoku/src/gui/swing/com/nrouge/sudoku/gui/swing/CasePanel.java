package com.nrouge.sudoku.gui.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.EventListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.MouseInputListener;

import com.nrouge.sudoku.gui.common.GUIConfig;
import com.nrouge.sudoku.model.Case;
import com.nrouge.sudoku.model.CharValeurs;
import com.nrouge.sudoku.model.Grille;

/**
 * 
 * @author Nicolas Rougé
 */
public class CasePanel extends JPanel implements KeyListener, MouseListener, MouseWheelListener {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5066973626162910843L;
	
	private final Case cas;
	private boolean immutable;
	
	private boolean focused;
	
	private final GUIConfig config;
	private final Grille grille;
	private final CharValeurs cv;
	private final int length;
	private final byte puissance;
	private final JLabel label = new JLabel();
	
	public CasePanel(GUIConfig config, Case cas) {
		super();
		this.config = config;
		this.cas = cas;
		immutable = cas.isSolved();
		grille = config.getGrille();
		cv = grille.getCharValeurs();
		length = grille.getLength();
		puissance = grille.getPuissance();
		setPreferredSize(new Dimension(32, 32));
		setBackground(Color.white);
		setOpaque(true);
		setBorder(null);
		addKeyListener(this);
		addMouseListener(this);
		addMouseWheelListener(this);
		setLayout(new GridLayout());
		add(label);
	}

	/**
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	public void paint(Graphics graphics) {
		String newText = getLabelText();
		if (!newText.equals(label.getText())) {
			label.setText(newText);
			if (cas.isSolved()) {
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setHorizontalTextPosition(SwingConstants.CENTER);
				label.setVerticalAlignment(SwingConstants.CENTER);
				label.setVerticalTextPosition(SwingConstants.CENTER);
			} else if (config.isShowPossibilites()) {
				//TODO
			} else {
				//TODO
			}
		}
		label.setForeground((immutable) ? Color.black : Color.gray);
		setBackground(focused ? Color.lightGray : Color.white);
		super.paint(graphics);
	}
	
	private String getLabelText() {
		if (cas.isSolved()) {
			return Character.toString(cv.toChar(cas.getValeur()));
		} else if (config.isShowPossibilites()) {
			return "TODO";
		} else {
			return "";
		}
	}
	
	private void incrementerValeur() {
		if (immutable) return;
		if (cas.isSolved()) {
			int valeur = cas.getValeur() + 1;
			if (valeur == length) cas.reset(puissance); else cas.setValeur(valeur);
		} else {
			cas.setValeur(0);
		}
		repaint();
	}
	
	private void decrementerValeur() {
		if (immutable) return;
		if (cas.isSolved()) {
			int valeur = cas.getValeur() - 1;
			if (valeur == -1) cas.reset(puissance); else cas.setValeur(valeur);
		} else {
			cas.setValeur(length - 1);
		}
		repaint();
	}

	/**
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent ke) {
	}

	/**
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent ke) {
	}

	/**
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent ke) {
		System.out.println(cas.getId()+ke);
	}

	/**
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent me) {
		switch (me.getButton()) {
			case MouseEvent.BUTTON1: incrementerValeur(); break;
			case MouseEvent.BUTTON3: decrementerValeur(); break;
			default:
		}
	}

	/**
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent me) {
		requestFocus();
		focused = true;
		repaint();
	}

	/**
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent me) {
		focused = false;
		repaint();
	}

	/**
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent me) {
	}

	/**
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent me) {
	}

	/**
	 * @see java.awt.event.MouseWheelListener#mouseWheelMoved(java.awt.event.MouseWheelEvent)
	 */
	public void mouseWheelMoved(MouseWheelEvent mwe) {
		switch (mwe.getWheelRotation()) {
			case -1: incrementerValeur(); break;
			case  1: decrementerValeur(); break;
			default:
		}
	}
}
