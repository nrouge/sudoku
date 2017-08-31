package com.nrouge.sudoku.solver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

import com.nrouge.sudoku.model.Grille;
import com.nrouge.sudoku.solver.impl.JavaSolver;
import com.nrouge.sudoku.util.SudokuFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@Slf4j
public class JavaSolverTest {

	JavaSolver solver = new JavaSolver();

	@Test
	public void solve() {
		String basedir = getClass().getResource("/").getFile();
		File directory = new File(basedir, "com" + File.separator + "nrouge" + File.separator + "sudoku" + File.separator + "solver");
		assertThat(directory).exists().isDirectory();
		Arrays.stream(directory.listFiles((dir, name) -> name.endsWith(".sdk"))).forEach(this::testSolve);
	}

	private void testSolve(File sdkFile) {
		Optional<Grille> grille = SudokuFileUtils.importFromFile(sdkFile);
		assertThat(grille.isPresent()).isTrue();
		log.info("Trying to solve {}", sdkFile);
		testSolve(grille.get());
	}

	private void testSolve(Grille grille) {
		for (int level = 0; level < 10; level++) {
			boolean solved;
			try {
				solved = solver.solve((Grille) grille.clone(), level);
			} catch (UndeterminedSolutionException use) {
				log.info("{}:UNDETERMINED", level);
				continue;
			} catch (SolverException se) {
				log.info("{}:ERROR {}", level, se.getMessage());
				break;
			}
			if (solved) {
				log.info("{}:OK", level);
				break;
			} else {
				log.info("{}:NO", level);
			}
		}
	}

}
