package com.nrouge.sudoku.gui.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.nrouge.sudoku.model.Case;
import com.nrouge.sudoku.model.CharValeurs;
import com.nrouge.sudoku.model.Grille;
import com.nrouge.sudoku.util.PossibilitesUtils;

/**
 * 
 * @author Nicolas Rougé
 */
public class CasePanel extends JPanel implements KeyListener, MouseListener, MouseWheelListener {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5066973626162910843L;
	
	/**
	 * La case
	 */
	private final Case cas;
	
	/**
	 * Indique si la case ne peut pas changer de valeur
	 */
	private boolean immutable;
	
	/**
	 * Indique que la case a été chargée valorisée
	 */
	private boolean loaded;
	
	/**
	 * Indique si la case a le focus
	 */
	private boolean focused;
	
	/**
	 * Sauvegarde des possibilités d'une case
	 */
	private long sauvegardePossibilites;
	
	private final SwingGUIConfig config;
	private final Grille grille;
	private final CharValeurs cv;
	private final byte puissance;
	private final JLabel label = new JLabel();

	public CasePanel(SwingGUIConfig config, Case cas) {
		super();
		this.config = config;
		this.cas = cas;
		loaded = immutable = cas.isSolved();
		grille = config.getGrille();
		cv = grille.getCharValeurs();
		puissance = grille.getPuissance();
		sauvegardePossibilites = cas.getPossibilites();
		setPreferredSize(new Dimension(32, 32));
		setBackground(Color.white);
		setOpaque(true);
		setBorder(null);
		addKeyListener(this);
		addMouseListener(this);
		addMouseWheelListener(this);
		setLayout(new GridLayout());
		label.setFont(cas.isSolved() ? config.getValeurFont() : config.getPossibiliteFont());
		add(label);
	}

	/**
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	public void paint(Graphics graphics) {
		String newText = getLabelText();
		if (!newText.equals(label.getText())) {
			if (cas.isSolved()) {
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setHorizontalTextPosition(SwingConstants.CENTER);
				label.setVerticalAlignment(SwingConstants.CENTER);
				label.setVerticalTextPosition(SwingConstants.CENTER);
				label.setFont(config.getValeurFont());
			} else {
				label.setHorizontalAlignment(SwingConstants.LEFT);
				label.setHorizontalTextPosition(SwingConstants.LEFT);
				label.setVerticalAlignment(SwingConstants.TOP);
				label.setVerticalTextPosition(SwingConstants.TOP);
				label.setFont(config.getPossibiliteFont());
			}
			label.setText(newText);
		}
		label.setForeground(immutable ? (loaded ? Color.black : Color.blue) : Color.gray);
		setBackground(focused && !immutable ? Color.lightGray : Color.white);
		super.paint(graphics);
	}
	
	private String getLabelText() {
		if (cas.isSolved()) {
			return Character.toString(cv.toChar(cas.getValeur()));
		} else if (config.isShowPossibilites()) {
			final int[] valeurs = PossibilitesUtils.getValeursPossibles(cas.getPossibilites());
			final int valeursLength = valeurs.length;
			StringBuffer sb = new StringBuffer(valeursLength);
			for (int i = 0; i < valeursLength; i++) {
				sb.append(cv.toChar(valeurs[i]));
			}
			return SwingGUIUtils.getWrappedLabelText(this, cas.isSolved() ? config.getValeurFont() : config.getPossibiliteFont(), sb.toString());
		} else {
			return "";
		}
	}
	
	private void changerValeur(boolean plus) {
		if (immutable) return;
		int[] valeurs = PossibilitesUtils.getValeursPossibles(sauvegardePossibilites);
		int valeursLength = valeurs.length;
		if (valeursLength == 0) return;
		if (!cas.isSolved()) {
			cas.setValeur((plus) ? valeurs[0] : valeurs[valeursLength - 1]);
		} else {
			int idx = Arrays.binarySearch(valeurs, cas.getValeur());
			idx += (plus) ? 1 : -1;
			if ((idx <= -1) || (idx >= valeursLength)) {
				cas.reset(puissance);
				cas.setPossibilites(sauvegardePossibilites);
			} else {
				cas.setValeur(valeurs[idx]);
			}
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
			case MouseEvent.BUTTON1: changerValeur(true); break;
			case MouseEvent.BUTTON3: changerValeur(false); break;
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
			case -1: changerValeur(true); break;
			case  1: changerValeur(false); break;
			default:
		}
	}

	/**
	 * Sets the immutable
	 * @param immutable The immutable to set.
	 */
	public void setImmutable(boolean immutable) {
		this.immutable = immutable;
	}

	/**
	 * Sets the sauvegardePossibilites
	 * @param sauvegardePossibilites The sauvegardePossibilites to set.
	 */
	public void setSauvegardePossibilites(long sauvegardePossibilites) {
		this.sauvegardePossibilites = sauvegardePossibilites;
	}
}
