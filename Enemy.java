import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Enemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Enemy extends Entity
{
    /**
     * The Enemy class consists of anything from Skeletons to Goblins, to Goblin Kings
     * I put them all in a class so I can detect things like, whether or not the player has defeated 
     * all enemies in a room, or whether or not the player is touching an enemy.
     */
    
    //These variables are all consistent throughout all enemies
    int knockbackStrength;
    public static int x;
    public static int y;
   
    GreenfootImage[] idle = new GreenfootImage[4];  
    SimpleTimer animationTimer = new SimpleTimer();
    String action="Idle_";
    int imageIndex=0;
    int animationDelay = 100;
    char xDirectionChar='r';
    public Enemy(){
        
    }
    //Animation
    public void animate(int animationDelay,String enemy)
    {
        if(animationTimer.millisElapsed() < animationDelay)
        {
            return;
        }
        animationTimer.mark();
        imageIndex = (imageIndex + 1 )% idle.length;
        GreenfootImage currentImage = new GreenfootImage("images/"+enemy+"_/"+action+"/"+xDirectionChar+action+imageIndex+".png");
        currentImage.scale(72,72);
        setImage(currentImage);
    }
    
    
    public void act()
    {
        //For the getter methods
        x=getX();
        y=getY();
    }
    public void knockback(int strength){
        knockbackStrength=strength;
    }
    public static int getXPos(){
        return x;
    }
    public static int getYPos(){
        return y;
    }
}
