package com.nrouge.sudoku.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.nrouge.sudoku.model.CharValeurs;
import com.nrouge.sudoku.model.Grille;

public final class SudokuFileUtils {
	private SudokuFileUtils() {
	}
	
	public static Grille importFromStream(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		try {
			String representation = br.readLine();
			if (representation == null) return null;
			int length = representation.length();
			byte puissance = (byte) Math.sqrt(length);
			if (puissance * puissance != length) return null;
			Grille g = new Grille(puissance, representation);
			CharValeurs cv = g.getCharValeurs();
			String s;
			int i = 0;
			while ((i < length) && (s = br.readLine()) != null) {
				if (s.length() != length) return null;
				for (int j = 0; j < length; j++) {
					g.getCase(i, j).setValeur(cv.toValeur(s.charAt(j)));
				}
				i++;
			}
			return g;
		} finally {
			try { br.close(); }
			catch (IOException ioe) { }
		}
	}
	
	public static Grille importResource(String resourceName) {
		InputStream is = null;
		try {
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
			return importFromStream(is);
		} catch (IOException ioe) {
			return new Grille();
		} finally {
			if (is != null) {
				try { is.close(); }
				catch (IOException ioe) { }
			}
		}
	}
}