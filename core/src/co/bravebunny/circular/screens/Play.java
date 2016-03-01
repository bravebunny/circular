package co.bravebunny.circular.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.MathUtils;
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
import co.bravebunny.circular.entities.objects.Multiplier;
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
    private Group layerBackground = new Group();
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
    private Multiplier multiplier;
    private EntityFactory factory = EntityFactory.getInstance();

    //objects
    private Music music;
    private Music deathMusic;

    //values
    private float time = 0;
    private int coinBeat = 0;
    private int coinFreq = 3;
    private float nextEventScore = 0;
    private static final int EVENT_SCORE_INC = 50;
    private float bpm;
    private float speed = 1;
	
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
        bpm = levels[selectedLevel].getBpm();

    	bgRed = 0;
    	bgGreen = 89;
    	bgBlue = 118;
    	stage = new Stage();
    	
    	super.show();

        multiplier = new Multiplier();
        multiplier.setLayer(layerBackground);
    	
    	circle = new Circle();
    	circle.setLayer(layerGame);
    	
    	ship = new Ship();
    	ship.setLayer(layerShip);
        ship.setBPM(bpm);

        hud = new HUD();
        hud.setLayer(layerHUD);

        combo = new Combo();
        combo.setBPM(bpm);
        combo.setLayer(layerHUD);

        score = new Score();
        score.setLevel(selectedLevel);
        score.startTween(bpm);
        score.setLayer(layerHUD);
        Particles.show();

        // the order of these lines defines the z-order of the layers
        getStage().addActor(layerBackground);
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
        if (music.getPosition() < 0) return;

        Array<Recyclable> enemies = factory.getEnemies();
        Array<Recyclable> coins = factory.getCoins();

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
        if (time >= 60/bpm) {
            if (ship.state == ShipState.ALIVE) {
                //call all the rhythm related stuff
                rhythm();
            }
            time -= 60/bpm;
        }

        rotateScreenProgress(delta);
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
            nextEventScore = 0;
            speed = 1;
            bpm = levels[selectedLevel].getBpm();
            rotationProgress = 0;
            rotateScreen = false;
            rotateSlowdown = 1;
            rotateDirection = 1;
            rotationCount = 0;
            viewport.getCamera().rotate(-angle, 0, 0, 1);
            angle = 0;

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
        	}, 60/bpm);
    	}
    }
    
    
    // events that happen every beat
    public void rhythm() {
        // used to know when we should spawn a coin
    	coinBeat = (coinBeat + 1)%coinFreq;

        Enemy enemy = factory.createEnemy();
        enemy.setSpeed(speed);
        enemy.setBPM(bpm);
        enemy.setRotation(ship.getRotation() + 180);
        enemy.grow();
		enemy.setLayer(layerObjects);
		
		if (coinBeat == 0) {
            if (lastCoin != null) {
                if(lastCoin.destroy()) combo.reset();
            }
            lastCoin = null;
            lastCoin = factory.createCoin();
            lastCoin.setBPM(bpm);
            lastCoin.setRotation(ship.getRotation() + 200);
            lastCoin.grow();
            lastCoin.setLayer(layerObjects);
		}

		circle.beat(bpm);
        score.inc(combo.getMultiplier());

        callEvents();
    }

    public void callEvents() {
        // weight that the difficulty of the level has in determining the frequency of game events
        float weight = 1 + (selectedLevel) * 0.5f;

        boolean isEvenTime = score.getScore() > nextEventScore;

        // score at witch we will call a game event
        if (nextEventScore == 0 || isEvenTime) nextEventScore += weight * EVENT_SCORE_INC;

        if (isEvenTime) {
            rotateEvent();
        }
    }

    private float rotationProgress = 0;
    private boolean rotateScreen = false;
    private int rotateSlowdown = 1;
    private int rotateDirection = 1;
    private int rotationCount = 0;

    public void rotateEvent() {
        rotateScreen = true;
    }

    public void speedupEvent() {
        speed *= 1.2;
        ship.setSpeed(speed);
    }

    public void rotateScreenProgress(float delta) {
        if(rotationCount > 1) {
            rotationCount = 0;
            rotateScreen = false;
        }
        if (!rotateScreen) return;

        if (rotationProgress > 0.5) rotateSlowdown *= -1;
        else if (rotationProgress < 0) {
            rotateDirection *= -1;
            rotateSlowdown = 1;
            rotationCount++;
        }

        float angle = MathUtils.lerpAngleDeg(0, 3*rotateDirection, rotationProgress);
        rotationProgress += 0.05 * delta * rotateSlowdown;
        viewport.getCamera().rotate(angle, 0, 0, 1);
        this.angle += angle;
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