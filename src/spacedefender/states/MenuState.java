package spacedefender.states;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;
import spacedefender.CheatEngine;
import spacedefender.GameEngine;
import spacedefender.ParallaxEngine;
import static spacedefender.GameEngine.HEIGHT;
import static spacedefender.GameEngine.WIDTH;

public class MenuState extends BasicGameState {
	public static ParallaxEngine background;
	public static CheatEngine cheatEngine;

	private Font textFont;
	private Font cursorFont;
	public static UnicodeFont textUniFont;
	public static UnicodeFont cursorUniFont;

	private int cursorPosition;
	private int cursorSpacing;

	private final String[] text = { "PLAY", "STATS", "CREDITS", "EXIT" };

	private char[] konamiCode = { Input.KEY_UP, Input.KEY_UP, Input.KEY_DOWN, Input.KEY_DOWN, Input.KEY_LEFT,
			Input.KEY_RIGHT, Input.KEY_LEFT, Input.KEY_RIGHT, Input.KEY_SPACE, Input.KEY_SPACE };

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		background = new ParallaxEngine();
		background.init();

		cheatEngine = new CheatEngine();
		cheatEngine.init();

		try {
			textFont = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/fonts/visitor.TTF"));
			cursorFont = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/fonts/arial.TTF"));
		} catch (FontFormatException | IOException ex) {
			System.out.println("FontFormatException | IOException ex");
		}

		textUniFont = new UnicodeFont(textFont, 18, false, false);
		cursorUniFont = new UnicodeFont(cursorFont, 14, false, false);

		cursorPosition = 0;
		cursorSpacing = 0;

		ColorEffect cf = new ColorEffect();
		textUniFont.getEffects().add(cf);
		cursorUniFont.getEffects().add(cf);
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		background.render();
		cheatEngine.render(g);

		textUniFont.loadGlyphs();
		cursorUniFont.loadGlyphs();

		g.setFont(textUniFont);
		g.setColor(Color.white);
		g.drawString(GameEngine.TITLE, WIDTH / 10, HEIGHT / 3);
		for (int i = 0; i < text.length; i++) {
			g.drawString(text[i], WIDTH / 10, HEIGHT / 2 + i * 20);
		}

		switch (cursorPosition) {
		case 0:
			cursorSpacing = 0;
			break;
		case 1:
			cursorSpacing = 20;
			break;
		case 2:
			cursorSpacing = 40;
			break;
		case 3:
			cursorSpacing = 60;
		}
		g.setFont(cursorUniFont);
		g.drawString("►", WIDTH / 10 - 20, HEIGHT / 2 + cursorSpacing);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		background.update();

		if (gc.getInput().isKeyPressed(Input.KEY_UP)) {
			if (cursorPosition > 0) {
				cursorPosition--;
			} else {
				cursorPosition = 3;
			}
		}

		if (gc.getInput().isKeyPressed(Input.KEY_DOWN)) {
			if (cursorPosition < 3) {
				cursorPosition++;
			} else {
				cursorPosition = 0;
			}
		}

		if (gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
			switch (cursorPosition) {
			case 0:
				gc.getInput().clearKeyPressedRecord();
				sbg.getState(GameEngine.PLAY_STATE).init(gc, sbg);
				sbg.enterState(GameEngine.PLAY_STATE);
				break;
			case 1:
				gc.getInput().clearKeyPressedRecord();
				sbg.getState(GameEngine.STATS_STATE).init(gc, sbg);
				sbg.enterState(GameEngine.STATS_STATE);
				break;
			case 2:
				gc.getInput().clearKeyPressedRecord();
				sbg.getState(GameEngine.CREDITS_STATE).init(gc, sbg);
				sbg.enterState(GameEngine.CREDITS_STATE);
				break;
			case 3:
				gc.exit();
			}
		}
	}

	public void keyPressed(int key, char c) {
		cheatEngine.checkCode(key, c, konamiCode);
	}

	public int getID() {
		return GameEngine.MENU_STATE;
	}
}
