package enamel;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class TestRobot {
	public Robot r;
	private static int delay = 500;

	public TestRobot() throws AWTException {
		r = new Robot();
	}

	public void pressKey(int key) {
		pressKey(key, 1);
	}

	public void pressKey(int key, int times) {
		for (int i = 0; i < times; i++) {
			r.delay(delay);
			r.keyPress(key);
			r.delay(delay);
			r.keyRelease(key);
		}
		r.delay(100);
	}

	public void enterText(String s) {
		s = s.toUpperCase();
		for (int i = 0; i < s.length(); i++) {
			pressKey(s.charAt(i));
		}
	}

	public void pressMouse() {
		pressMouse(KeyEvent.BUTTON1_MASK);
	}

	public void pressMouse(int key) {
		pressMouse(key, 1);
	}

	public void pressMouse(int key, int times) {
		for (int i = 0; i < times; i++) {
			r.delay(delay);
			r.mousePress(key);
			r.delay(delay);
			r.mouseRelease(key);
		}
		r.delay(delay);
	}

	public void moveMouse(int x, int y) {
		r.delay(delay);
		r.mouseMove(x, y);
		r.delay(delay);
	}

	public void moveMouse(Point p) {
		moveMouse(p.x, p.y);
	}

	public void moveMouse(Component comp) {
		moveMouse(comp.getLocationOnScreen().x + 5, comp.getLocationOnScreen().y + 5);
	}

	public void delay(int ms) {
		r.delay(ms);
	}
}
