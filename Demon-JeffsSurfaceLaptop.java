import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Demon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Demon extends Enemy
{
    /**
     * Act - do whatever the Demon wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    String text= "";
    Label textLabel=new Label(text,30);
    SimpleTimer textTimer = new SimpleTimer();
    boolean spoken = false;
    boolean canTakeMoney=true;
    public void act()
    {
        action="Idle_";
        MyWorld world = (MyWorld) getWorld();
        animate(160,"demon");
        if(world.level>0){
            world.removeObject(this);
        }
        if(getObjectsInRange(100,Hero.class).toString().equals("[]")==false&spoken==false){
            spoken=true;
            world.addObject(textLabel,getX(),getY()-30);
            textTimer.mark();
            
        }
        if(spoken==true){
            speak();
        }
        if(getObjectsInRange(100,Hero.class).toString().equals("[]")==false&Greenfoot.isKeyDown("space")){
            if(world.coins<10&world.doorsRemoved()==false){
                textLabel.setValue("Money?");
            }
            else{
                world.decreaseCoins(10);
                world.doorsRemoved=true;
                textLabel.setValue("Cool");
                
            }
        }
        if(getObjectsInRange(150,Hero.class).toString().equals("[]")){
            textLabel.setValue("");
            spoken=false;
        }
        }
    public void speak(){
        MyWorld world = (MyWorld) getWorld();
        spoken=true;
        if(textTimer.millisElapsed()<1000){
            textLabel.setValue("Hey don't step in that lava");
        }
        else if(textTimer.millisElapsed()<2030){
            
            textLabel.setValue("Want to me to get rid of it? 10 coins [space]");
            canTakeMoney=true;
        }
       
        
        
        
    }
}
