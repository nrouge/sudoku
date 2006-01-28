package com.nrouge.sudoku.gui.swing;

import java.util.Map;

import com.nrouge.sudoku.model.Case;
import com.nrouge.sudoku.solver.ICaseChangeListener;

/**
 * 
 * @author Nicolas Rougé
 */
public class SwingCaseChangeListener implements ICaseChangeListener {
	
	private final Map casePanelMap;
	private final long sleepTime;
	
	public SwingCaseChangeListener(Map casePanelMap, long sleepTime) {
		this.casePanelMap = casePanelMap;
		this.sleepTime = sleepTime;
	}

	/**
	 * @see com.nrouge.sudoku.solver.ICaseChangeListener#caseHasChanged(com.nrouge.sudoku.model.Case)
	 */
	public void caseHasChanged(Case c) {
		sleep();
		CasePanel cp = (CasePanel) casePanelMap.get(c);
		cp.repaint();
	}
	
	private void sleep() {
		if (sleepTime == 0) return;
		try { Thread.sleep(sleepTime); }
		catch (InterruptedException ie) { }
	}

}
