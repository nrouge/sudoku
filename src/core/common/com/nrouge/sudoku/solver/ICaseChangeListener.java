package com.nrouge.sudoku.solver;

import com.nrouge.sudoku.model.Case;

/**
 * 
 * @author Nicolas Roug�
 */
public interface ICaseChangeListener {
	void caseHasChanged(Case c);
}
