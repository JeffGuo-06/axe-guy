import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Coin here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Coin extends Entity
{
    /**
     * Act - do whatever the Coin wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    GreenfootImage[] idle = new GreenfootImage[4];  
    SimpleTimer animationTimer = new SimpleTimer();
    SimpleTimer timer = new SimpleTimer();
    int imageIndex=0;
    boolean collected=false;
    int speed = 10;
    int idleTime;
    public Coin(){
        timer.mark();
        //randomises idleTime, so that coins do not wait for a constant amount of time, for variation
        idleTime=Greenfoot.getRandomNumber(2)*Greenfoot.getRandomNumber(12)*100+500;
        imageIndex=Greenfoot.getRandomNumber(3);
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
        GreenfootImage currentImage = new GreenfootImage("images/coin_/coin_"+imageIndex+".png");
        currentImage.scale(30,30);
        setImage(currentImage);
        
    }
    public void act()
    {            
        
        if(timer.millisElapsed()>idleTime){
            //Turns towards the CoinUI in the top right corner of the screen.
            turnTowards(580,30);
            
            move(speed);
            
        }
        if(isTouching(CoinUI.class)){
            //If the coin is touching CoinUI.class the speed gradually reduces to 1, allowing it to line up perfectly with its x and y positions
            
            if(speed>1){
                speed--;
            }
        }
        
        if(getX()==580&getY()==30){
            
            MyWorld world = (MyWorld) getWorld();
            
            //Plays a sound chosen randomly from three options
            new GreenfootSound("sounds/Coin_/Coin_"+Greenfoot.getRandomNumber(2)+".mp3").play();
            world.increaseCoins();
            world.removeObject(this);
            
        }
        animate(150);
    }
}
