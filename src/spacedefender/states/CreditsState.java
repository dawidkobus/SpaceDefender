package spacedefender.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import spacedefender.GameEngine;
import static spacedefender.GameEngine.HEIGHT;
import static spacedefender.GameEngine.WIDTH;
import static spacedefender.states.MenuState.textUniFont;

public class CreditsState extends BasicGameState {
	private final String[] developerText = { "DEVELOPER:", "Space Defender was developed by Dawid Kobus.",
			"2016 All rights reserved." };
	private final String[] musicText = { "BACKGROUND MUSIC:",
			"La la triroriro by Rolemusic is licensed under CC-BY 4.0.",
			"http://freemusicarchive.org/music/Rolemusic/" };
	private final String[] fontText = { "FONT:", "Visitor Font by Brian Kent is licensed under CC BY-ND 4.0.",
			"http://www.fontriver.com/foundry/aenigma/" };

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		MenuState.background.render();

		textUniFont.loadGlyphs();
		g.setFont(textUniFont);
		g.setColor(Color.white);

		for (int i = 0; i < developerText.length; i++) {
			g.drawString(developerText[i], WIDTH / 10, HEIGHT * 2 / 10 + i * g.getFont().getLineHeight());
		}

		for (int i = 0; i < musicText.length; i++) {
			g.drawString(musicText[i], WIDTH / 10, HEIGHT * 4 / 10 + i * g.getFont().getLineHeight());
		}

		for (int i = 0; i < fontText.length; i++) {
			g.drawString(fontText[i], WIDTH / 10, HEIGHT * 6 / 10 + i * g.getFont().getLineHeight());
		}

		g.drawString("PRESS ENTER TO CONTINUE TO MAIN MENU...", WIDTH / 20, HEIGHT * 9 / 10);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		MenuState.background.update();
		if (gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
			gc.getInput().clearKeyPressedRecord();
			sbg.enterState(GameEngine.MENU_STATE);
		}
	}

	public int getID() {
		return GameEngine.CREDITS_STATE;
	}
}
