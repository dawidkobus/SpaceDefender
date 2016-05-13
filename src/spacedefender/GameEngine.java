package spacedefender;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import spacedefender.states.OverState;
import spacedefender.states.CreditsState;
import spacedefender.states.StatsState;
import spacedefender.states.PlayState;
import spacedefender.states.MenuState;

public class GameEngine extends StateBasedGame{
    public static final String TITLE = "SPACE DEFENDER";
    public static final String[] ICONS = {"res/icons/32x32.tga", "res/icons/24x24.tga", "res/icons/16x16.tga"};
    public static final int WIDTH = 960;
    public static final int HEIGHT = 540;
    public static final int FPS = 60;
    
    public static final int MENU_STATE = 0;
    public static final int PLAY_STATE = 1;
    public static final int STATS_STATE = 2;
    public static final int CREDITS_STATE = 3;
    public static final int OVER_STATE = 4;
    
    public static Music backgroundMusic;
    
    public GameEngine(String title){
        super(title);
    }
    
    public void initStatesList(GameContainer gc) throws SlickException{
        this.addState(new MenuState());
        this.addState(new PlayState());
        this.addState(new StatsState());
        this.addState(new CreditsState());
        this.addState(new OverState());
        
        backgroundMusic = new Music("res/music/music.ogg");
        backgroundMusic.loop(1.05f, 0.5f);
    }
    
    public static void main(String[] args) throws SlickException{
        AppGameContainer app = new AppGameContainer(new GameEngine(TITLE));
        app.setDisplayMode(WIDTH, HEIGHT, false);
        app.setIcons(ICONS);
        app.setTargetFrameRate(FPS);
        app.setShowFPS(false);
        app.setVSync(true);
        app.start();
    }
}
