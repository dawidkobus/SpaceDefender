package spacedefender.objects;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import static spacedefender.GameEngine.HEIGHT;
import static spacedefender.GameEngine.WIDTH;

public class EnemySpaceship extends Spaceship{
    private float enemyPatternX;
    private float enemyPatternY;
    private boolean patternLatch;
    private boolean allowPoints;
    
    public EnemySpaceship(Vector2f position){
        super(position);
    }
    
    public void init() throws SlickException{
        super.init();
        
        sprite = new Image("res/models/enemy1.png");
        speed = 2;
        lives = 2;
        projectilesFireRate = 500;
        projectilesInterspace = sprite.getHeight() - Projectile.getProjectileHeight();
        projectilesSpeed = 500;
        projectilesShipInterspace = -Projectile.getProjectileWidth();
        
        enemyPatternX = getCenterX();
        enemyPatternY = getCenterY();
        patternLatch = false;
        
        allowPoints = false;
    }
    
    public void render(Graphics g){
        super.render(g);
    }
    
    public void update(int delta){
        super.update(delta);
        if(position.getX() < -WIDTH/2){
            alive = false;
        }
        if(timeSinceLastShot > projectilesFireRate){
            fireProjectiles(new Vector2f (-projectilesSpeed, 0), new Projectile());
        }
        enemyPatternX -= speed;
        setCenterPosition(new Vector2f(enemyPatternX, enemyPatternY));
        if(patternLatch){
            enemyPatternY -= speed;
            if(enemyPatternY < HEIGHT/10){
                patternLatch = false;
            }
        }
        if(!patternLatch){
            enemyPatternY += speed;
            if(enemyPatternY > HEIGHT*9/10){
                patternLatch = true;
            }
        }
    }
    
    protected void destroy(){
        enemyPatternX = -WIDTH/10;
        allowPoints = true;
    }
    
    public boolean getAllowPoints(){
        return allowPoints;
    }
    
    public void setAllowPoints(boolean allowPoints){
        this.allowPoints = allowPoints;
    }
}
