import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class Entity here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Entity extends Actor
{
    /**
     * Act - do whatever the Entity wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }
    public void setLayers(){
        MyWorld world = (MyWorld) getWorld();
        for(Class c: new Class[]{Tile.class})
        {
            List<Tile> bs = getIntersectingObjects(c);
            for(Tile b :bs)
            {
                if(b.getY() < this.getY()+24)
                {
                    int x = getX(), y = getY();
                    world.removeObject(this);
                    world.addObject(this, x, y);   
                    
                }
            }
        }
    }
    /**
     * Tells you if you can move left, if you can move right, if you can move up or down
     * I don't want things walking through walls
     */
    public boolean canMoveLeft()
    {
        for(Class c: new Class[]{Tile.class})
        {
            List<Tile> bs = getIntersectingObjects(c);
            for(Tile b: bs)
            {
                if(b.getX() < this.getX()&b.getTileDirection().indexOf("R")!=-1){
                    if(b.getY()>=this.getY()-10&b.getY()<=this.getY()+46){
                        if(b.getX()>=this.getX()-42){
                            return false;
                        }
                    }
                }
                if(b.getX() < this.getX()&b.getTileDirection().indexOf("D")!=-1){
                    if(b.getY()>=this.getY()-3&b.getY()<=this.getY()+33){
                        if(b.getX()>=this.getX()-42){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    public boolean canMoveRight()
    {
        for(Class c: new Class[]{Tile.class})
        {
            
            List<Tile> bs = getIntersectingObjects(c);
            for(Tile b: bs)
            {
                if(b.getX() > this.getX()&b.getTileDirection().indexOf("L")!=-1){
                    if(b.getY()>=this.getY()-10&b.getY()<=this.getY()+46){
                        if(b.getX()<=this.getX()+42){
                            return false;
                        }
                    }
                }
                if(b.getX() > this.getX()&b.getTileDirection().indexOf("D")!=-1){
                    if(b.getY()>=this.getY()-3&b.getY()<=this.getY()+33){
                        if(b.getX()<=this.getX()+42){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    public boolean canMoveUp()
    {
        MyWorld world = (MyWorld) getWorld();
        for(Class c: new Class[]{Tile.class})
        {
            
            List<Tile> bs = getIntersectingObjects(c);
            
            for(Tile b: bs)
            {
                
                if(b.getY() < this.getY()&b.getTileDirection().indexOf("D")!=-1)
                    if(b.getX()<=this.getX()+30&b.getX()>=this.getX()-30){
                        if(b.getY()>=this.getY()-9){
                            return false;
                        }
                }
            }
        }
        return true;
    }
    public boolean canMoveDown()
    {
        for(Class c: new Class[]{Tile.class})
        {
            
            List<Tile> bs = getIntersectingObjects(c);
            
            for(Tile b: bs)
            {   
                
                if(b.getY() > this.getY()&b.getTileDirection().indexOf("U")!=-1){
                    if(b.getX()<=this.getX()+30&b.getX()>=this.getX()-30){
                        if(b.getY()<=this.getY()+40){
                            return false;
                        }
                    }
                }

                if(b.getY() > this.getY()&b.getTileDirection().indexOf("D")!=-1){
                    if(b.getX()<=this.getX()+30&b.getX()>=this.getX()-30){
                        if(b.getY()<=this.getY()+40){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
