package co.bravebunny.circular.screens;

import co.bravebunny.circular.managers.GameInput;
import co.bravebunny.circular.managers.Particles;
import co.bravebunny.circular.objects.multiple.Enemy;
import co.bravebunny.circular.objects.single.Circle;
import co.bravebunny.circular.objects.single.Score;
import co.bravebunny.circular.objects.single.Ship;
import co.bravebunny.circular.objects.single.Ship.ShipState;
import co.bravebunny.circular.objects.Solid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Level extends Common implements Screen {
	
	//values
	private static float bpm = 107;
	private static Music music;
	private static float time = 0;
	public static int score = 0;
	
	//groups (object layers)
	public static Group layerGame = new Group();
	public static Group layerShip = new Group();
	public static Group layerObjects = new Group();
	public static Group layerOverlay = new Group();
	public static Group layerGUI = new Group();
	
    public static float getBPM() {
    	return bpm;
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
    	//time = TimeUtils.millis();
    	
    	super.show();
    	Circle.show();
        Score.show();
        Particles.show();
        Ship.show();
        
        getStage().addActor(layerGame);
        getStage().addActor(layerShip);
        getStage().addActor(layerObjects);
        getStage().addActor(layerOverlay);
        getStage().addActor(layerGUI);
        
    	//initialize input
    	GameInput input = new GameInput();
    	Gdx.input.setInputProcessor(input);
    	
    	//start music
    	music = Gdx.audio.newMusic(Gdx.files.internal("media/music/music1.ogg"));
    	music.play();
    	
    	//start rhythm
    	//rhythm();

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

    @Override
    public void dispose() {
    	super.dispose();
    	Particles.dispose();
    }
    
    public void renderRun(float delta) {
    	Particles.render(delta);
    	Ship.render(delta);
    	Circle.render(delta);
    	Score.render(delta);
    	
    	for (Solid solid : Solid.solids) {
    		solid.render(delta);
    	}
    	
        if (music.isPlaying()) {
        	time += delta;
            if (time >= 60/bpm) {
            	if (Ship.state == ShipState.ALIVE) {
            		//call all the rhythm related stuff
                	rhythm();
            	}
                time -= 60/bpm;
            }
        }
    }
    
    public void renderPause(float delta) {
    }
    
    
    //events that happen every beat
    public void rhythm() {
		@SuppressWarnings("unused")
		Enemy enemy = new Enemy(bpm);
		Circle.beat();
		Score.inc();
		//enemy.beat();

    }

}
