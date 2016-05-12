package spacedefender.objects;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;

public abstract class Spaceship extends GameObject{
    protected Image sprite = null;
    protected Vector2f position;
    
    protected int speed;
    protected int lives;
    
    protected Projectile[] projectilesUp;
    protected Projectile[] projectilesDown;
    
    protected int maxProjectiles = 16;
    
    protected int projectilesFireRate;
    protected int projectilesInterspace;
    protected int projectilesSpeed;
    protected int projectilesShipInterspace;
    
    protected int currentProjectile = 0;
    protected int timeSinceLastShot = 0;
    
    protected Sound hitSound;
    
    public Spaceship(Vector2f position){
        this.position = position;
        projectilesUp = new Projectile[maxProjectiles];
        for(int i = 0; i < projectilesUp.length; i++){
            projectilesUp[i] = new Projectile();
        }
        projectilesDown = new Projectile[maxProjectiles];
        for(int i = 0; i < projectilesDown.length; i++){
            projectilesDown[i] = new Projectile();
        }
    }
    
    public void init() throws SlickException{
        alive = true;
        damage = 1;
        hitSound = new Sound("res/soundeffects/hit.ogg");
    }
    
    public void render(Graphics g){
        sprite.draw(position.getX(), position.getY());
        
        for(Projectile p : projectilesUp){
            p.render(g);
        }
        for(Projectile p : projectilesDown){
            p.render(g);
        }
        
        //super.showHitboxes(g);
    }
    
    public void update(int delta){
        timeSinceLastShot += delta;
        for(Projectile p : projectilesUp){
            p.update(delta);
        }
        for(Projectile p : projectilesDown){
            p.update(delta);
        }
        sprite.setImageColor(255, 255, 255);
    }
    
    protected void fireProjectiles(Vector2f velocity, Projectile p){
        timeSinceLastShot = 0;
        projectilesUp[currentProjectile] = new Projectile(position.copy().add(new Vector2f(projectilesShipInterspace, 0)), velocity);
        projectilesDown[currentProjectile] = new Projectile(position.copy().add(new Vector2f(projectilesShipInterspace, projectilesInterspace)), velocity);
        currentProjectile++;
        if(currentProjectile >= projectilesUp.length || currentProjectile >= projectilesDown.length){
            currentProjectile = 0;
        }
    }
    
    public Projectile[] getProjectilesUp(){
        return projectilesUp;
    }
    
    public Projectile[] getProjectilesDown(){
        return projectilesDown;
    }
    
    protected Vector2f getPosition(){
        return position;
    }
    
    protected float getCenterX(){
        return position.copy().x + sprite.getCenterOfRotationX();
    }
    
    protected float getCenterY(){
        return position.copy().y + sprite.getCenterOfRotationY();
    }
    
    protected void setCenterPosition(Vector2f newPosition){
        position.x = newPosition.x - sprite.getCenterOfRotationX();
        position.y = newPosition.y - sprite.getCenterOfRotationY();
    }
    
    public void checkProjectileCollision(Projectile[] otherProjectiles){
        for(Projectile op : otherProjectiles){
            if(op.isAlive() && op.collidesWith(this)){
                op.destroy();
                sprite.setImageColor(255, 0, 0);
                lives -= op.getDamage();
                hitSound.play();
                if(lives < 1 && alive){
                    destroy();
                }
            }
        }
    }
    
    public void checkSpaceshipCollision(Spaceship otherSpaceship){
        if(otherSpaceship.collidesWith(this)){
            sprite.setImageColor(255, 0, 0);
            lives -= otherSpaceship.getDamage();
            if(lives < 1 && alive){
                destroy();
            }
        }
    }
    
    protected abstract void destroy();
    
    protected float getX1(){
        return position.getX() + sprite.getWidth()/10;
    }
    
    protected float getX2(){
        return position.getX() + sprite.getWidth()*9/10;
    }
    
    protected float getY1(){
        return position.getY() + sprite.getHeight()/10;
    }
    
    protected float getY2(){
        return position.getY() + sprite.getHeight()*9/10;
    }
}
