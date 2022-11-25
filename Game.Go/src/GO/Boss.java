package GO.GO;

import java.awt.*;
import java.util.Random;

public class Boss extends GameObject{

    private Handler handler;
    Random r = new Random();

    private int timer = 80;
    private int timer2 = 50;

    public Boss(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;

        velX = 0;
        velY = 2;
    }
    public Rectangle getBounds(){
        return new Rectangle((int) x, (int) y,48,48);
    }
    public void tick() {
        x += velX;
        y += velY;

        if(timer <= 0 ) velY = 0;
        else timer --;

        if(timer <=0 ) timer2 --;
        if(timer2 <=0) {
            if(velX == 0) velX = 2;
            int spawn = r.nextInt(10);
            if(spawn == 0) handler.addObject(new BossBullet((int)x+24, (int)y+24, ID.Enemy,handler ));
        }


       // if (y <= 0 || y >= Game.HEIGHT - 32)velY *= -1;
        if (x <= 0 || x >= Game.WIDTH - 48)velX *= -1;

    }
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int) x, (int) y,48,48);

    }
}
