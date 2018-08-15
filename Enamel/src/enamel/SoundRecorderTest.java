package enamel;

import static org.junit.Assert.assertTrue;

import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Paths;

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
		sr.setFile("audio");
		t.moveMouse(sr.getRecord());
		t.pressMouse();
		t.pressKey(KeyEvent.VK_ENTER);
		t.delay(3000);
		t.pressMouse();
		assertTrue(Files.exists(Paths.get("./audio.wav")));
	}

	@Test
	void testStart2() {
		sr.setFile("audio2");
		t.moveMouse(sr.getRecord());
		t.pressMouse();
		t.pressKey(KeyEvent.VK_ENTER);
		t.delay(3000);
		t.pressMouse();
		assertTrue(Files.exists(Paths.get("./audio2.wav")));
	}

}
