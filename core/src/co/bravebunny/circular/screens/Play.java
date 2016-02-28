package co.bravebunny.circular.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import co.bravebunny.circular.entities.objects.Circle;
import co.bravebunny.circular.entities.objects.Coin;
import co.bravebunny.circular.entities.objects.Enemy;
import co.bravebunny.circular.entities.objects.HUD;
import co.bravebunny.circular.entities.objects.Combo;
import co.bravebunny.circular.entities.objects.Recyclable;
import co.bravebunny.circular.entities.objects.Score;
import co.bravebunny.circular.entities.objects.Ship;
import co.bravebunny.circular.entities.objects.Ship.ShipState;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.managers.EntityFactory;
import co.bravebunny.circular.managers.GameInput;
import co.bravebunny.circular.managers.Particles;

public class Play extends GameScreen implements Screen {
	//groups (object layers)
    private Group layerGame = new Group();
    private Group layerShip = new Group();
    private Group layerObjects = new Group();
    private Group layerOverlay = new Group();
    private Group layerHUD = new Group();

    //game objects
    private Ship ship;
    private Circle circle;
    private Coin lastCoin;
    private HUD hud;
    private Score score;
    private Combo combo;
    private EntityFactory factory = EntityFactory.getInstance();

    //objects
    private Music music;
    private Music deathMusic;

    //values
    private float time = 0;
    private int coinBeat = 0;
    private int coinFreq = 3;

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
        ship.setBPM(getBPM());

        hud = new HUD();
        hud.setLayer(layerHUD);

        combo = new Combo();
        combo.setBPM(getBPM());
        combo.setLayer(layerHUD);

        score = new Score();
        score.setLevel(selectedLevel);
        score.startTween(getBPM());
        score.setLayer(layerHUD);
        Particles.show();

        // the order of these lines defines the z-order of the layers
        getStage().addActor(layerGame);
        getStage().addActor(layerShip);
        getStage().addActor(layerObjects);
        getStage().addActor(layerOverlay);
        getStage().addActor(layerHUD);
        
    	// initialize input
    	GameInput input = new GameInput(this);
    	Gdx.input.setInputProcessor(input);
    	
    	// start music
        // TODO might need some countdown here to make the game not hang when starting
        music = Assets.getMusic(levels[selectedLevel].getMusicFile());
        deathMusic = Assets.getMusic("menu");
        deathMusic.setLooping(true);

        music.play();
        music.setLooping(true);

        ship.state = ShipState.ALIVE;


    }

    @Override
    public void hide() {
    	
    }

    @Override
    public void pause() {
        if (ship.state == ShipState.ALIVE) music.pause();
    	else deathMusic.pause();
        super.pause();
    }

    @Override
    public void resume() {
        if (ship.state == ShipState.ALIVE) music.play();
        else deathMusic.play();
    	super.resume();
    }
    
    public void renderRun(float delta) {
        Array<Recyclable> enemies = factory.getEnemies();
        Array<Recyclable> coins = factory.getCoins();

        if (music.getPosition() > 0) {
            if (!ship.isVisible()) {
                //ship.setVisibility(true);
            }
            Particles.render(delta);
	    	ship.render(delta);
	    	circle.render(delta);
            score.render(delta);
            hud.render(delta);
            combo.render(delta);

            //check collisions between enemies and ship
            for (Recyclable r : enemies) {
                Enemy e = (Enemy) r;
                if (!e.isDead()) {
                    e.render(delta);
                    if (e.collidesWith(ship)) {
                        e.explode();
                        death();
                    }
                }
            }

            //check collisions between coins and ship
            for (Recyclable r : coins) {
                Coin c = (Coin) r;
                if (!c.isDead()) {
                    c.render(delta);
                    if (c.collidesWith(ship)) {
                        c.collect();
                        combo.incCounter();
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

    public void death() {
        ship.destroy();
        hud.restartShow();
        circle.growToCover(viewport.getWorldWidth(), viewport.getWorldHeight(), layerGame, layerOverlay);
        music.pause();
        deathMusic.play();
        combo.reset();
        combo.clear();
    }
    
    public void restart() {
    	if (hud.restart.getScaleX() >= 1) {
            circle.shrink(layerGame, layerOverlay);
            hud.restartHide();
            score.setScore(0);

            factory.resetAll();

            Timer.schedule(new Task(){
        	    @Override
        	    public void run() {
                    music.play();
                    deathMusic.stop();
                    ship.reset();
        	    	ship.moveUp();
                    score.reset();
                }
        	}, 60/getBPM());
    	}
    }
    
    
    //events that happen every beat
    public void rhythm() {
    	coinBeat = (coinBeat + 1)%coinFreq;

        Enemy enemy = factory.createEnemy();
        enemy.setBPM(getBPM());
        enemy.setRotation(ship.getRotation() + 180);
        enemy.grow();
		enemy.setLayer(layerObjects);
		
		if (coinBeat == 0) {
            if (lastCoin != null) {
                if(lastCoin.destroy()) combo.reset();
            }
            lastCoin = null;
            lastCoin = factory.createCoin();
            lastCoin.setBPM(getBPM());
            lastCoin.setRotation(ship.getRotation() + 200);
            lastCoin.grow();
            lastCoin.setLayer(layerObjects);
		} else if (coinBeat == 2 && lastCoin != null) {
            //destroy the coin after some time
            //lastCoin.destroy();
        }
		
		
		circle.beat(getBPM());
        score.inc(combo.getMultiplier());
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
        factory.dispose();
        ship.dispose();
		circle.dispose();
		score.dispose();
		hud.dispose();
        music.dispose();
        combo.dispose();
    }
	
}