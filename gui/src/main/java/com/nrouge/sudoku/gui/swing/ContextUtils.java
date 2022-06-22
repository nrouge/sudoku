package com.nrouge.sudoku.gui.swing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 
 * @author Nicolas Rougé
 */
public final class ContextUtils {
	
	private ContextUtils() {
	}
	
	private static final File CONTEXT_FILE = new File(System.getProperty("user.home") + File.separator + ".sudoku" + File.separator + "context");
	
	
	public static void saveConfig(SwingGUIConfig config) {
		CONTEXT_FILE.getParentFile().mkdirs();
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(CONTEXT_FILE));
			oos.writeObject(config);
		} catch (IOException ioe) {
			System.err.println(ioe);
		} finally {
			if (oos != null) {
				try { oos.close(); }
				catch (IOException ioe) { }
			}
		}
	}
	
	public static SwingGUIConfig loadConfig() {
		if (!CONTEXT_FILE.exists()) return null;
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(CONTEXT_FILE));
			return (SwingGUIConfig) ois.readObject();
		} catch (IOException ioe) {
			System.err.println(ioe);
			return null;
		} catch (ClassNotFoundException cnfe) {
			System.err.println(cnfe);
			return null;
		} finally {
			if (ois != null) {
				try { ois.close(); }
				catch (IOException ioe) { }
			}
		}
	}

}
