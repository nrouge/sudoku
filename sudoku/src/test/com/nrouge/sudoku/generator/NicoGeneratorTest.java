package com.nrouge.sudoku.generator;

import junit.framework.TestCase;

import com.nrouge.sudoku.generator.impl.NicoGenerator;
import com.nrouge.sudoku.solver.impl.Nico2Solver;

public class NicoGeneratorTest extends TestCase {

	/*
	 * Test method for 'com.nrouge.sudoku.generator.NicoGenerator.generate(byte, int)'
	 */
	public void testGenerate() {
		System.err.println(new NicoGenerator().generate((byte)4, 3, new Nico2Solver()));
	}

}
