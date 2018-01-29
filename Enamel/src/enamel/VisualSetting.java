package enamel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * the setting for VisualPlayer
 * 
 * @author guopy96
 *
 */
public class VisualSetting {

	/**
	 * option for VisualPlayer
	 * 
	 * @author guopy96
	 *
	 */
	final static class Step {
		public final static int FLAG = 0;
		public final static int CLEARCELL = 1;
		public final static int PINCELL = 2;
		public final static int DISPLAYTEXT = 3;
		public final static int AUDIOTEXT = 4;
		public final static int SOUND = 5;
		public final static int SKIPBUTTON = 6;
		public final static int SKIPKEY = 7;
		public final static int SKIP = 8;
		public final static int RESETBUTTON = 9;
		public final static int PAUSE = 10;
		public final static int USERINPUT = 11;

		private String text;

		// String flag;
		// Dictionary<Integer, boolean[]> cellPins;
		// String displayString;
		// String audioText;
		// String sound;
		// int pause;
		// String skip;
		// Dictionary<Integer,String> skipButton;

		private Step(String step) {
			this.text = step;
		}

		public final static Step createFlag(String flag) {
			return new Step("/~" + flag);
		}

		public final static Step clearCell(int cell) {
			return new Step("/~disp-cell-clear:" + cell);
		}

		public final static Step pinCell(int cell, boolean data[]) {
			String text = "/~disp-cell-pins:" + cell + " ";
			for (boolean d : data) {
				text += d ? "1" : "0";
			}
			return new Step(text);
		}

		public final static Step displayText(String text) {
			return new Step("/~disp-string:" + text);
		}

		public final static Step audioText(String text) {
			return new Step(text);
		}

		public final static Step sound(String soundName) {
			return new Step("/~sound:" + soundName);
		}

		public final static Step skipButton(int button, String flag) {
			return new Step("/~skip-button:" + button + " " + flag);
		}

		public final static Step skipKey(int key, String flag) {
			return new Step("/~skip-button:" + key + " " + flag);
		}

		public final static Step skip(String flag) {
			return new Step("/~skip:" + flag);
		}

		public final static Step resetButton() {
			return new Step("/~reset-buttons");
		}

		public final static Step pasue(int time) {
			return new Step("/~pause:" + time);
		}

		public final static Step userInput() {
			return new Step("/~user-input");
		}

		public String toString() {
			return this.text;
		}
	}

	private int cells;
	private int buttons;
	private List<Step> steps;

	public VisualSetting() {
		this.cells = 0;
		this.buttons = 0;
		this.steps = new ArrayList<Step>();
	}

	/**
	 * get cell number
	 * 
	 * @return cell number
	 */
	public int getCells() {
		return cells;
	}

	/**
	 * set cell number
	 * 
	 * @param cells
	 *            cell number
	 */
	public void setCells(int cells) {
		if (cells >= 0)
			this.cells = cells;
	}

	/**
	 * get button number
	 * 
	 * @return button number
	 */
	public int getButtons() {
		return buttons;
	}

	/**
	 * set button number
	 * 
	 * @param buttons
	 *            button number
	 */
	public void setButtons(int buttons) {
		if (this.buttons >= 0)
			this.buttons = buttons;
	}

	/**
	 * get action steps
	 * 
	 * @return a list of steps
	 */
	public List<Step> getSteps() {
		return steps;
	}

	/**
	 * set action steps
	 * 
	 * @param steps
	 *            a list of steps
	 */
	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	/**
	 * Create a new setting file with this setting, if the file exists, overwrite
	 * it;
	 * 
	 * @param fileName
	 *            output file name.
	 * @return true create and write setting successfully; false the filename is a
	 *         directory, exists but not writable or meet some other Exceptions.
	 */
	public boolean createSettingFile(String fileName) {
		return VisualSetting.createSettingFile(fileName, this.toString());
	}

	/**
	 * Create a new setting file with a given setting, if the file exists, overwrite
	 * it;
	 * 
	 * @param fileName
	 *            output file name.
	 * @param setting
	 *            output text.
	 * @return true create and write setting successfully; false the filename is a
	 *         directory, exists but not writable or meet some other Exceptions.
	 */
	public static boolean createSettingFile(String fileName, String setting) {
		FileWriter w = null;
		try {
			File f = new File(fileName);
			if (!f.exists())
				f.createNewFile();
			if (f.canWrite()) {
				w = new FileWriter(f);
				w.write(setting);
				w.flush();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		} finally {
			try {
				w.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String toString() {
		String text = "Cell " + cells + "\nButton " + buttons + "\n";
		for (Step s : steps) {
			text += s;
		}
		return text;
	}

}
