package GO.GO;

import GO.GO.GameObject;
import GO.GO.ID;

import java.awt.*;



public class Player extends GameObject {


    public Player(int x, int y, ID id) {
        super(x, y, id);

    }

    public void tick() {
        x += velX;
        y += velY;

        x = Game.clamp(x,0,Game.WIDTH - 45);
        y = Game.clamp(y,0,Game.HEIGHT - 65);
    }

    public void render(Graphics g) {

        g.setColor(Color.white);
        g.fillRect(x,y,32,32);

    }


}
