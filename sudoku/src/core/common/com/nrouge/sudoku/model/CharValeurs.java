package com.nrouge.sudoku.model;

import java.io.Serializable;

public final class CharValeurs implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -9106036680946547297L;

	static final String[] DEFAULT_REPRESENTATION
		= {
			null,   //puissance 0 : non défini
			null,   //puissance 1 : non défini
			"1234", //puissance 2 : 4 valeurs possibles
			"123456789", //puissance 3 : classique, 9 valeurs possibles
			"0123456789ABCDEF", //puissance 4 : le sudoku avec 16 valeurs possibles (hexa)
			"ABCDEFGHIJKLMNOPQRSTUVWXY", //puissance 5 : 25 valeurs possibles => alphabet sauf le Z
			"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ", //puissance 6 : 36 valeurs possibles => les chiffres + l'alphabet
			"AaBbCcDdEeFfGgHhIiJjKkLlMmNoOoPpQqRrSsTtUuVvWwXxY", //puissance 7 : 49 valeurs possibles => les deux alphabets entrelacés maj/min jusqu'à Y
			"0123456789AaBbCcDdEeFfGgHhIiJjKkLlMmNoOoPpQqRrSsTtUuVvWwXxYyZz+*" //puissance 8 : 64 valeurs possibles => les chiffres, les deux alphabets maj/min, et le + et *
		};
	
	/**
	 * Représentation sous forme de char des valeurs
	 */
	private String representation;
	
	public CharValeurs(String representation) {
		this.representation = representation;
	}
	
	public char toChar(int valeur) {
		return (valeur == -1) ? '.' : representation.charAt(valeur);
	}
	
	public int toValeur(char c) {
		return representation.indexOf(c);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return representation;
	}

}
