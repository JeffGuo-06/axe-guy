import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CoinUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CoinUI extends UI

{
    /**
     * Act - do whatever the CoinUI wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    GreenfootImage[] idle = new GreenfootImage[4];  
    SimpleTimer animationTimer = new SimpleTimer();
    int imageIndex=0;
    int size=40;
    SimpleTimer bobTimer = new SimpleTimer();
    int count = 0;
    int x=580;
    int y=30;
    public CoinUI(){
        setLocation(580,30);
    }
    public void bob(int bobDelay)
    {
        if(bobTimer.millisElapsed() < bobDelay)
        {
            return;
        }
        count++;
        bobTimer.mark();
        if(count%2==0){
            setLocation(getX(),getY()+3);
        }
        else{
            setLocation(getX(),getY()-3);
        }
    }
    public void animate(int animationDelay,int size)
    {
        MyWorld world = (MyWorld) getWorld();
        if(animationTimer.millisElapsed() < animationDelay)
        {
            return;
        }
        animationTimer.mark();
        imageIndex = (imageIndex + 1 )% idle.length;
        GreenfootImage currentImage = new GreenfootImage("images/coin_/coin_"+imageIndex+".png");
        currentImage.scale(size,size);
        setImage(currentImage);

        world.removeObject(this);
        world.addObject(this,x,y);
    }
    public void act()
    {
        bringToTop();
        animate(150,size);
        if(isTouching(Coin.class)){
            animate(-1,40);
            
        }
        else{
            size=30;
        }
        bob(500);
        x=getX();
        y=getY();
    }
}
