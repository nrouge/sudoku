package com.nrouge.sudoku.solver;

import com.nrouge.sudoku.model.Grille;
import com.nrouge.sudoku.solver.impl.JavaSolver;
import com.nrouge.sudoku.util.SudokuFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class JavaSolverTest {

	JavaSolver solver = new JavaSolver();

	Map<String, Grille> grilles;

	@Before
	public void init() {
		String basedir = getClass().getResource("/").getFile();
		File directory = new File(basedir, "com" + File.separator + "nrouge" + File.separator + "sudoku" + File.separator + "solver");
		assertThat(directory).exists().isDirectory();
		grilles = Stream.of(directory.listFiles((dir, name) -> name.endsWith(".sdk")))
				.collect(Collectors.toMap(File::getName, this::loadGrille));
	}

	private Grille loadGrille(File sdkFile) {
		Optional<Grille> grille = SudokuFileUtils.importFromFile(sdkFile);
		assertThat(grille.isPresent()).isTrue();
		return grille.get();
	}

	@Test
	public void solve() {
		grilles.entrySet().forEach(this::testSolve);
	}

	@Test
	public void performanceTest() {
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			grilles.values().forEach(this::trySolve);
		}
		log.info("Executed in {}ms", System.currentTimeMillis() - startTime);
	}

	private void testSolve(Map.Entry<String, Grille> entry) {
		log.info("Trying to solve {}", entry.getKey());
		testSolve(entry.getValue());
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

	private void trySolve(Grille grille) {
		try {
			solver.solve((Grille) grille.clone(), 8);
		} catch (SolverException se) {
		}
	}

}
