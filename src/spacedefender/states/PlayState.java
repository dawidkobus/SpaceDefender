package spacedefender.states;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import spacedefender.GameEngine;
import spacedefender.objects.PlayerSpaceship;
import spacedefender.objects.EnemySpaceship;
import spacedefender.util.Timer;
import static spacedefender.GameEngine.HEIGHT;
import static spacedefender.GameEngine.WIDTH;
import static spacedefender.states.MenuState.textUniFont;

public class PlayState extends BasicGameState {
	private PlayerSpaceship player;
	private ArrayList<EnemySpaceship> enemies;
	private Random random;

	private static boolean paused;
	private static int score;
	private static int time;

	private static int enemySpawnChance;
	private static int enemySpawnPositionX;
	private static int enemySpawnPositionY;

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		player = new PlayerSpaceship(new Vector2f(WIDTH / 10, HEIGHT / 2));
		player.init();

		enemies = new ArrayList<>();
		random = new Random();

		paused = false;
		score = 0;
		time = 0;

		enemySpawnChance = 90;
		enemySpawnPositionX = 0;
		enemySpawnPositionY = 0;
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		MenuState.background.render();
		player.render(g);

		for (EnemySpaceship es : enemies) {
			es.render(g);
		}

		textUniFont.loadGlyphs();
		g.setFont(textUniFont);
		g.setColor(Color.white);

		g.drawString(" X " + player.getLives(), WIDTH / 20, HEIGHT / 30);
		g.drawString("SCORE: " + score, WIDTH * 5 / 20, HEIGHT / 30);
		g.drawString("TIME: " + time / 1000.f, WIDTH * 10 / 20, HEIGHT / 30);
		g.drawString("SHOOTING TIME: " + PlayerSpaceship.getShootingTime(), WIDTH * 15 / 20, HEIGHT / 30);

		if (paused) {
			g.setColor(new Color(0f, 0f, 0f, 0.5f));
			g.fillRect(0, 0, WIDTH, HEIGHT);

			g.setColor(Color.white);
			g.drawString("PRESS ESCAPE TO CONTINUE...", WIDTH / 20, HEIGHT * 9 / 10 - 20);
			g.drawString("PRESS ENTER TO CONTINUE TO MAIN MENU...", WIDTH / 20, HEIGHT * 9 / 10);
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (!paused) {
			MenuState.background.update();
			player.update(gc, delta);

			if (MenuState.cheatEngine.getCodeEntered()) {
				player.setLives(30);
				MenuState.cheatEngine.setCodeEntered(false);
			}

			Iterator<EnemySpaceship> i = enemies.iterator();
			while (i.hasNext()) {
				EnemySpaceship es = i.next();
				es.update(delta);

				if (es.getAllowPoints()) {
					score += 10;
					es.setAllowPoints(false);
				}

				if (!es.isAlive()) {
					i.remove();
				}

				player.checkProjectileCollision(es.getProjectilesUp());
				es.checkProjectileCollision(player.getProjectilesUp());

				player.checkProjectileCollision(es.getProjectilesDown());
				es.checkProjectileCollision(player.getProjectilesDown());

				player.checkSpaceshipCollision(es);
				es.checkSpaceshipCollision(player);
			}

			if ((time / 1000.f) > enemySpawnChance - 45) {
				enemySpawnChance += 45;
			}

			if (random.nextInt(enemySpawnChance - (int) (time / 1000.f)) == 0) {
				enemySpawnPositionX = random.nextInt(((WIDTH + WIDTH / 10) - WIDTH) + 1) + WIDTH;
				enemySpawnPositionY = random.nextInt(((HEIGHT - HEIGHT / 20) - HEIGHT / 20) + 1) + HEIGHT / 20;
				EnemySpaceship es = new EnemySpaceship(new Vector2f(enemySpawnPositionX, enemySpawnPositionY));
				es.init();
				enemies.add(es);
			}

			if (!player.isAlive()) {
				if (Timer.timePassed(1, delta)) {
					gc.getInput().clearKeyPressedRecord();
					sbg.enterState(GameEngine.OVER_STATE);
				}
			} else {
				time += delta;
			}

			if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
				gc.getInput().clearKeyPressedRecord();
				paused = true;
			}
		} else {
			GameEngine.backgroundMusic.setVolume(0.2f);
			if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
				GameEngine.backgroundMusic.setVolume(0.9f);
				paused = false;
			}
			if (gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
				GameEngine.backgroundMusic.setVolume(0.9f);
				gc.getInput().clearKeyPressedRecord();
				sbg.getState(GameEngine.MENU_STATE).init(gc, sbg);
				sbg.enterState(GameEngine.MENU_STATE);
			}
		}
	}

	public int getID() {
		return GameEngine.PLAY_STATE;
	}

	public static int getScore() {
		return score;
	}

	public static float getTime() {
		return time / 1000.f;
	}
}
