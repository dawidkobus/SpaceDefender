package spacedefender.objects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;
import static spacedefender.GameEngine.HEIGHT;
import static spacedefender.GameEngine.WIDTH;

public class PlayerSpaceship extends Spaceship{
    private Image spriteSmall = null;
    
    private static int shootingTime;
    
    private Sound shootSound;
    private Sound crashSound;
    
    public PlayerSpaceship(Vector2f position){
        super(position);
    }
    
    public void init() throws SlickException{
        super.init();
        
        sprite = new Image("res/models/player1.png");
        spriteSmall = sprite.getScaledCopy(0.35f);
        spriteSmall.setRotation(-90);
        speed = 8;
        lives = 3;
        projectilesFireRate = 125;
        projectilesInterspace = sprite.getHeight() - Projectile.getProjectileHeight();
        projectilesSpeed = 1000;
        projectilesShipInterspace = sprite.getWidth();
        
        position.y -= sprite.getCenterOfRotationY();
        
        shootingTime = 0;
        
        shootSound = new Sound("res/soundeffects/pew.ogg");
        crashSound = new Sound("res/soundeffects/bwhaz.ogg");
    }
    
    public void render(Graphics g){
        super.render(g);
        spriteSmall.draw(WIDTH/20 - 20, HEIGHT/30);
    }
    
    public void update(GameContainer gc, int delta){
        if(alive){
            super.update(delta);

            if(gc.getInput().isKeyDown(Input.KEY_SPACE)){
                shootingTime += delta;
                if(timeSinceLastShot > projectilesFireRate){
                    fireProjectiles(new Vector2f(projectilesSpeed, 0), new Projectile());
                    shootSound.play();
                }
            }
            if((gc.getInput().isKeyDown(Input.KEY_UP) && position.getY() > 10)){
                position.y -= speed;
            }
            if((gc.getInput().isKeyDown(Input.KEY_DOWN) && position.getY() < gc.getHeight() - sprite.getHeight() - 10)){
                position.y += speed;
            }
            if((gc.getInput().isKeyDown(Input.KEY_LEFT)) && position.getX() > 10){
                position.x -= speed;
            }
            if((gc.getInput().isKeyDown(Input.KEY_RIGHT) && position.getX() < gc.getWidth() - sprite.getWidth() - 10)){
                position.x += speed;
            }
        }
    }
    
    protected void destroy(){
        alive = false;
        crashSound.play();
        position.y = -sprite.getHeight();
    }
    
    public static float getShootingTime(){
        return shootingTime/1000.0f;
    }
    
    public int getLives(){
        return lives;
    }
    
    public void setLives(int lives){
        if(lives > 0){
            this.lives = lives;
        }
    }
}
