import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class BasicHero here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Hero extends Entity
{
    /**
     * Act - do whatever the BasicHero wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    //variables used in animation
    GreenfootImage[] idle = new GreenfootImage[4];  
    SimpleTimer animationTimer = new SimpleTimer();
    String action="Idle_";
    int imageIndex=0;
    int animationDelay = 100;
    
    //variables used in movement along the x axis
    int xVelocity=0;
    int xSpeed = 2;
    static int xDirection=0;
    static char xDirectionChar = 'r';
    static int x;
    //variables used in movement along the y axis
    int yVelocity=0;
    int ySpeed = 2;
    static int yDirection=0;
    static int y;
    
    //variables used in 
    SimpleTimer damageCooldownTimer = new SimpleTimer();
    
    public Hero()
    {
        animate(0);
    }
    public void animate(int animationDelay)
    {
        if(animationTimer.millisElapsed() < animationDelay)
        {
            return;
        }
        animationTimer.mark();
        imageIndex = (imageIndex + 1 )% idle.length;
        GreenfootImage currentImage = new GreenfootImage("images/mHero_/"+action+"/"+xDirectionChar+action+imageIndex+".png");
        currentImage.scale(72,72);
        setImage(currentImage);
    }
    public void act()
    {
        // basic movement
        MyWorld world = (MyWorld) getWorld();
        x=getX();
        y=getY();
        
        if(world.health>0){
            
            //If there is a wall to the left, you cannot move left
            if(xDirection<0&canMoveLeft()==false){
                xDirection=0;
            }
            //If there is a wall to the right, you cannot move right
            if(xDirection>0&canMoveRight()==false){
                xDirection=0;
            }
            //If there is a wall above, you cannot move up
            if(yDirection<0&canMoveUp()==false){
                yDirection=0;
            }
            //If there is a wall below, you cannot move down
            if(yDirection>0&canMoveDown()==false){
                yDirection=0;
            }
            
            setLocation(getX()+xVelocity,getY()+yVelocity);
            xVelocity=xDirection*xSpeed;
            yVelocity=yDirection*ySpeed;
            
            if((xVelocity==0)&(yVelocity==0)){
                //If the player stands still the Idle_ animation plays
                action="Idle_";
                animationDelay=125;
            }
            else{
                //If the player is walking the WalkRun_ animation plays
                action="WalkRun_";
                animationDelay=100;
            }
            
            //Basic movement
            if(Greenfoot.isKeyDown("right")){
                xDirection=1;
                xDirectionChar='r';
            }
            if(Greenfoot.isKeyDown("left")){
                xDirection=-1;
                xDirectionChar='l';
            }
            //If both left and right are pressed the player does not move horizontally
            if(Greenfoot.isKeyDown("right")==false&(Greenfoot.isKeyDown("left")==false)){
                xDirection=0;
            }
        
            //Basic movement
            if(Greenfoot.isKeyDown("down")){
                yDirection=1;
            }
            if(Greenfoot.isKeyDown("up")){
                yDirection=-1;
            }
            //If both up and down are pressed the player does not move vertically
            if(Greenfoot.isKeyDown("down")==false&(Greenfoot.isKeyDown("up")==false)){
                yDirection=0;
            }
            
            
            if(isTouchingSkeleton()||isTouchingGoblin()){
                action="Hurt_";         
                animationDelay=125;
                
            }
            //The following code executes when the player is Hurt and able to take damage
            if(action=="Hurt_"&damageCooldownTimer.millisElapsed()>500){
                imageIndex=1;
                animate(0);
                
                world.decreaseHealth();
                damageCooldownTimer.mark();
                knockSkeletons(100,4);
            }
            animate(animationDelay);
        }
        else{
            if(action!="Death_"){
                imageIndex=1;
            }
            action="Death_";
            
            
            
            if(imageIndex<3){
                animate(1000);
            }
        }
        /**
         * These two if statements change the levels
         */
        if(getX()>610){
            setLocation(-10,getY());
            world.levelUp();
        }
        if(getX()<-10){
            setLocation(610,getY());
            world.levelDown();
        }
        setLayers();
    }
    public void knockSkeletons(int range,int strength){
        for(Class c: new Class[]{Skeleton.class})
        {
            
            List<Skeleton> bs = getObjectsInRange(range,c);
            
            for(Skeleton b: bs)
            {
                if(b.canKill()){
                    b.knockback(strength);
                }
            }
        }
    }
    public boolean isTouchingSkeleton()
    {
        for(Class c: new Class[]{Skeleton.class})
        {
            
            List<Skeleton> bs = getIntersectingObjects(c);
            
            for(Skeleton b: bs)
            {
                if(b.canKill()){
                    if(b.getX()+30>=this.getX()&b.getX()-30<=this.getX()&b.getY()+60>=this.getY()&b.getY()-60<=this.getY()){
                        return true;
                    }
                }
            }
        }
        return false;
    }
     public boolean isTouchingGoblin()
    {
        for(Class c: new Class[]{Goblin.class})
        {
            
            List<Goblin> bs = getIntersectingObjects(c);
            
            for(Goblin b: bs)
            {
                if(b.canKill()){
                    if(b.getX()+30>=this.getX()&b.getX()-30<=this.getX()&b.getY()+60>=this.getY()&b.getY()-60<=this.getY()){
                        return true;
                    }
                }
            }
        }
        return false;
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
    
    //some static getter functions to communicate with other objects
    public static int getXPos()
    {
        return x;
    }
    public static int getYPos()
    {
        return y;
    }
    public static int getXDirection()
    {
        return xDirection;
    }
    public static char getXDirectionChar()
    {
        return xDirectionChar;
    }
    public static int getYDirection()
    {
        return yDirection;
    }
}
