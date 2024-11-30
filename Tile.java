import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class Wall here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tile extends Obstacle
{
    /**
     * The Tile class manages all the tiles
     * Each tile is different and interacts differently with the world
     * Their hitboxes can differ due to the sprites that I picked
     * I regret not choosing squares as my tiles, since they would have been much easier to use
     */
    
    
    public String tileName="";
    public int x;
    public int y;
    public int tileSize=40;
    /**
     * The Tile 
     * @param tileName The name of the Tile, which holds information such as its hitbox and image
     * @param x the x position before being snapped to the grid
     * @param y the y position before being snapped to the grid 
     */
    public Tile(String tileName,int x, int y){
        this.tileName=tileName;
        GreenfootImage image= new GreenfootImage("images/Tiles_/"+tileName+".png");
        setImage(image);
        image.scale(tileSize,tileSize);
        this.x=x; 
        this.y=y;
    }
    
    public void act()
    {
        //This code is what snaps the walls to the grid, it will also allow me to easily design levels
        MyWorld world = (MyWorld) getWorld();
        setLocation(x+tileSize/2-(x%tileSize),y+tileSize/2-(y%tileSize));
        setLayers();  
        
        if(getTileDirection().indexOf("U")!=-1){
            
            //This code puts the tile in the top layer, since I always want tiles facing up to be in the foreground
            int x = getX(), y = getY();
            world.removeObject(this);
            world.addObject(this, x, y);    
        }
        
        //This code only applies if the Tile is a door, which is the red lava blocks
        if(getTileType().equals("door")){
            
            //Removes the doors when the world tells it to
            if(world.doorsRemoved()){
                
                world.removeObject(this);
            }
        }
    }
    /**
     *This returns the direction of the Tile
     *for example if the tileName is wallD
     *it returns "D"
     *if the tileName is doorLR
     *it returns "LR"
     */
    public String getTileDirection()
    {
        String ans="";
        for(int i=this.tileName.length()-1 ; true ;i--){
            if(Character.isUpperCase(this.tileName.charAt(i))){
                ans+=this.tileName.charAt(i);
            }
            else{
                return ans;
            }
        }
    }
    /**
     *This returns the type of the Tile
     *for example if the tileName is wallD
     *it returns "wall"
     *if the tileName is doorLR
     *it returns "door"
     */
    public String getTileType()
    {
        String ans="";
        for(int i=0 ; true ;i++){
            if(Character.isLowerCase(this.tileName.charAt(i))){
                ans+=this.tileName.charAt(i);
            }
            else{
                return ans;
            }
        }
    }
    
    //If the Hero is lower down that the tile, the Hero will appear ontop and vice versa
    public void setLayers(){
        MyWorld world = (MyWorld) getWorld();
        for(Class c: new Class[]{Hero.class})
        {
            List<Hero> bs = getIntersectingObjects(c);
            for(Hero b :bs)
            {
                if(b.getY() +24< this.getY())
                {
                    
                    int x = getX(), y = getY();
                    world.removeObject(this);
                    world.addObject(this, x, y);        
                }
            }
        }
    }
}
