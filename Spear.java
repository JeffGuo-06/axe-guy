import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Spear here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spear extends Goblin
{
    /**
     * Act - do whatever the Spear wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    GreenfootImage spearImage = new GreenfootImage("images/spear_.png");
    public Spear(){
        setImage(spearImage);
        spearImage.scale(36,72);
    }
    
    public void act()
    {
        System.out.println(super.getXPos());
    
        setLocation(super.getXPos(),super.getYPos());
    }
}
