package com.nrouge.sudoku.generator;

import com.nrouge.sudoku.generator.impl.JavaGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class JavaGeneratorTest {

	/*
	 * Test method for 'com.nrouge.sudoku.generator.NicoGenerator.generate(byte, int)'
	 */
	@Test
	void generate() {
		log.info(new JavaGenerator().generate((byte) 3, 0).toString());
	}

}
