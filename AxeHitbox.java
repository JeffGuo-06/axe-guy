  import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CopyOfAxe here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AxeHitbox extends Entity
{
    /**
     * The AxeHitbox is the Hitbox behind the Axe. 
     * The AxeHitbox does all the heavy lifting while the Axe just has to look pretty.
     * I added an AxeHitbox because the Axe is a terrible shape for a thrown projectile, 
     * it would constantly get caught on walls and be frustating to maneuver
     */
    
    //All images
    GreenfootImage imageRight = new GreenfootImage("images/axe_.png");
    GreenfootImage imageLeft = new GreenfootImage("images/axe_.png");
    GreenfootImage attackImageRight = new GreenfootImage("images/swingingAxe_.png");
    GreenfootImage attackImageLeft = new GreenfootImage("images/swingingAxe_.png");
    GreenfootImage hitbox = new GreenfootImage("images/axeHitbox_.png");
    GreenfootImage swingHitbox = new GreenfootImage("images/axeSwingingHitbox_test1.png");
    GreenfootImage swingingImageRight = new GreenfootImage("images/swingAxe_.png");
    GreenfootImage swingingImageLeft = new GreenfootImage("images/swingAxe_.png");
    
    //All sounds
    GreenfootSound axeStuck = new GreenfootSound("sounds/AxeStuck.mp3");
    GreenfootSound swingSound = new GreenfootSound("sounds/swing.mp3");    
    //GreenfootSound swooshSound = new GreenfootSound("sounds/swoosh (1).mp3");    
    //This count variable is a replacement for SimpleTimer, I used it a ton during testing, since SimpleTimer was often finicky
    int count;
    
    public static int weaponDirection=-1; 
    int weaponDirectionY=1;
    public static boolean thrown=true;
    public static boolean attack=false;
    public static boolean stuck=true;
    boolean catchable=false;
    SimpleTimer attackTimer = new SimpleTimer();
    int thrownDirection;
    int speed = 5; //Speed when Axe is thrown
    int attackCooldown=0;
    int rotationOffset; //Used in the melee attack to aim it up or down
    public static int attackingWeaponDirection=0; 
    public static int x;//x position
    public static int y;//y position
    public static int rotation;//current rotation
    SimpleTimer thrownTimer = new SimpleTimer();
    public AxeHitbox()
    {
        //Initial values of the variables
        thrown=false;           
        stuck=false;
        weaponDirection=-1;
        setImage(hitbox);   
        swingHitbox.scale(24,48);
        swingSound.setVolume(50);
        followHero();
    }
    public void act()
    {
        //These three lines below, tell the Axe where it has to be, and its rotation
        x=getX();
        y=getY();
        rotation=getRotation();
        
        //A replacement for SimpleTimer, used alot during troubleshooting
        count++;
        
        //Lowers the attackCooldown every act
        attackCooldown-=1;
        
        
        MyWorld world = (MyWorld) getWorld();
        
        
        //When "Q" is pressed the thrown variable is set to true, which starts the ranged attack
        if(Greenfoot.isKeyDown("q")){
            thrown=true;
            thrownTimer.mark();
            thrownDirection=weaponDirection;
        }
        
        if(thrown==true){
            
            //Plays the swoosh sound
            if(stuck==false){
            //swooshSound.playLoop();
            }
            //If the axe is thrown the player cannot melee attack therefore attack must be false.
            attack=false;
            //This hitbox is a small square rather than the rectangle used for the melee attack
            setImage(hitbox);
            //If the axe travels 60 pixels away from the player, it is then catchable.
            //This is to prevent the axe being immediately caught, before the Axe can even leave the players catch radius
            if(Math.abs(this.getX()-Hero.getXPos())>60){
                catchable=true;
            }
            
            
            if(catchable){
                if(isTouching(Hero.class)){
                    //swooshSound.stop();
                    //If the Axe is catchable and touching the Hero class, it must not be stuck, it must not be thrown, and it must not be catchable
                    stuck=false;
                    thrown=false;
                    catchable=false;
                    //Puts the axe in the player's hand
                    setLocation(Hero.getXPos()+weaponDirection*40,Hero.getYPos());
                    //Resets the rotation since the Axe can be at any rotation when it is caught
                    setRotation(0);
                }
            }
            
            //The following code executes when the Axe comes into contact with an Obstacle, such as a wall.
            if((isTouching(Obstacle.class))){
                //This if statement is to ensure that the axeStuck sound only plays one time.
                if(stuck==false){
                    axeStuck.play();
                    //swooshSound.stop();
                }
                //The Axe will get stuck if it touches a wall
                stuck=true;
                //The following if else statement is to give the Axe a semi realistic rotation. 
                //The goal is to make sure the Axe head is stuck into the wall.
                if(getX()>Hero.getXPos()){
                    setRotation(20);
                }
                else{
                    setRotation(-20);
                }
                
                //This if statement allows the Axe to be picked up after it is stuck in a wall
                if(isTouching(Hero.class)&thrownTimer.millisElapsed()>100){
                    stuck=false;
                    thrown=false;
                    catchable=false;
                    followHero();
                    setRotation(0);
                }
                
            }
                //This else statement is what happens when the Axe is thrown, but it is not stuck to a wall
            else{
                
                //I'm not sure why I added this if statement, but I'm too scared to touch it right now.
                if(isTouching(Obstacle.class)==false){
                    
                   //The following lines of code control the movement of the Axe while it's being thrown.
                   //If the player moves right, the Axe's direction switches to right.
                   if(Greenfoot.isKeyDown("right")||weaponDirection==1){
                        weaponDirection=1;
                        weaponDirectionY=0;
                    }
                    
                   //If the player moves left, the Axe's direction switches to left.
                   if(Greenfoot.isKeyDown("left")||weaponDirection==-1){
                        weaponDirection=-1;
                        weaponDirectionY=0;
                    } 
                    
                    //If the player moves up or down, the Axe's direction switches to up or down.
                    //If the Axe leaves the screen, its ability to move vertically is disabled.
                    //This is to try to prevent the Axe from being lost when thrown off screen
                   if(isAtEdge()==false){
                       if(Greenfoot.isKeyDown("up")){
                            weaponDirectionY=-1;
                            weaponDirection=0;
                        }
                       if(Greenfoot.isKeyDown("down")){
                            weaponDirectionY=1;
                            weaponDirection=0;
                        }
                    }
                    
                    //Spins the Axe in the correct direction when being thrown
                   if(weaponDirection!=0){
                       setRotation(getRotation()+20*(weaponDirection)); 
                   }
                   else{
                       setRotation(getRotation()-20*(weaponDirectionY)); 
                   }
                   
                   //Puts the Axe in the correct location
                   setLocation(getX()+speed*weaponDirection,getY()+speed*weaponDirectionY);
                }
            }
        }
        
        //The following code executes if the Axe is in the player's hands
        if(stuck==false&thrown==false){
            allignDirectionWithHero();
        }   
        
        
        if(thrown==false){
            thrownTimer.mark();
            if(attackCooldown>0){
                setImage(hitbox);
            }
            
            //The following code excecutes if the space key is pressed, if attack is equal to false 
            //(to make sure the code executes upon once), and if there is no more cooldown.
            if(Greenfoot.isKeyDown("space")&attack==false&attackCooldown<=0){
                swingSound.play();
                //Marks the start of the attack
                attackTimer.mark();
                
                //Puts the attack on a 50 tick cooldown (Everytime public void act() is called, the cooldown decreases by 1)
                attackCooldown=50;
                
                //Initiates the attack sequence
                attack=true;
                
                //This line sets AttackingWeaponDirection to the initial direction of the attack
                //ensuring that the Axe does not change directions mid-animation
                attackingWeaponDirection=weaponDirection;
                
                //rotationOffset is set to 0 by default
                rotationOffset=0;
                
                //If either the up or down key is pressed the rotation offset makes the Axe swing up or down respectively
                if(Greenfoot.isKeyDown("up")&Greenfoot.isKeyDown("down")==false){
                    rotationOffset=-45*attackingWeaponDirection;
                }
                if(Greenfoot.isKeyDown("down")&Greenfoot.isKeyDown("up")==false){
                    rotationOffset=45*attackingWeaponDirection;
                }
                
                //If neither up or down is pressed, or if both are pressed, rotationOffset will stay at 0
            }
            
            //The following if statement executes when the player is performing the melee attack
            if(attack==true){
                
                //Makes sure were using the correct hitbox
                setImage(swingHitbox);
                
                //The attack lasts for 200 milliseconds
                if(attackTimer.millisElapsed()<200){
                    
                    //The Axe returns to a set location every act.
                    //This location is in front of the player.
                    //It is the center of the circle that is the swing animation
                    setLocation(Hero.getXPos()-20*attackingWeaponDirection,Hero.getYPos()+30);
                    
                    //The Axe is rotated anticipating the move method.
                    //This gives the illusion that its moving in a circle.
                    setRotation(((attackTimer.millisElapsed()*3/10)-50)*attackingWeaponDirection+rotationOffset);
                    
                    //This moveDistance variable is the Y value of a parabola, to get more of a triangle shape, I fiddled with the
                    //numbers until I was happy with the result
                    int moveDistance= (int)Math.round(0.001*(Math.pow((attackTimer.millisElapsed()-100),2))+95);
                    
                    //The number inside thise move method is essentially the radius of the swing animation.
                    move(moveDistance*attackingWeaponDirection-attackTimer.millisElapsed()/8*attackingWeaponDirection);
                     
                    //This rotation makes the Axe look semi realistic, otherwise we would be hitting people with the tail of the Axe.
                    setRotation(getRotation()+120*attackingWeaponDirection);
                }
                
                    //When 200 milli seconds passes, the Axe stops moving and returns to the player
                else if(attackTimer.millisElapsed()>200){
                    attack=false;
                    setRotation(0);
                    setImage(hitbox);
                    followHero();
                }
            }
            if(attack==false){
                followHero();
                x=getX();
                y=getY();
                
                //These two lines puts the Axe on the front most layer, appearing above all other objects
                world.removeObject(this);
                world.addObject(this, x, y);
            }
        }
    }
    
    /**
     * This puts the Axe right infront of the Hero, giving the illusion that he's holding it
     */
    public void followHero(){
        setLocation(Hero.getXPos()+weaponDirection*40,Hero.getYPos());
        setRotation(0);
    }
    /**
     * This alligns the Axe's direction with that of the Hero's 
     * Using the Hero's getXDirectionChar() getter method
     */
    public void allignDirectionWithHero(){
        if(Hero.getXDirectionChar()=='r'){
                weaponDirection=1;
            }
            else if(Hero.getXDirectionChar()=='l'){
                weaponDirection=-1;
            }
    }
    //The following methods are all getter methods essential for communicating with other objects in the game
    public static int getAttackingWeaponDirection(){
        return attackingWeaponDirection;
    }
    public static int getWeaponDirection(){
        return weaponDirection;
    }
    public static int getXPosition(){
        return x;
    }
    public static int getYPosition(){
        return y;
    }
    public static int getAxeRotation(){
        return rotation;
    }
    static boolean isThrown()
    {
        return thrown;
    }
    static boolean isAttacking()
    {
        return attack;
    }
    static boolean isStuck()
    {
        return stuck;
    }
}
