import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class Skeleton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Goblin extends Enemy
{
    /**
     * The Goblin is an enemy, that performs a charged attack.
     * When the player enters its range, it waits a few seconds,
     * then charges towards the player quickly. Additionally it has
     * 2 health. 
     */
    int count=0;
    //variables used in animation
    GreenfootImage[] idle = new GreenfootImage[4];  
    SimpleTimer animationTimer = new SimpleTimer();
    String action="Idle_";
    int imageIndex=0;
    int animationDelay = 125;
    
    //variables used in movement along the x axis
    int xVelocity=0;
    int xSpeed = 1;
    int deltaX;
    static int xDirection=0;
    static char xDirectionChar = 'r';
    static int x;
    int xOffset;
    
    //variables used in movement along the y axis
    int yVelocity=0;
    int ySpeed = 1;
    int deltaY;
    int yDirection=0;
    static int y;
    int yOffset;
    
    //variables used in the ai
    String personality="robotic";
    String moveState="routing";
    String previousMoveState="attacking";
    int changeStateMillis;
    SimpleTimer moveStateTimer = new SimpleTimer();
    int targetX;
    int targetY;
    
    //variables used in combat
    SimpleTimer hurtPlayerTimer = new SimpleTimer();
    int knockbackStrength;
    SimpleTimer damageCooldownTimer = new SimpleTimer();
    int health=2;
    int chargeAngle;
    int randomDirection;
    boolean canDropCoin=true;
    public Goblin(){
        animate(0);
        MyWorld world = (MyWorld) getWorld();

        
        moveState="walking";
        health=2;
        randomDirection=(Greenfoot.getRandomNumber(2)*2)-1;
        
    }
    public void animate(int animationDelay)
    {
        if(animationTimer.millisElapsed() < animationDelay)
        {
            return;
        }
        animationTimer.mark();
        imageIndex = (imageIndex + 1 )% idle.length;
        GreenfootImage currentImage = new GreenfootImage("images/orc_/"+action+"/"+xDirectionChar+action+imageIndex+".png");
        currentImage.scale(72,72);
        setImage(currentImage);
    }
    
    public void act()
    {
        // Add your action code here.
        MyWorld world = (MyWorld) getWorld();
        x=getX();
        y=getY();
        
        if(moveState!="hurt"&moveState!="dead"){
            
                        
            if(getObjectsInRange(200,Hero.class).toString().equals("[]")==false&moveState=="walking"){
                
                    moveState="aiming";
                    moveStateTimer.mark();
                   
                action="Idle_";
                animationDelay=100;

                if(getObjectsInRange(170,Hero.class).toString().equals("[]")==false&moveState=="walking"){
                    action="WalkRun_";
                    animationDelay=100;
                    turnTowards(Hero.getXPos(),Hero.getYPos());
                    turn(180);
                    move(1);
                    setRotation(0);
                }
                
                
                
            }
            else{
                if(moveState=="walking"){
                    action="WalkRun_";
                    animationDelay=100;
                    
                    if(Hero.getXPos()>this.getX()){
                        setLocation(getX()+xSpeed,getY());
                    }
                    if(Hero.getXPos()<this.getX()){
                        setLocation(getX()-xSpeed,getY());
                    }
                    if(Hero.getYPos()>this.getY()){
                        setLocation(getX(),getY()+ySpeed);
                    }
                    if(Hero.getYPos()<this.getY()){
                        setLocation(getX(),getY()-ySpeed);
                    }
                    
                }
            }
            
            if(moveState=="aiming"){
                action="Idle_";
                if(moveStateTimer.millisElapsed()>Greenfoot.getRandomNumber(1000)){
                    turnTowards(Hero.getXPos(),Hero.getYPos());
                    turn(90);
                    smartMove(1*randomDirection);
                    setRotation(0);
                }
                
                if(moveStateTimer.millisElapsed()%4==0&animationDelay>40){
                    animationDelay-=3;
                }
                
                
                if(moveStateTimer.millisElapsed()>1500+Greenfoot.getRandomNumber(3000)){
                    moveState="attacking";
                    targetX=Hero.getXPos();
                    targetY=Hero.getYPos();
                    turnTowards(targetX,targetY);
                    chargeAngle=getRotation();
                    moveStateTimer.mark();
                }
                
            }
            if(moveState=="attacking"){
                action="WalkRun_";
                animationDelay=20;
                setRotation(chargeAngle);
                smartMove(5);
                setRotation(0);
                
            }
            
            if(isTouching(AxeHitbox.class)&((AxeHitbox.isThrown()&AxeHitbox.isStuck()==false)||AxeHitbox.isAttacking())){
                takeDamage();
                moveStateTimer.mark();
                if(AxeHitbox.isAttacking()){
                    knockbackStrength=10;
                }
            }
        }
        if(knockbackStrength>0){
            turnTowards(Hero.getXPos(),Hero.getYPos());
            turn(180);
            move(knockbackStrength);
            smartMove(knockbackStrength);
            

            knockbackStrength-=1;
            setRotation(0);
            
            }
            //System.out.println(moveState);
        //here is the animation for the hurt and death, I wanted the hurt animation to be faster therefore the delay is only 70
        if(moveState=="hurt"){
            if(action!="Hurt_"){
                imageIndex=1;
            }
            action="Hurt_";
            animationDelay=125;
            if(damageCooldownTimer.millisElapsed()>501){
                moveState="walking";
                action="Idle_";
            }
        }
        
        if(health<=0||moveState=="dead"){
            xVelocity=0;
            yVelocity=0;
            action="Death_";
            animationDelay=200;
            moveState="dead";
            if(moveStateTimer.millisElapsed()>animationDelay*4){
                if(canDropCoin){
                    world.spawnCoin(getX(),getY());
                }
                world.removeObject(this);
                canDropCoin=false;
            }
        }
        
        animate(animationDelay);
    }
   
    public void takeDamage(){
        if(damageCooldownTimer.millisElapsed()>1000){
        
            health--;
            moveState="hurt";
            imageIndex=1;
                action="Hurt_";
                animate(0);
                new GreenfootSound("sounds/Hit_/EnemyHurt_"+Greenfoot.getRandomNumber(1)+".mp3").play();
            damageCooldownTimer.mark();
        }
    }
    public void smartMove(int distance){
        if(0<=getRotation()&getRotation()<=90){
            if(canMoveDown()&canMoveRight()){
                move(distance);
            }
            else{
                moveState="dead";
            }
        }
        if(90<getRotation()&getRotation()<=180){
            if(canMoveDown()&canMoveLeft()){
                move(distance);
            }
            else{
                moveState="dead";
            }
        }
        if(180<getRotation()&getRotation()<=270){
            if(canMoveUp()&canMoveLeft()){
                move(distance);
            }
            else{
                moveState="dead";
            }
        }
        if(270<getRotation()&getRotation()<=360){
            if(canMoveUp()&canMoveRight()){
                move(distance);
            }
            else{
                moveState="dead";
            }
        }
    }
    public void knockback(int strength){
        knockbackStrength=strength;
    }
    
    public boolean canKill(){
        if(moveState!="hurt"&moveState!="dead"){
            return true;
        }
        return false;
    }
    //this resets the offset for even more randomness
    public void changeOffset(){
        xOffset=Greenfoot.getRandomNumber(100)-50;
        yOffset=Greenfoot.getRandomNumber(100)-50;
        xOffset=0;
        yOffset=0;
    }
   public boolean canMoveLeft()
    {
        for(Class c: new Class[]{Tile.class})
        {
            
            List<Tile> bs = getIntersectingObjects(c);
            
            for(Tile b: bs)
            {
                if(b.getX() < this.getX()&b.getTileDirection().indexOf("R")!=-1){
                    if(b.getX()>=this.getX()-33){
                        return false;
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
                if(b.getX() > this.getX()&b.getTileDirection().indexOf("L")!=-1)
                    if(b.getX()<=this.getX()+33){
                        return false;
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
                    if(b.getY()>=this.getY()-6){
                        return false;
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
                
                if(b.getY() > this.getY()&b.getTileDirection().indexOf("U")!=-1)
                    return false;
            }
        }
        return true;
    }
}
