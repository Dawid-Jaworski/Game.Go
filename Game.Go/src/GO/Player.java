package GO.GO;

import GO.GO.GameObject;
import GO.GO.ID;

import java.awt.*;



public class Player extends GameObject {
    Handler handler;

    public Player (float x, float y, ID id, Handler handler){
        super(x,y,id);
        this.handler = handler;
    }


    public Player(float x, float y, ID id) {
        super(x, y, id);

    }
    public Rectangle getBounds(){
        return new Rectangle((int) x, (int) y,32,32);
    }
    public void tick() {
        x += velX;
        y += velY;

        x = Game.clamp(x,0,Game.WIDTH - 45);
        y = Game.clamp(y,0,Game.HEIGHT - 65);

       // handler.addObject(new Trail(x,y,ID.Trail,Color.white,32,32,0.07f,handler));

        collision();

    }
    private void collision(){
        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);
                    //Collision check
            if(tempObject.getId() == ID.Enemy || tempObject.getId() == ID.FastEnemy || tempObject.getId() == ID.SmartEnemy || tempObject.getId() == ID.RandomFastEnemy){
                if(getBounds().intersects(tempObject.getBounds())){
                    HUD.HEALTH -= 2;
                }

            }


        }
    }

    public void render(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g.setColor(Color.white);
        g.fillRect((int) x, (int) y,32,32);

    }


}
