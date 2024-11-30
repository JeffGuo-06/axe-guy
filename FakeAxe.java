import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class FakeAxe here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FakeAxe extends Entity
{
    /**
     * Act - do whatever the FakeAxe wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public FakeAxe(){
        getImage().mirrorHorizontally();
        getImage().scale(36,73);
        setRotation(-15);
    }
    public void act()
    {
        MyWorld world = (MyWorld) getWorld();
        if(world.level>0){
            world.removeObject(this);
        }
        
    }
}
