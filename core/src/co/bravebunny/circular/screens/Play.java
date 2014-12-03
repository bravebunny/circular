package co.bravebunny.circular.screens;

import co.bravebunny.circular.entities.objects.Circle;
import co.bravebunny.circular.entities.objects.Coin;
import co.bravebunny.circular.entities.objects.Enemy;
import co.bravebunny.circular.entities.objects.HUD;
import co.bravebunny.circular.entities.objects.Score;
import co.bravebunny.circular.entities.objects.Ship;
import co.bravebunny.circular.entities.objects.Ship.ShipState;
import co.bravebunny.circular.managers.GameInput;
import co.bravebunny.circular.managers.Particles;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Play extends GameScreen implements Screen {
	//objects
	private static Music music;
	
	//values
	private static float time = 0;
	public static int score = 0;
	
	//groups (object layers)
	//THESE NEED TO STOP BEING STATIC ASAP
	public static Group layerGame = new Group();
	public static Group layerShip = new Group();
	public static Group layerObjects = new Group();
	public static Group layerOverlay = new Group();
	public Group layerHUD = new Group();
	
	//game objects
	public Ship ship;
	public Circle circle;
	public HUD hud;
	public Score scoreText;
	public Array<Enemy> enemies = new Array<Enemy>();
	public Array<Coin> coins = new Array<Coin>();
	
    public float getBPM() {
    	return levels[selectedLevel].getBpm();
    }
	
    @Override
    public void render(float delta) {
    	super.render(delta);
    }
    
    @Override
    public void resize(int width, int height) {    
    	super.resize(width, height);
    }

    @Override
    public void show() {
    	bgRed = 0;
    	bgGreen = 89;
    	bgBlue = 118;
    	stage = new Stage();
    	
    	super.show();
    	
    	circle = new Circle();
    	circle.setLayer(layerGame);
    	
    	ship = new Ship();
    	ship.setLayer(layerShip);
    	
    	hud = new HUD();
    	hud.setLayer(layerHUD);
    	
        scoreText = new Score();
        scoreText.startTween(getBPM());
        scoreText.setLayer(layerHUD);
        Particles.show();
        
        getStage().addActor(layerGame);
        getStage().addActor(layerShip);
        getStage().addActor(layerObjects);
        getStage().addActor(layerOverlay);
        getStage().addActor(layerHUD);
        
    	//initialize input
    	GameInput input = new GameInput(this);
    	Gdx.input.setInputProcessor(input);
    	
    	//start music
    	music = Gdx.audio.newMusic(Gdx.files.internal("media/music/" + levels[selectedLevel].getMusicFile() + ".ogg"));
    	//music = Assets.getMusic("music1");
    	//I SHOULD BE USING THE ASSET MANAGER HERE
    	//I'M NOT, THOUGH
    	//WHY ARE WE YELLING
    	music.play();
    	
    	ship.state = ShipState.ALIVE;


    }

    @Override
    public void hide() {
    	
    }

    @Override
    public void pause() {
    	music.pause();
    	super.pause();
    }

    @Override
    public void resume() {
    	music.play();
    	super.resume();
    }
    
    public void renderRun(float delta) {
    	if (music.isPlaying()) {
	    	Particles.render(delta);
	    	ship.render(delta);
	    	circle.render(delta);
	    	scoreText.render(delta);
	    	hud.render(delta);
	    	
	    	for (int i = 0; i < enemies.size; i++) {
	    		if (enemies.get(i).isDead()) {
	    			enemies.removeIndex(i);
	    		} else {
	    			enemies.get(i).render(delta);
	    			if (ship.collidesWith(enemies.get(i))) {
		    			enemies.get(i).explode();
		    			enemies.removeIndex(i);
		    			ship.destroy();
		    			hud.restartShow();
		    			circle.growToCover(viewport.getWorldWidth(), viewport.getWorldHeight());
		    		}
	    		}
	    	}
	    	
	    	for (int i = 0; i < coins.size; i++) {
	    		if (coins.get(i).isDead()) {
	    			coins.removeIndex(i);
	    		} else {
	    			coins.get(i).render(delta);
	    			if (ship.collidesWith(coins.get(i))) {
	    				coins.get(i).collect();
	    				coins.removeIndex(i);
		    		}
	    		}
	    	}
	        
	    	time += delta;
	        if (time >= 60/getBPM()) {
	        	if (ship.state == ShipState.ALIVE) {
	        		//call all the rhythm related stuff
	            	rhythm();
	        	}
	            time -= 60/getBPM();
	        }
        
        }
    }
    
    public void renderPause(float delta) {
    }
    
    public void restart() {
    	if (hud.restart.getScaleX() >= 1) {
        	circle.shrink();
        	hud.restartHide();
        	score = 0;
        	
        	Timer.schedule(new Task(){
        	    @Override
        	    public void run() {
        	    	ship.reset();
        	    	ship.moveUp();
        	    }
        	}, 60/getBPM());
    	}
    }
    
    
    //events that happen every beat
    public void rhythm() {
		Enemy enemy = new Enemy();
		enemy.setRotation(ship.getRotation() + 180);
		enemy.grow(getBPM());
		enemies.add(enemy);
		enemy.setLayer(layerObjects);
		
		Coin coin = new Coin();
		coin.setRotation(ship.getRotation() + 200);
		//coin.grow(getBPM());
		coins.add(coin);
		coin.setLayer(layerObjects);
		
		circle.beat(getBPM());
		scoreText.inc();
		//enemy.beat();

    }

	@Override
	public void touchDown(int screenX, int screenY) {
		if (ship.state == Ship.ShipState.ALIVE) {
			ship.moveDown();
		} else {
			restart();
		}
		
	}

	@Override
	public void touchUp(int screenX, int screenY) {
		switch (ship.state)
        {
        case ALIVE:
        	ship.moveUp();
            break;
        case DEAD:
        	
            break;
        }
		
	}

	@Override
	public void backKey() {
		music.stop();
		dispose();
		((Game)Gdx.app.getApplicationListener()).setScreen(new Menu());
		
	}

	@Override
	public void dispose() {
		super.dispose();
		Particles.dispose();
		for (Enemy enemy : enemies) {
			enemy.dispose();
		}
		for (Coin coin : coins) {
			coin.dispose();
		}
		ship.dispose();
		circle.dispose();
		//score.dispose();
		hud.dispose();
	}
	
}