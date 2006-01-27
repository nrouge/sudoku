package com.nrouge.sudoku.model;

public final class Grille implements Cloneable {
	
	/**
	 * Le tableau de cases du sudoku
	 */
	private Case[][] cases;
	
	/**
	 * Puissance du sudoku
	 */
	private final byte puissance;
	
	/**
	 * Lien vers les données de références de représentation en char des valeurs
	 */
	private final CharValeurs charValeurs;
	
	/**
	 * Taille de la grille (carré de la puissance)
	 */
	private final int length;
	
	public Grille() {
		this((byte) 3);
	}
	
	public Grille(byte puissance) {
		this(puissance, CharValeurs.DEFAULT_REPRESENTATION[puissance]);
	}
	
	public Grille(byte puissance, String representation) {
		this.puissance = puissance;
		length = puissance * puissance;
		if (representation.length() != length) throw new IllegalArgumentException();
		charValeurs = new CharValeurs(representation);
		cases = new Case[length][length];
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				Case c = new Case(puissance, "(" + i + "," + j + ")");
				cases[i][j] = c;
			}
		}
	}
	
	public Case getCase(int i, int j) {
		return cases[i][j];
	}

	/**
	 * @return Returns the charValeurs.
	 */
	public CharValeurs getCharValeurs() {
		return charValeurs;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				sb.append(charValeurs.toChar(cases[i][j].getValeur()));
			}
			if (i < length - 1) sb.append('\n');
		}
		return sb.toString();
	}

	/**
	 * @return Returns the length.
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @return Returns the puissance.
	 */
	public byte getPuissance() {
		return puissance;
	}
	
	public int getSolvedCount() {
		int count = 0;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				if (cases[i][j].isSolved()) count++;
			}
		}
		return count;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		Grille newGrille;
		try {
			newGrille = (Grille) super.clone();
		} catch (CloneNotSupportedException cnse) {
			throw new RuntimeException(cnse);
		}
		Case[][] newCases = new Case[length][length];
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				newCases[i][j] = (Case) cases[i][j].clone();
			}
		}
		newGrille.cases = newCases;
		return newGrille;
	}


}