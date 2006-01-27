package com.nrouge.sudoku.solver;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nrouge.sudoku.model.Grille;
import com.nrouge.sudoku.solver.impl.JavaSolver;
import com.nrouge.sudoku.util.SudokuFileUtils;

public class NicoSolverTest extends TestCase {
	
	private static final Log log = LogFactory.getLog(NicoSolverTest.class);
	
	private Grille getGrille(String sdkFile) throws IOException {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(sdkFile);
		log.warn("Chargement de " + sdkFile);
		Grille g = SudokuFileUtils.importFromStream(is);
		log.warn(g);
		return g;
	}
	
	public void testSolve1() throws IOException, SolverException {
		_test("gen34.sdk", 5);
	}
	
	private void _test(String sdkFile, int level) throws IOException, SolverException {
		Grille g = getGrille(sdkFile);
		JavaSolver ns = new JavaSolver();
		log.warn("Tentative de résolution");
		try {
			ns.solve(g, level);
		} finally {
			log.warn("Finie\n"+g);
		}
	}

}
