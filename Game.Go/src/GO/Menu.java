package GO.GO;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Menu extends MouseAdapter {

   private Game game;
   private Handler handler;
   private Random r = new Random();
   private HUD hud;

    public Menu(Game game, Handler handler, HUD hud){
        this.game = game;
        this.handler = handler;
        this.hud = hud;
    }

   public void mousePressed(MouseEvent e){

       int mx = e.getX();
       int my = e.getY();

       if(game.gameState == Game.State.Menu) {
           //Play Button
           if (mouseOver(mx, my, 210, 150, 200, 64)) {
               game.gameState = Game.State.Game;
               hud.setLevel(1);
               hud.setScore(0);
               handler.addObject(new Player(Game.WIDTH / 2 - 32, Game.HEIGHT / 2 - 32, ID.Player, handler));
               handler.clearEnemies();
               handler.addObject(new Enemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.Enemy, handler));

           }
           //Help
           if (mouseOver(mx, my, 210, 250, 200, 64)) {
               game.gameState = Game.State.Help;
           }
           //Quit
           if (mouseOver(mx, my, 210, 350, 200, 64)) {
               System.exit(1);
           }
       }
       //Back Button
       if(game.gameState == Game.State.Help){
           if(mouseOver(mx,my,210,350,200,64)){
               game.gameState = Game.State.Menu;
               return;
           }
       }
       //End Screen
       if(game.gameState == Game.State.End){
           if(mouseOver(mx,my,210,350,200,64)){
               game.gameState = Game.State.Game;
               handler.addObject(new Player(Game.WIDTH/2-32,Game.HEIGHT/2-32,ID.Player,handler));
               handler.clearEnemies();
               handler.addObject(new Enemy(r.nextInt(Game.WIDTH - 50),r.nextInt(Game.HEIGHT -50),ID.Enemy,handler));

           }
       }

   }
   public void mouseReleased(MouseEvent e){

   }

   private boolean mouseOver(int mx, int my,int x ,int y ,int width,int height){
       if(mx > x && mx < x + width){
           if (my > y && my < y + height){
               return true;
           }else return false;
       }else return false;


   }

   public void tick(){

   }

    public void render(Graphics g){
        if (game.gameState == Game.State.Menu) {
            Font fnt = new Font("arial",1,50);
            Font fnt2 = new Font("arial",1,30);

            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Menu",240,70);


            //Start
            g.setFont(fnt2);
            g.drawRect(210,150,200,64);
            g.drawString("Play",270,190);
            //Help
            g.drawRect(210,250,200,64);
            g.drawString("Help",270,290);
            //Exit
            g.drawRect(210,350,200,64);
            g.drawString("Quit",270,390);
        }else if(game.gameState == Game.State.Help){
            Font fnt = new Font("arial",1,50);
            Font fnt2 = new Font("arial",1,30);
            Font fnt3 = new Font("arial",1,20);

            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Help",240,70);

            g.setFont(fnt3);
            g.drawString("Use WASD keys to move player and dodge enemies",60,200);

            g.setFont(fnt2);
            g.drawRect(210,350,200,64);
            g.drawString("Back",270,390);
        }else if(game.gameState == Game.State.End){
            Font fnt = new Font("arial",1,50);
            Font fnt2 = new Font("arial",1,30);
            Font fnt3 = new Font("arial",1,20);

            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Game Over",180,70);

            g.setFont(fnt3);
            g.drawString("You lost with a score of : "+ hud.getScore(),175,200);

            g.setFont(fnt2);
            g.drawRect(210,350,200,64);
            g.drawString("Try Again!",240,390);
        }

    }
}
