package com.nrouge.sudoku.gui.swing;

import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.text.BreakIterator;

import javax.swing.SwingUtilities;

/**
 * 
 * @author Nicolas Rougé
 */
public final class SwingGUIUtils {
	
	// inspiré de http://david.fallingrock.net/2005/06/30/wrap-jlabel-text/
	public static String getWrappedLabelText(Container labelContainer, Font labelFont, String text) {
		FontMetrics fm = labelContainer.getFontMetrics(labelFont);
		int containerWidth = labelContainer.getWidth();

		BreakIterator boundary = BreakIterator.getCharacterInstance();
		boundary.setText(text);

		StringBuilder trial = new StringBuilder();
		StringBuilder real = new StringBuilder("<html>");

		int start = boundary.first();
		for (int end = boundary.next(); end != BreakIterator.DONE;
			start = end, end = boundary.next()) {
			String word = text.substring(start,end);
			trial.append(word);
			int trialWidth = SwingUtilities.computeStringWidth(fm,
				trial.toString());
			if (trialWidth > containerWidth) {
				trial = new StringBuilder(word);
				real.append("<br>");
			}
			real.append(word);
		}

		real.append("</html>");

		return real.toString();
	}
	

}
