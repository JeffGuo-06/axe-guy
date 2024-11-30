import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Title here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Title extends UI
{
    /**
     * Act - do whatever the Title wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    SimpleTimer bobTimer = new SimpleTimer();
    GreenfootImage image = new GreenfootImage("images/title_.png");
    int count=0;
    public Title(){
        setImage(image);
        getImage().scale(512,64);
        
    }
    public void act()
    {
        
        bob(500);
        
    }
    public void bob(int bobDelay)
    {
        if(bobTimer.millisElapsed() < bobDelay)
        {
            return;
        }
        count++;
        bobTimer.mark();
        if(count%2==0){
            setLocation(getX(),getY()+3);
        }
        else{
            setLocation(getX(),getY()-3);
        }
    }
}
