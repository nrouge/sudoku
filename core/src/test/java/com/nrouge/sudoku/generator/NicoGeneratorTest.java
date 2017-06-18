package com.nrouge.sudoku.generator;

import junit.framework.TestCase;

import com.nrouge.sudoku.generator.impl.JavaGenerator;
import com.nrouge.sudoku.solver.impl.JavaSolver;

public class NicoGeneratorTest extends TestCase {

	/*
	 * Test method for 'com.nrouge.sudoku.generator.NicoGenerator.generate(byte, int)'
	 */
	public void testGenerate() {
		System.out.println(new JavaGenerator().generate((byte) 3, 0));
	}

}
