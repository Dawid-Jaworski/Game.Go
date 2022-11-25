package GO.GO;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;


public class Game extends Canvas implements Runnable{

/**
 * The following code initializes a bunch of other classes/functions into the main game class*/

    private static final long serialVersionUID = 4005983670752542640L;
    public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
    private Thread thread;
    private boolean running = false;
    private Random r = new Random();
    private Handler handler;
    private HUD hud;
    private Spawn spawner;
    private Menu menu;
    /** enum allows us to create numerous states for us to switch between as there are numerous windows with different functionalities */
    public enum State {
        Menu,
        Help,
        Game,
        End;
    }
    /**  we start the program in the State.Menu window that allows us to choose further options */
    public static State gameState = State.Menu;

    /**  this is the main game function that handles most of the heavy lifting for the game and menu screen*/
    public Game() {
        handler = new Handler();
        hud = new HUD();
        menu = new Menu(this,handler,hud);
        this.addKeyListener(new KeyInput(handler));
        this.addMouseListener(menu);



        new Window(WIDTH, HEIGHT, "Dodge Ball", this);


        spawner = new Spawn(handler, hud);

        //spawn instances
        if (gameState == State.Game) {
            handler.addObject(new Player(Game.WIDTH / 2 - 32, Game.HEIGHT / 2 - 32, ID.Player, handler));
            handler.addObject(new Enemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.Enemy, handler));
        }else{
            for(int i = 0; i < 20; i++){
                handler.addObject(new MenuParticle(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.MenuParticle, handler));
            }
        }
    }
    /** this starts the Thread which then leads to frames and ticks kicking off allowing the game to function properly as it give us a constant frame rate */
    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
/**
 * Dewittes games loop this game loop allows the game to render threads and also limit frames
 * */
    public void run() {
        this.requestFocus();
        long lastTime =System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
               // System.out.println("FPS:"+ frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick(){
        handler.tick();
        if(gameState == State.Game){
            hud.tick();
            spawner.tick();

            if(HUD.HEALTH <= 0  ){
                HUD.HEALTH = 100;
                gameState = State.End;
                handler.clearEnemies();
                for(int i = 0; i < 20; i++){
                    handler.addObject(new MenuParticle(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.MenuParticle, handler));
                }

            }
        }else if(gameState == State.Menu || gameState == State.End){
            menu.tick();
        }


    }
    /** render loads the graphical interfaces such as the player, enemies, HUD */
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;

        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0,0,WIDTH,HEIGHT);

        handler.render(g);

        if(gameState == State.Game){
            hud.render(g);
        }else if(gameState == State.Menu || gameState == State.Help || gameState == State.End){
            menu.render(g);
        }


        g.dispose();
        bs.show();
    }
    /** this clamps the screen to a certain value which allows the enemies to bounce on the screen */
    public static float clamp(float var,float min,float max){
        if (var >= max)
            return var = max;
        else if (var <= min)
            return var = min;
        else
            return var;
    }


    /** we load/start the game */
    public static void main(String[] args){
        new Game();
    }



}
