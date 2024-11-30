import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Axe here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Axe extends BasicHero
{
    /**
     * Act - do whatever the Axe wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    GreenfootImage currentImage = new GreenfootImage("images/axe_.png");
    public void act()
    {
        super.act();
        setImage(currentImage);
        currentImage.scale(24,48);
        setLocation(super.getXPos(),super.getYPos());
        System.out.println(super.getXPos());
    }
}
