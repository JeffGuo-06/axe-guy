import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class Skeleton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Skeleton extends Enemy
{
    /**
     * The Skeleton is one of the weakest enemies,
     * it walks towards the player horizontally and vertically, based on its personality
     * it has one health.
     */
    int count=0;
    //variables used in animation
    GreenfootImage[] idle = new GreenfootImage[4];  
    SimpleTimer animationTimer = new SimpleTimer();
    String action="Idle_";
    int imageIndex=0;
    int animationDelay = 125;
    String enemy = "skeleton_";
    int size = 72;
    //variables used in movement along the x axis
    int xVelocity=0;
    int xSpeed = 2;
    int deltaX;
    static int xDirection=0;
    static char xDirectionChar = 'r';
    static int x;
    int xOffset;
    
    //variables used in movement along the y axis
    int yVelocity=0;
    int ySpeed = 2;
    int deltaY;
    int yDirection=0;
    static int y;
    int yOffset;
    
    //variables used in the ai
    String personality="robotic";
    String moveState="routing";
    String previousMoveState="attacking";
    int changeStateMillis;
    SimpleTimer moveStateTimer = new SimpleTimer();
    
    
    //variables used in combat
    SimpleTimer hurtPlayerTimer = new SimpleTimer();
    int knockbackStrength=0;
    SimpleTimer damageCooldownTimer = new SimpleTimer();
    int health=1;
    int coins=5;
    boolean canDropCoins=true;
    public Skeleton(){
        animate(0);
        //offset to make sure the skeletons don't clump up onto one pixel
        xOffset=Greenfoot.getRandomNumber(100)-50;
        yOffset=Greenfoot.getRandomNumber(100)-50;
        MyWorld world = (MyWorld) getWorld();
        //i gave each skeleton a "personality" to add some variation in their movements
        if(Greenfoot.getRandomNumber(2)==0){
            moveState="attacking";
        }
        if(Greenfoot.getRandomNumber(2)==0){
            personality="erratic";
        }
        if(Greenfoot.getRandomNumber(8)==0){
            personality="smart";
        }
        
        if(moveState=="dead"){
            world.removeObject(this);
        }
    }
    public void act()
    {
        // The AI function is called in other classes such as the GoblinKing class
        ai();
        
        
    }
    /**
     * The AI class, does a lot of things
     * It moves the skeleton, it makes the skeletons decisions
     * it deals with animation, it makes sure the skeleton can get hurt
     * it makes sure the skeleton can die, as well as take knockback.
     */
    public void ai(){
        MyWorld world = (MyWorld) getWorld();
        
        if(moveState!="hurt"&moveState!="dead"){
            animationDelay=200;
            //a skeleton with the personality "smart" will try to dodge the players axes
            
            if(moveState=="waiting"){
                xDirection=0;
                yDirection=0;
            }
            //i called moving in the x direction attacking, they always move towards the players x location while attacking
            if (moveState=="attacking"){

                deltaX = getX() - Hero.getXPos();
                yDirection=0;
                if(deltaX<0){
                    if(canMoveRight()){
                        setLocation(getX()+xSpeed,getY());
                        xDirection=1;
                        xDirectionChar='r';
                    }
                }
                if(deltaX>0){
                    if(canMoveLeft()){
                        setLocation(getX()-xSpeed,getY());
                        xDirection=(-1);
                        xDirectionChar='l';
                    }
                }
                if(Hero.getXPos()==getX()) {
                    moveState="routing";
                }
            }
            
            //I called moving in the y direction routing, they always move towards the players y location while routing
            if(moveState=="routing"){

                deltaY = getY() - Hero.getYPos();
                xDirection=0;

                if(deltaY>0){
                    if(canMoveUp()){
                        setLocation(getX(),getY()-ySpeed);
    
                        yDirection=-1;
                        
                    }
                }
                if(deltaY<0){
                    if(canMoveDown()){
                        setLocation(getX(),getY()+ySpeed);
                        
                        yDirection=1;
                    }
                }
                if(deltaY==0) {
                    moveState="attacking";
                }
            }
            
                //I added a waiting period to make the skeletons easier to handle, and to give the player some breathing room
            if(moveStateTimer.millisElapsed()>changeStateMillis&(moveState=="attacking"||moveState=="routing")){
                
                previousMoveState=moveState;
                moveState="waiting";
                moveStateTimer.mark();
                changeStateMillis=1000;
            }
            
            if(moveStateTimer.millisElapsed()>changeStateMillis&(moveState=="waiting")){
                //a skeleton with the personality "robotic" will always move in the x axis, then y, then x, then y, then x ...
                
                if(personality=="robotic"){
                    if(previousMoveState=="attacking"){
                        moveState="routing";
                    }
                    if(previousMoveState=="routing"){
                        moveState="attacking";
                    }
                }
                //a skeleton with the personality "erratic" chooses randomly between moving in the x and the y axis.
                if(personality=="erratic"||personality=="smart"){
                    if(Greenfoot.getRandomNumber(2)==0){
                        moveState="attacking";
                    }
                    else{
                        moveState="routing";
                    }
                }
                
                changeOffset();
                moveStateTimer.mark();
                changeStateMillis=1000;
            }
    
            //This code allows the Skeleton to takeDamage when its hit by a flying or swinging axe
            if(isTouching(AxeHitbox.class)&((AxeHitbox.isThrown()&AxeHitbox.isStuck()==false)||AxeHitbox.isAttacking())){
                takeDamage();
                moveStateTimer.mark();
                if(AxeHitbox.isAttacking()){
                    knockbackStrength=10;
                }
                
            }
            
        }
        
        if(knockbackStrength>0){
            //These lines of code allows the Skeleton to turn away from the player and move a set knockBackStrength away
            
            turnTowards(Hero.getXPos(),Hero.getYPos());
            turn(180);
            move(knockbackStrength);
            smartMove(knockbackStrength);
            
            //KnockbackStrength diminishes gradually
            knockbackStrength-=1;
            
            //Resets the rotation
            setRotation(0);
            
            }
       
        //Here is the animation for the hurt and death, I wanted the hurt animation to be faster therefore the delay is only 70
        if(moveState=="hurt"){
            if(action!="Hurt_"){
                imageIndex=1;
                
            }
            action="Hurt_";
            animationDelay=125;
            if(damageCooldownTimer.millisElapsed()>501){
                moveState="attacking";
                action="Idle_";
            }
        }
        
        //The following code executes when the skeleton dies
        if(health<=0||moveState=="dead"){
            xVelocity=0;
            yVelocity=0;
            action="Death_";
            animationDelay=200;
            moveState="dead";
            if(moveStateTimer.millisElapsed()>animationDelay*4){
                
        
                if(canDropCoins){
                    dropLoot(1);
                    
                }
                world.removeObject(this);
                canDropCoins=false;
            }
        }
        
        animate(animationDelay);
    }
    /**
     * This code is meant to nicely drop multiple coins on the ground in a line, when something dies
     * but I only ever drop one coin at a time, so it's kind of useless
     */
    public void dropLoot(int coins)
    {
        MyWorld world = (MyWorld) getWorld();
        for(int i = 0 ; i < coins ; i++){
            world.spawnCoin(getX()+i*10-(coins*5),getY()+(i%2)*5);
        }
        world.removeObject(this);
    }
    //Animation
    public void animate(int animationDelay)
    {
        if(animationTimer.millisElapsed() < animationDelay)
        {
            return;
        }
        animationTimer.mark();
        imageIndex = (imageIndex + 1 )% idle.length;
        GreenfootImage currentImage = new GreenfootImage("images/"+enemy+"/"+action+"/"+xDirectionChar+action+imageIndex+".png");
        currentImage.scale(size,size);
        setImage(currentImage);
    }
    
    
    public void takeDamage(){
        if(damageCooldownTimer.millisElapsed()>1000){
        
            health--;
            moveState="hurt";
            imageIndex=1;
                action="Hurt_";
            animate(0);
            new GreenfootSound("sounds/Hit_/EnemyHurt_"+Greenfoot.getRandomNumber(1)+".mp3").play();
            damageCooldownTimer.mark();
            

        }
    }
    
    /**
     * The method smartMove uses the canMove(Up/Down/Left/Right) methods
     * The idea is to prevent skeletons walking inside of walls
     * To eliminate the hassle I just made it so that, they die when they touch the wall
     */
    public void smartMove(int distance){
        if(0<getRotation()&getRotation()<=90){
            if(canMoveDown()&canMoveRight()){
                move(distance);
            }
            else{
                moveState="dead";
            }
        }
        if(90<getRotation()&getRotation()<=180){
            if(canMoveDown()&canMoveLeft()){
                move(distance);
            }
            else{
                moveState="dead";
            }
        }
        if(180<getRotation()&getRotation()<=270){
            if(canMoveUp()&canMoveLeft()){
                move(distance);
            }
            else{
                moveState="dead";
                
            }
        }
        if(270<getRotation()&getRotation()<=360){
            if(canMoveUp()&canMoveRight()){
                move(distance);
            }
            else{
                moveState="dead";
            }
        }
    }
    /**
     * Allows other classes to give the skeleton knockback
     */
    public void knockback(int strength){
        knockbackStrength=strength;
    }
    /**
     * This getter methods tells the player that the skeleton is dangerous,
     * I do not want the player to take damage from a dying skeleton
     */
    public boolean canKill(){
        if(moveState!="hurt"&moveState!="dead"){
            return true;
        }
        return false;
    }
    //this resets the offset for even more randomness
    public void changeOffset(){
        xOffset=Greenfoot.getRandomNumber(100)-50;
        yOffset=Greenfoot.getRandomNumber(100)-50;
        xOffset=0;
        yOffset=0;
    }
  
}
