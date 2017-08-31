package com.nrouge.sudoku.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import com.nrouge.sudoku.model.CharValeurs;
import com.nrouge.sudoku.model.Grille;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public final class SudokuFileUtils {
	private SudokuFileUtils() {
	}
	
	public static Optional<Grille> importFromResource(Resource resource) {
		try (
				InputStream is = resource.getInputStream();
				InputStreamReader isr = new InputStreamReader(is, StandardCharsets.US_ASCII);
				BufferedReader br = new BufferedReader(isr)) {
			String representation = br.readLine();
			if (representation == null) {
				return Optional.empty();
			}
			int length = representation.length();
			byte puissance = (byte) Math.sqrt(length);
			if (puissance * puissance != length) {
				return Optional.empty();
			}
			Grille g = new Grille(puissance, representation);
			CharValeurs cv = g.getCharValeurs();
			String s;
			int i = 0;
			while ((i < length) && (s = br.readLine()) != null) {
				if (s.length() != length) {
					return Optional.empty();
				}
				for (int j = 0; j < length; j++) {
					g.getCase(i, j).setValeur(cv.toValeur(s.charAt(j)));
				}
				i++;
			}
			return Optional.of(g);
		} catch (IOException ioe) {
			throw new IllegalArgumentException("Unable to read from " + resource, ioe);
		}
	}

	public static Optional<Grille> importFromFile(File f) {
		return importFromResource(new FileSystemResource(f));
	}

}
