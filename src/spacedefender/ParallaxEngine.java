package spacedefender;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import static spacedefender.GameEngine.HEIGHT;
import static spacedefender.GameEngine.WIDTH;

public class ParallaxEngine {
	private Image[] backgrounds;
	private Vector2f[] backgroundsScrollers;

	public void init() throws SlickException {
		backgrounds = new Image[6];
		backgrounds[0] = new Image("res/backgrounds/b0.png");
		backgrounds[1] = new Image("res/backgrounds/b1.png");
		backgrounds[2] = new Image("res/backgrounds/b2.png");
		backgrounds[3] = new Image("res/backgrounds/b3.png");
		backgrounds[4] = new Image("res/backgrounds/b4.png");
		backgrounds[5] = new Image("res/backgrounds/b5.png");

		backgroundsScrollers = new Vector2f[6];
		for (int i = 0; i < backgroundsScrollers.length; i++) {
			backgroundsScrollers[i] = new Vector2f(WIDTH, 0);
		}
	}

	public void render() {
		for (int i = 0; i < backgrounds.length; i++) {
			backgrounds[i].draw(backgroundsScrollers[i].getX(), 0, WIDTH, HEIGHT);
			backgrounds[i].draw(backgroundsScrollers[i].getY(), 0, WIDTH, HEIGHT);
		}
	}

	public void update() {
		for (int i = 0, j = 1; i < backgroundsScrollers.length; i++, j++) {
			backgroundsScrollers[i].x -= j;
			backgroundsScrollers[i].y -= j;

			if (backgroundsScrollers[i].getX() <= -WIDTH) {
				backgroundsScrollers[i].x = WIDTH;
			}

			if (backgroundsScrollers[i].getY() <= -WIDTH) {
				backgroundsScrollers[i].y = WIDTH;
			}
		}
	}
}
