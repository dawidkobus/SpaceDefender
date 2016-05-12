package spacedefender;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class CheatEngine{
    private char[] keysPressed = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int keyPosition;
    private boolean codeEntered;
    private Image codeEnteredImage;
    
    public void init() throws SlickException{
        keyPosition = 0;
        codeEntered = false;
        codeEnteredImage = new Image("res/backgrounds/star.png");
    }
    
    public void render(Graphics g) throws SlickException{
        if(codeEntered){
            codeEnteredImage.draw(10, 10);
        }
    }
    
    public void checkCode(int key, char c, char[] code){
        keysPressed[keyPosition] = (char)key;
        
        if(keysPressed[keyPosition] == code[keyPosition]){
            keyPosition++;
        }
        else{
            keyPosition = 0;
        }
        
        if(keyPosition == keysPressed.length){
            keyPosition = 0;
            for(int i = 0; i < keysPressed.length; i++){
                if(keysPressed[keyPosition] == code[keyPosition]){
                    keyPosition++;
                }
                if(keyPosition == keysPressed.length){
                    keyPosition = 0;
                    codeEntered = true;
                }
            }
        }
    }
    
    public boolean getCodeEntered(){
        return codeEntered;
    }
    
    public void setCodeEntered(boolean codeEntered){
        this.codeEntered = codeEntered;
    }
}
