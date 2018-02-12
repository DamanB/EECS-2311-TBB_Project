package enamel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainMenuTest {

	MainMenu m;

	@BeforeEach
	void setUp() throws Exception {
		MainMenu.getScreen();
		m = MainMenu.instance;
	}

	@Test
	void testPlay() {
		m.getPlayer().doClick();
	}

	@Test
	void testEdit() {
		m.getEditor().doClick();
	}

}
