import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GoblinKing here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GoblinKing extends Skeleton
{
    /**
     * Act - do whatever the GoblinKing wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    SimpleTimer spawnMinionTimer=new SimpleTimer();
    boolean minionsDead=true;
    
    public GoblinKing(){
        xSpeed=2;
        size = 100;
        health=10;
        enemy="goblinKing_";
        personality="robotic";
    }
    
    public void act()
    {
        x=getX();
        y=getY();
        MyWorld world = (MyWorld) getWorld();
        
        if(spawnMinionTimer.millisElapsed()>5000&world.getObjects(Minion.class).size()<=0){
            world.addObject(new Minion(50,0),getX()+50,getY());
            world.addObject(new Minion(-50,0),getX()-50,getY());
            world.addObject(new Minion(0,50),getX(),getY()+50);
            world.addObject(new Minion(0,-50),getX(),getY()-50);
            world.addObject(new Minion(35,35),getX(),getY());
            world.addObject(new Minion(-35,-35),getX(),getY());
            world.addObject(new Minion(-35,35),getX(),getY());
            world.addObject(new Minion(35,-35),getX(),getY());
            
        }
        if(world.getObjects(Minion.class).size()<=0){
            spawnMinionTimer.mark();
        }
        
        if(knockbackStrength>5){
            knockbackStrength=3;
        }
        ySpeed=1;
        
        ai();
    }
    public static int getXPos ()
    {
        return x;
    }
    public static int getYPos ()
    {
        return y;
    }
}
