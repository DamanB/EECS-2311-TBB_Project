package enamel;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class TestRobot {

	public static int DEFAULTDELAY = 100;
	public Robot r;

	public TestRobot() throws AWTException {
		r = new Robot();
	}

	public void pressKey(int key) {
		pressKey(key, 1);
	}

	public void pressKey(int key, int times) {
		for (int i = 0; i < times; i++) {
			delay();
			r.keyPress(key);
			delay();
			r.keyRelease(key);
		}
		delay();
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
			delay();
			r.mousePress(key);
			delay();
			r.mouseRelease(key);
		}
		delay();
	}

	public void moveMouse(int x, int y) {
		delay();
		r.mouseMove(x, y);
		delay();
	}

	public void moveMouse(Point p) {
		moveMouse(p.x, p.y);
	}

	public void moveMouse(Component comp) {
		moveMouse(comp.getLocationOnScreen().x + 5, comp.getLocationOnScreen().y + 5);
	}

	public void delay() {
		r.delay(DEFAULTDELAY);
	}

	public void delay(int ms) {
		r.delay(ms);
	}
}
