package com.nrouge.sudoku.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PossibilitesUtilsTest {

	@Test
	void getPossibilitesMasques() {
		long pos = 1 + 2 + 4 + 8 + 16 + 32;
		System.out.println("pos=" + pos + ":" + Long.toBinaryString(pos));
		long[] t = PossibilitesUtils.getPossibilitesMasques(pos, 5);
		assertThat(t).isNotNull();
		for (long l : t) System.out.println(Long.toBinaryString(l));
	}
}
