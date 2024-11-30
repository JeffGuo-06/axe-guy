import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Minion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Minion extends GoblinKing
{
    /**
     * Act - do whatever the Minion wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    int xOffset;
    int yOffset;
    public Minion(int xOffset,int yOffset){
        health=1;
        enemy="Slime_";
        this.xOffset=xOffset;
        this.yOffset=yOffset;
        size=72;
    }
    public void act()
    {
        MyWorld world = (MyWorld) getWorld();
        
        
        setLocation(GoblinKing.getXPos()-xOffset,GoblinKing.getYPos()-yOffset);
        if(isTouching(AxeHitbox.class)&((AxeHitbox.isThrown()&AxeHitbox.isStuck()==false)||AxeHitbox.isAttacking())){
                takeDamage();
                moveStateTimer.mark();
                if(AxeHitbox.isAttacking()){
                    knockbackStrength=10;
                }
                
            }
        if(moveState=="hurt"){
            if(action!="Hurt_"){
                imageIndex=1;
            }
            action="Hurt_";
            animationDelay=125;
            if(damageCooldownTimer.millisElapsed()>501){
                moveState="attacking";
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
                world.spawnCoin(getX(),getY());
                world.removeObject(this);
            }
            
        }
        animate(animationDelay);
    }
}
