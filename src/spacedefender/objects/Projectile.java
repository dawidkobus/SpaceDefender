package spacedefender.objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Projectile extends GameObject{
    private Vector2f position;
    private Vector2f velocity;
    
    private static int projectileWidth = 30;
    private static int projectileHeight = 3;
    
    private int lifetime = 3000;
    private int livesFor = 0;
    
    public Projectile(Vector2f position, Vector2f velocity){
        this.position = position;
        this.velocity = velocity;
        alive = true;
        damage = 1;
    }
    
    public Projectile(){
        alive = false;
    }
    
    public void render(Graphics g){
        if(alive){
            g.setColor(Color.red);
            g.fillRect(position.getX(), position.getY(), projectileWidth, projectileHeight);
            
            //super.showHitboxes(g);
        }
    }
    
    public void update(int delta){
        if(alive){
            Vector2f realSpeed = velocity.copy();
            realSpeed.scale(delta/1000.0f);
            position.add(realSpeed);
            
            livesFor += delta;
            if(livesFor > lifetime){
                alive = false;
            }
        }
    }
    
    protected void destroy(){
        alive = false;
    }
    
    protected float getX1(){
        return position.getX();
    }
    
    protected float getX2(){
        return position.getX() + projectileWidth;
    }
    
    protected float getY1(){
        return position.getY();
    }
    
    protected float getY2(){
        return position.getY() + projectileHeight;
    }
    
    public static int getProjectileWidth(){
        return projectileWidth;
    }
    
    public static int getProjectileHeight(){
        return projectileHeight;
    }
}
