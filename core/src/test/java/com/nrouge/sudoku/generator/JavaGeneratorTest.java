package com.nrouge.sudoku.generator;

import com.nrouge.sudoku.generator.impl.JavaGenerator;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JavaGeneratorTest extends TestCase {

	/*
	 * Test method for 'com.nrouge.sudoku.generator.NicoGenerator.generate(byte, int)'
	 */
	public void testGenerate() {
		log.info(new JavaGenerator().generate((byte) 3, 0).toString());
	}

}
