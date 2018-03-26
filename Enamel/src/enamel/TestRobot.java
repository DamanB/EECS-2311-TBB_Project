package enamel;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Point;
import java.awt.Robot;

public class TestRobot {
	public Robot r;

	public TestRobot() throws AWTException {
		r = new Robot();
	}

	public void pressKey(int key) {
		r.delay(100);
		r.keyPress(key);
		r.delay(100);
		r.keyRelease(key);
		r.delay(100);
	}

	public void pressMouse(int key) {
		r.delay(100);
		r.mousePress(key);
		r.delay(100);
		r.mouseRelease(key);
		r.delay(100);
	}

	public void moveMouse(int x, int y) {
		r.delay(100);
		r.mouseMove(x, y);
		r.delay(100);
	}

	public void moveMouse(Point p) {
		moveMouse(p.x, p.y);
	}

	public void moveMouse(Component comp) {
		int x = comp.getLocation().x;
		int y = comp.getLocation().y;
		while (comp.getParent() != null) {
			comp = comp.getParent();
			x += comp.getX();
			y += comp.getY();
		}
		moveMouse(x + 5, y + 5);
	}

	public void delay(int ms) {
		r.delay(ms);
	}
}
