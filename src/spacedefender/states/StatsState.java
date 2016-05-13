package spacedefender.states;

import spacedefender.StatsEngine;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import spacedefender.GameEngine;
import spacedefender.util.Timer;
import static spacedefender.GameEngine.HEIGHT;
import static spacedefender.GameEngine.WIDTH;
import static spacedefender.states.MenuState.cursorUniFont;
import static spacedefender.states.MenuState.textUniFont;

public class StatsState extends BasicGameState{
    public static StatsEngine stats;
    private String[] statsArray;
    private final String[] text = {"TIME", "SHOOTING TIME", "NAME", "DATE", "SCORE"};
    
    private int currentLine;
    private int lineCount;
    private int maxLineCount;
    
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
        stats = new StatsEngine();
        stats.init();
        statsArray = stats.read();
        
        currentLine = 0;
        lineCount = 0;
        maxLineCount = 12;
        if(statsArray != null){
            for(String statsArray1 : statsArray){
                if(statsArray1 != null){
                    lineCount++;
                }
            }
            lineCount /= 5;
        }
    }
    
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
        MenuState.background.render();
        
        textUniFont.loadGlyphs();
        g.setFont(textUniFont);
        g.setColor(Color.white);
        
        for(int i = 0; i < text.length; i++){
            g.drawString(text[i], WIDTH/20 + WIDTH*i/5, HEIGHT/10);
        }
        
        for(int i = 0, xTable = 0, yTable = currentLine; i < statsArray.length; i++, xTable++){
            if(xTable > 4){
                xTable = 0;
                yTable++;
            }
            if(statsArray[i] != null){
                if(yTable >= 0 && yTable < maxLineCount){
                    g.drawString(statsArray[i], WIDTH/20 + WIDTH*xTable/5, HEIGHT*2/10 + HEIGHT/20*yTable);
                }
            }
        }
        g.drawString("PRESS ENTER TO CONTINUE TO MAIN MENU...", WIDTH/20, HEIGHT*9/10);
        
        if(lineCount > maxLineCount){
            cursorUniFont.loadGlyphs();
            g.setFont(cursorUniFont);
            g.drawString("▲", WIDTH*19/20, HEIGHT*3/20);
            g.drawString("▼", WIDTH*19/20, HEIGHT*16/20);
        }
    }
    
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
        MenuState.background.update();
        
        if(gc.getInput().isKeyPressed(Input.KEY_ENTER)){
            gc.getInput().clearKeyPressedRecord();
            sbg.enterState(GameEngine.MENU_STATE);
        }
        
        if(gc.getInput().isKeyDown(Input.KEY_UP) && Timer.timePassed(0.1f, delta)){
            if(lineCount > maxLineCount){
                if(currentLine < 0){
                    currentLine++;
                }
            }
        }
        
        if(gc.getInput().isKeyDown(Input.KEY_DOWN) && Timer.timePassed(0.1f, delta)){
            if(lineCount > maxLineCount){
                if(currentLine > (-stats.getStatsCapacity()/5 + maxLineCount) && (lineCount + currentLine) > maxLineCount){
                    currentLine--;
                }
            }
        }
    }
    
    public int getID(){
        return GameEngine.STATS_STATE;
    }
}
