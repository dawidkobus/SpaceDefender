# SpaceDefender
SpaceDefender is a simple side-scrolling shooter developed in Java using Slick2D library.  
Initially, it was created as an assignment for object-oriented programming classes, since then a couple of bugs were fixed and functionality was expanded by arrow keys operated menu, secret code support and statistics menu scrolling.

## Credits
La la triroriro by Rolemusic is licensed under CC-BY 4.0.  
http://freemusicarchive.org/music/Rolemusic/  
Visitor Font by Brian Kent is licensed under CC BY-ND 4.0.  
http://www.fontriver.com/foundry/aenigma/  
All the other elements of the game were created by me using programs FamiTracker and GIMP.

## Features
-   difficulty increases with every second and resets after 45 seconds
-   infinite number of enemies spawning in random places
-   secret code giving extra 27 lives
-	statistics stored in .txt file

## Controls
-	arrow keys - move
-	spacebar - shoot
-	escape - pause
-	enter - select

## Video presentation
[drive.google.com](https://drive.google.com/open?id=0Bwel3KT1jXhHWk9RTHZkeUtjMFU)

## Problems and solutions

##### Disappearing projectiles
Projectiles of every ship are represented by arrays initialized in its constructor.
```
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
```
If enemy spaceship object is deleted, its fields containing projectiles are deleted too. After they're fired, they become separate entity in game world so deleting them after enemy spaceship is destroyed would be a mistake. I decided to teleport spaceship which is meant to be deleted outside left edge of the screen.
```
protected void destroy(){
    enemyPatternX = -WIDTH/10;
    allowPoints = true;
}
```
Spaceship object and its projectiles are deleted after spaceship crosses border in -(width of screen)/2 distance from left edge.
```
public void update(int delta){
    /* ... */
    if(position.getX() < -WIDTH/2){
        alive = false;
    }
    /* ... */
}
```
It may seem that projectiles fired before teleportation can outrun or hit spaceship which fired them which is only partially true. In fact, they can outrun spaceship, but they can't give him any damage because collision between enemy projectiles and enemy spaceships is not checked.

##### Jumping states
During testing I found problem with transition between states. Choosing PLAY from main menu with previously pressing key responsible for pause turned on the game with pause menu opened. To prevent such situations, before every state change record of previously pressed keys is erased.
```
if(gc.getInput().isKeyPressed(Input.KEY_ENTER)){
	switch(cursorPosition){
	case 0:
		gc.getInput().clearKeyPressedRecord();
		sbg.getState(GameEngine.PLAY_STATE).init(gc, sbg);
		sbg.enterState(GameEngine.PLAY_STATE);
		break;
	/* ... */
	}
}
```
