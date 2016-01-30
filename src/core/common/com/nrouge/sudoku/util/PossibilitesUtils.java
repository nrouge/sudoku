package com.nrouge.sudoku.util;


public final class PossibilitesUtils {

	private PossibilitesUtils() {
	}
	
	/**
	 * @param pos masque des possiblités
	 * @return la valeur correspondante à la seule possibilité, -1 sinon
	 */
	public static int getValeur(long pos) {
		if (pos == 0) return -1;
		int valeur = 0;
		while ((pos % 2) == 0) {
			valeur++;
			pos >>= 1;
		}
		return (pos == 1) ? valeur : -1;
	}
	
	/**
	 * Convertit un masque de possibilités en tableau de valeurs possibles
	 * @param pos
	 * @return
	 */
	public static int[] getValeursPossibles(long pos) {
		final int nbPos = getNbPossibilites(pos);
		int[] res = new int[nbPos];
		long p = pos;
		int i = 0;
		int v = 0;
		while (p != 0) {
			if (p % 2 == 1) res[i++] = v;
			p >>= 1;
			v++;
		}
		return res;
	}
	
	/**
	 * @param pos
	 * @return
	 */
	public static int getNbPossibilites(long pos) {
		long p = pos;
		int nb = 0;
		while (p != 0) {
			if (p % 2 == 1) nb++;
			p >>= 1;
		}
		return nb;
	}
	
	/**
	 * Renvoie la liste des masques des possiblités à n valeurs
	 * @param valeurs
	 * @param n
	 * @return
	 */
	public static long[] getPossibilitesMasques(long pos, int n) {
		int[] valeurs = getValeursPossibles(pos);
		final int nb = valeurs.length;
		//calcul de la taille du tableau de résultat
		final int[] compteur = new int[n]; //tableau des indices des valeurs que l'on choisit
		for (int i = 0; i < n; i++) compteur[i] = i; //initialisation compteur
		int nbRes = 1;
		while (incrementeCompteur(compteur, nb)) nbRes++;
		//création tableau de résultat
		final long[] res = new long[nbRes];
		for (int i = 0; i < n; i++) compteur[i] = i; //initialisation compteur
		int c = 0;
		while (c < nbRes) {
			long p = 0;
			for (int i = 0; i < n; i++) p |= 1 << valeurs[compteur[i]]; // calcul de la possibilité à n valeurs représentée par compteur
			res[c++] = p;
			incrementeCompteur(compteur, nb);
		}
		return res;
	}
	
	public static boolean incrementeCompteur(int[] compteur, int nb) {
		final int length = compteur.length;
		return incrementeCompteur(compteur, nb, length, length - 1);
	}
	
	private static boolean incrementeCompteur(int[] compteur, int nb, int n, int i) {
		if (compteur[i] == nb - n + i) {
			if (i == 0) return false;
			if (!incrementeCompteur(compteur, nb, n, i - 1)) return false;
			compteur[i] = compteur[i - 1] + 1;
		} else {
			compteur[i]++;
		}
		return true;
	}
	
	public static String toBinaryString(long pos, int length) {
		StringBuffer sb = new StringBuffer(length);
		for (int v = length - 1; v >= 0; v--) {
			long vMask = 1 << v;
			sb.append(((pos & vMask) == vMask) ? '1' : '0');
		}
		return sb.toString();
	}

}
