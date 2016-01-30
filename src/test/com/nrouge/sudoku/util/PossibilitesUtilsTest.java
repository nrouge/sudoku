package com.nrouge.sudoku.util;

import junit.framework.TestCase;

public class PossibilitesUtilsTest extends TestCase {

	public void testGetPossibilitesMasques() {
		long pos = 1+2+4+8+16+32;
		System.out.println("pos="+pos+":"+PossibilitesUtils.toBinaryString(pos, 6)); 
		long[] t = PossibilitesUtils.getPossibilitesMasques(pos, 5);
		assertNotNull(t);
		for (int i = 0; i < t.length; i++) System.out.println(PossibilitesUtils.toBinaryString(t[i], 6));
	}
}
