package spacedefender.states;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import spacedefender.GameEngine;
import spacedefender.objects.PlayerSpaceship;
import static spacedefender.GameEngine.HEIGHT;
import static spacedefender.GameEngine.WIDTH;
import static spacedefender.states.MenuState.textUniFont;

public class OverState extends BasicGameState{
    private static String name;
    private TextField textField;
    private String[] newStatsEntry;
    
    private Date date;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
        textField = new TextField(gc, textUniFont, WIDTH/10, HEIGHT*2/3, WIDTH*8/10, 20);
        newStatsEntry = new String[5];
    }
    
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
        MenuState.background.render();
        
        textUniFont.loadGlyphs();
        g.setFont(textUniFont);
        g.setColor(Color.white);
        
        g.drawString("GAME OVER", WIDTH/10, HEIGHT/3);
        g.drawString("SCORE: " + PlayState.getScore() + "\n"
                + "TIME: " + PlayState.getTime() + "\n"
                + "SHOOTING TIME: " + PlayerSpaceship.getShootingTime(), WIDTH/10, HEIGHT*4/10);
        
        g.drawString("ENTER YOUR NAME: ", WIDTH/10, HEIGHT*2/3 - 20);
        g.drawString("PRESS ENTER TO CONTINUE TO MAIN MENU...", WIDTH/20, HEIGHT*9/10);
        
        textField.render(gc, g);
    }
    
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
        MenuState.background.update();
        
        textField.setAcceptingInput(true);
        textField.setFocus(true);
        
        if(gc.getInput().isKeyPressed(Input.KEY_ENTER)){
            date = new Date();
            name = textField.getText();
            
            newStatsEntry[0] = "" + PlayState.getTime();
            newStatsEntry[1] = "" + PlayerSpaceship.getShootingTime();
            newStatsEntry[2] = "" + getName();
            newStatsEntry[3] = "" + dateFormat.format(date);
            newStatsEntry[4] = "" + PlayState.getScore();
            StatsState.stats.write(newStatsEntry);
            
            textField.setAcceptingInput(false);
            textField.setFocus(false);
            textField.setText("");
            
            gc.getInput().clearKeyPressedRecord();
            sbg.enterState(GameEngine.MENU_STATE);
        }
    }
    
    public int getID(){
        return GameEngine.OVER_STATE;
    }
    
    public static String getName(){
        return name;
    }
}
