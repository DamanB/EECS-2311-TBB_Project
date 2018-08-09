package enamel;

import java.awt.event.KeyEvent;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SoundRecorderTest {
	static JSoundRecorder sr;
	static TestRobot t;

	@BeforeAll
	static void setUp() throws Exception {
		sr = new JSoundRecorder();
		t = new TestRobot();
	}

	@Test
	void testStart1() {
		t.moveMouse(sr.getRecord());
		t.delay();
		t.pressMouse();
		t.delay();
		t.pressKey(KeyEvent.VK_ENTER);
		t.delay(3000);
		t.pressMouse();
		t.delay();
	}

	@Test
	void testStart2() {
		t.moveMouse(sr.getRecord());
		t.delay();
		t.pressMouse();
		t.delay();
		t.pressKey(KeyEvent.VK_ENTER);
		t.delay(3000);
		t.pressMouse();
		t.delay();
	}

}
