import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.List;
/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{

    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    static int worldHeight=600;   
    static int worldWidth=400;
    int health = 5;
    int maxHealth=5;
    int coins =0;
    int level =0;
    int volume=0;
    public String tileName;
    static boolean doorsRemoved = false;
    Color coinsColor = new Color(255,200,126);
    public Label coinsLabel = new Label(coins,30);
    GreenfootSound music = new GreenfootSound("sounds/jeff_video_game_INTRO.mp3");
    GreenfootSound musicMain = new GreenfootSound("sounds/jeff_video_game_MAIN.mp3");
    SimpleTimer musicTimer = new SimpleTimer();
    Label instructions1 = new Label("",30);
    Label title = new Label ("",80);
    Label description = new Label("",28);
    Label author = new Label("",28);
            Label instructions2 = new Label("",30);
            
    /**
     * These ginormous arrays are used in the levels, 
     * they hold the information of every single tile of a particular level, its position, its name and direction
     */
    Tile[] level1Map = {
    new Tile("wallR",24,19),new Tile("wallR",15,52),new Tile("wallR",15,146),new Tile("wallR",26,253),
    new Tile("wallR",18,297),new Tile("wallR",17,339),new Tile("wallD",71,25),new Tile("wallD",105,19),
    new Tile("wallD",145,20),new Tile("wallD",194,23),new Tile("wallR",18,105),new Tile("wallD",224,23),
    new Tile("wallD",267,22),new Tile("wallD",309,22),new Tile("wallD",346,23),new Tile("wallD",392,24),
    new Tile("wallD",421,22),new Tile("wallD",460,22),new Tile("wallD",504,22),new Tile("wallD",534,22),
    new Tile("wallL",580,62),new Tile("wallL",588,103),new Tile("wallL",581,142),new Tile("wallL",577,259),
    new Tile("wallL",583,296),new Tile("wallL",593,332),new Tile("wallL",592,19),new Tile("wallCornerUR",33,378),
    new Tile("wallU",72,378),new Tile("wallU",98,378),new Tile("wallU",133,382),new Tile("wallU",174,383),
    new Tile("wallU",218,384),new Tile("wallU",251,385),new Tile("wallU",299,381),new Tile("wallU",342,383),
    new Tile("wallU",384,383),new Tile("wallU",421,386),new Tile("wallU",461,388),new Tile("wallU",500,387),
    new Tile("wallU",536,386),new Tile("wallCornerUL",572,385),new Tile("wallUL",576,263),new Tile("wallD",577,132),
    new Tile("wallD",16,147),new Tile("wallUR",20,259),new Tile("door2LR",582,168),new Tile("door2LR",583,213),};
    Tile[] level3Map = {
    new Tile("wallR",24,19),new Tile("wallR",15,52),new Tile("wallR",15,146),new Tile("wallR",26,253),
    new Tile("wallR",18,297),new Tile("wallR",17,339),new Tile("wallD",71,25),new Tile("wallD",105,19),
    new Tile("wallD",145,20),new Tile("wallD",194,23),new Tile("wallR",18,105),new Tile("wallD",224,23),
    new Tile("wallD",267,22),new Tile("wallD",309,22),new Tile("wallD",346,23),new Tile("wallD",392,24),
    new Tile("wallD",421,22),new Tile("wallD",460,22),new Tile("wallD",504,22),new Tile("wallD",534,22),
    new Tile("wallL",580,62),new Tile("wallL",588,103),new Tile("wallL",581,142),new Tile("wallL",577,259),
    new Tile("wallL",583,296),new Tile("wallL",593,332),new Tile("wallL",592,19),new Tile("wallCornerUR",33,378),
    new Tile("wallU",72,378),new Tile("wallU",98,378),new Tile("wallU",133,382),new Tile("wallU",174,383),
    new Tile("wallU",218,384),new Tile("wallU",251,385),new Tile("wallU",299,381),new Tile("wallU",342,383),
    new Tile("wallU",384,383),new Tile("wallU",421,386),new Tile("wallU",461,388),new Tile("wallU",500,387),
    new Tile("wallU",536,386),new Tile("wallCornerUL",572,385),new Tile("wallUL",576,263),new Tile("wallD",577,132),
    new Tile("wallD",16,147),new Tile("wallUR",20,259),new Tile("door2LR",582,168),new Tile("door2LR",583,213),};
    Tile[] level2Map = {
    new Tile("wallR",24,19),new Tile("wallR",15,52),new Tile("wallR",15,146),new Tile("wallR",26,253),
    new Tile("wallR",18,297),new Tile("wallR",17,339),new Tile("wallD",71,25),new Tile("wallD",105,19),
    new Tile("wallD",145,20),new Tile("wallD",194,23),new Tile("wallR",18,105),new Tile("wallD",224,23),
    new Tile("wallD",267,22),new Tile("wallD",309,22),new Tile("wallD",346,23),new Tile("wallD",392,24),
    new Tile("wallD",421,22),new Tile("wallD",460,22),new Tile("wallD",504,22),new Tile("wallD",534,22),
    new Tile("wallL",580,62),new Tile("wallL",588,103),new Tile("wallL",581,142),new Tile("wallL",577,259),
    new Tile("wallL",583,296),new Tile("wallL",593,332),new Tile("wallL",592,19),new Tile("wallCornerUR",33,378),
    new Tile("wallU",72,378),new Tile("wallU",98,378),new Tile("wallU",133,382),new Tile("wallU",174,383),
    new Tile("wallU",218,384),new Tile("wallU",251,385),new Tile("wallU",299,381),new Tile("wallU",342,383),
    new Tile("wallU",384,383),new Tile("wallU",421,386),new Tile("wallU",461,388),new Tile("wallU",500,387),
    new Tile("wallU",536,386),new Tile("wallCornerUL",572,385),new Tile("wallUL",576,263),new Tile("wallD",577,132),
    new Tile("wallD",16,147),new Tile("wallUR",20,259),new Tile("door2LR",582,168),new Tile("door2LR",583,213),new Tile("wallD",450,220),
new Tile("wallD",500,220),new Tile("wallUR",500,180),
new Tile("wallUL",450,180),};
    Tile[] level0Map = {
    new Tile("wallR",24,19),new Tile("wallR",15,52),new Tile("wallR",15,146),new Tile("wallR",26,253),
    new Tile("wallR",18,297),new Tile("wallR",17,339),new Tile("wallD",71,25),new Tile("wallD",105,19),
    new Tile("wallD",145,20),new Tile("wallD",194,23),new Tile("wallR",18,105),new Tile("wallD",224,23),
    new Tile("wallD",267,22),new Tile("wallD",309,22),new Tile("wallD",346,23),new Tile("wallD",392,24),
    new Tile("wallD",421,22),new Tile("wallD",460,22),new Tile("wallD",504,22),new Tile("wallD",534,22),
    new Tile("wallL",580,62),new Tile("wallL",588,103),new Tile("wallL",581,142),new Tile("wallL",577,259),
    new Tile("wallL",583,296),new Tile("wallL",593,332),new Tile("wallL",592,19),new Tile("wallCornerUR",33,378),
    new Tile("wallU",72,378),new Tile("wallU",98,378),new Tile("wallU",133,382),new Tile("wallU",174,383),
    new Tile("wallU",218,384),new Tile("wallU",251,385),new Tile("wallU",299,381),new Tile("wallU",342,383),
    new Tile("wallU",384,383),new Tile("wallU",421,386),new Tile("wallU",461,388),new Tile("wallU",500,387),
    new Tile("wallU",536,386),new Tile("wallCornerUL",572,385),new Tile("wallUL",576,263),new Tile("wallD",577,132),
    new Tile("wallR",16,147),new Tile("wallR",20,259),new Tile("wallR",18,176),new Tile("wallR",20,222),};
    
    /**
     * This is an array of the arrays above, it is the sequence of which they will be spawned in
     */
    Tile[][]mapArray={level0Map,level2Map,level1Map,level1Map,level1Map,level1Map,level1Map,level1Map,level1Map};
    
    /**
     * These arrays are the same idea as the map arrays, except they hold enemies instead
     */
    Enemy[]level0Army={};
    Enemy[]level1Army={new Skeleton(),new Skeleton(),new Skeleton(),new Skeleton()};
    Enemy[]level2Army={new Goblin(), new Goblin(), new Skeleton(), new Skeleton()};
    Enemy[]level3Army={new Goblin(), new Goblin(), new Skeleton(), new Skeleton(), new Skeleton(), new Skeleton(), new Skeleton(), new Skeleton(), new Skeleton(), new Skeleton()};
    Enemy[]level4Army={new Goblin(), new Goblin(),new GoblinKing(), new Skeleton(), new Skeleton()};
    Enemy[]level5Army={new Goblin(), new Goblin(),new Goblin(), new Goblin(),new Goblin(), new Goblin()};
    Enemy[]level6Army={new Skeleton(),new Skeleton(),new Skeleton(),new Skeleton(),new Skeleton(),new Skeleton(),new Skeleton(),new Skeleton(),new Skeleton(),new Skeleton(),new Skeleton(),new Skeleton(),};
    Enemy[]level7Army={new Goblin(), new Goblin(),new Goblin(), new Goblin(),new Goblin(), new Goblin(),new Skeleton(),new Skeleton(),new Skeleton(),new Skeleton(),new Skeleton(),new Skeleton(),new Skeleton(),new Skeleton(),new Skeleton(),new Skeleton(),new GoblinKing()};
    Enemy[][]armyArray={level0Army,level0Army,level1Army,level2Army,level3Army,level4Army,level5Army,level6Army,level7Army};
   
    
    
    public MyWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(worldHeight, worldWidth, 1, false); 
        doorsRemoved=false;
        musicTimer.mark();
        tileName="wallL";
        volume=25;
        Hero hero = new Hero();
        addObject(hero,300,200);
        
        addObject(new Axe(),70,200);
        addObject(new AxeHitbox(),60,200);
        
        
        
        
        
        
        coinsLabel.setFillColor(coinsColor);
        
        spawnArmy(level);
        spawnMap(level);
        //armyArray[level].set(1, new Nothing());
        if(level==0&getObjects(Title.class).size()<1){
            addObject(new Title(),300,100);
            description.setValue("Music by Brandon Dela Cruz [+] and [-] for volume");
            addObject(description,300,330);
            author.setValue("by Jeff Guo");
            addObject(author,300,300);
           
        }
    }
    public void act(){
        /**
         * Displays the Title and Description on level 0
         */
        
        if(level!=0){
            
            description.setValue("");
            author.setValue("");
            removeObjects(getObjects(Title.class));
               
        }
        /**
         * Displays the Instructions on level 1
         */
        if(level==1&getObjects(Label.class).size()<5){
            instructions1.setValue("Press space to melee attack");
            instructions2.setValue("Press q and arrow keys to ranged attack");
            
            addObject(instructions1,300,150);
            addObject(instructions2,300,250);
        }
        if(level!=1){
            
            
            instructions1.setValue("");
            instructions2.setValue("");
        }
        /**
         * Displays the UI such as the Hearts and Coins on level 2
         */
        if(level==2&getObjects(CoinUI.class).size()<1){
            
            updateHealth();
            addObject(new CoinUI(),580,30);
            addObject(coinsLabel, 550,30);
        }
        
        /**
         * Plays the music done by my friend Brandon Dela Cruz, who offered to make music for me.
         * What a nice guy.
         */
        if(musicTimer.millisElapsed()>16000){
            //The main music is on a 16 second loop and repeats seemlessly
            musicMain.play();
            
        }
        else{
            music.play();
        }
        
        music.setVolume(volume);
        musicMain.setVolume(volume);
        
        //Controls the volume when [-] or [+] are pressed 
        if ( Greenfoot.isKeyDown ("=")&volume<100 ){
            volume++;
        }
        if ( Greenfoot.isKeyDown ("-")&volume>0 ) {
            volume--;
        } 
        
        //Whenever the mouse button is clicked, a tile with the name of tileName is spawned on the location of the mouse
        /**
        if(Greenfoot.mouseClicked(null)){
            MouseInfo mouse = Greenfoot.getMouseInfo();
            
            addObject(new Tile(tileName,mouse.getX(),mouse.getY()),mouse.getX(),mouse.getY());
            //When a tile is created, the code prints out "newTile(tileName,x,y)" which I can then easily 
            //copy and paste into the mapArrays above
            System.out.println("new Tile(\""+tileName+"\","+mouse.getX()+","+mouse.getY()+"),");
        }
        if(Greenfoot.isKeyDown("p")){
            spawnSkeletonScreen();
            health=100;
            maxHealth=100;
            updateHealth();
        }
        **/
        //Removes all enemies after you die, so that you can die in peace
        if(health==0){
            removeObjects(getObjects(Enemy.class));
            if(volume>10){
                if(Greenfoot.getRandomNumber(30)==0){
                volume--;
                }
            }
            if(volume<=10){
                if(Greenfoot.getRandomNumber(100)==0){
                volume--;
                }
            }
        }
        
        //Deals with doors
        if(level>0&getObjects(Enemy.class).size()<=0){
            
            doorsRemoved=true;
        }
        else if(level>0){
            doorsRemoved=false;
        }        
    }
    public void setDoors(boolean bool){
        doorsRemoved=bool;
    }
    public static boolean doorsRemoved(){
       return doorsRemoved;
    }
    //Spawns the next room up
    public void levelUp(){
        level++;
        
        spawnArmy(level);
        spawnMap(level);
    }
    //Spawns the next room down
    public void levelDown(){
        
        level--;
        spawnArmy(level);
        spawnMap(level);
    }
    /**
     * Iterates through the armyArray that corresponds with the current level
    */
    public void spawnArmy(int level){
        
        
        removeObjects(getObjects(Enemy.class));
        for(int i = 0 ; i < armyArray[level].length ; i++){
            //adds the enemy object
            spawnRandomInArea(armyArray[level][i],100,50,550,350);
            
        }
    }
    /**
     * Iterates through the mapArray that corresponds with the current level
    */
    public void spawnMap(int level){
   
        removeObjects(getObjects(Tile.class));
        for(int i = 0 ; i < mapArray[level].length ; i++){
            //Adds the tile object
            addObject(mapArray[level][i],0,0);
        }
    }
    /**Gets a random number in between x and y 
     * @param x first number
     * @param y second number
     * @return a random number between x and y
     */
    public int getRandomNum(int x, int y){
        return Greenfoot.getRandomNumber(y-x)+x;
    }
    /**Spawns an enemy c on a random point in the rectangular area defined by the points (x1,y1) and (x2,y2)
     * @param Enemy c the type of enemy that will be spawned
     * @param x1 x position of first corner
     * @param y1 y position of first corner
     * @param x2 x position of second corner
     * @param y2 y position of second corner
     */
    public void spawnRandomInArea(Enemy c, int x1, int y1, int x2, int y2){
        addObject(c,getRandomNum(x1,x2),getRandomNum(y1,y2));
    }    
    /**
     * Increases and updates coins by 1
     */
    public void increaseCoins(){
        coins++;
        coinsLabel.setValue(coins);

    }
    /**
     * decreases and updates coins by a set amount
     * @param amount amount subtracted from coins
     */
    public void decreaseCoins(int amount){
        coins-=amount;
        coinsLabel.setValue(coins);

    }
    /**
     * Increases health and updates health
     */
    public void increaseHealth(){
        health++;
        updateHealth();
    }   
    /**
     * Decreases health and updates health
     */
    public void decreaseHealth(){
        health--;
        new GreenfootSound("sounds/Hit_/PlayerHurt_0.mp3").play();
        updateHealth();
    }
    /**
     *Updates Health
     */
    public void updateHealth(){
        //Removes the old hearts
        removeObjects(getObjects(HeartUI.class));
        //Cycles maxHealth times
        for(int i = 0 ; i <maxHealth ; i++){
            //Adds health amount of red hearts
            if(i<health){
                addObject(new HeartUI("images/heart0.png"),40*i+35,30);
            }
            //The rest of the hearts are empty hearts
            else{
                addObject(new HeartUI("images/heart1.png"),40*i+35,30);
            }
        }
    }
    /**
     * This fills the entire screen with a carpet of skeletons
     * Useless in game, but useful during testing
     */
    public void spawnSkeletonScreen(){
        removeObjects(getObjects(Skeleton.class));
        for(int x = 50 ; x <= 550 ; x = x + 20){
            for(int y = 50 ; y <= 350 ; y = y +20){
                spawnSkeleton(x,y);
            }
        }
    }
    public void spawnSkeleton(int x, int y)
    {
        //Spawns a skeleton at x y
        addObject(new Skeleton(),x,y);
    }
    public void spawnGoblin(int x, int y)
    {
        //Spawns a goblin at x y
        addObject(new Goblin(),x,y);
    }
    public void spawnCoin(int x, int y)
    {
        //Spawns a coin at x y
        addObject(new Coin(), x, y);
    }
    public static String getTileDirection(Tile t)
    {
        //This returns the tileDirection, I will use this in the code for the hitboxes, since each tiles hitbox 
        //varies based on its direction
        return t.tileName.substring(t.tileName.length()-1);
    }
}
