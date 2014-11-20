package co.bravebunny.circular.screens;

import co.bravebunny.circular.Circular.CurrentScreen;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.managers.GameInput;
import co.bravebunny.circular.objects.single.Circle;
import co.bravebunny.circular.objects.single.Score;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Menu extends Common implements Screen {
	//objects
	private static Music music;
	
	//values
	public static int totalScore = 0;
	
	//groups (object layers)
	public static Group layerButtons = new Group();
	public static Group layerLocked = new Group();
	public static Group layerLevels = new Group();
	public static Group layerCircles = new Group();
	public static Circle circle;
	
	
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
    	screen = CurrentScreen.MENU;
    	
    	bgRed = 0;
    	bgGreen = 89;
    	bgBlue = 118;
    	stage = new Stage();
    	
    	super.show();
    	circle = new Circle();
    	circle.setLayer(layerCircles);
        Score.show();
        
        getStage().addActor(layerButtons);
        getStage().addActor(layerLocked);
        getStage().addActor(layerLevels);
        getStage().addActor(layerCircles);
        
    	//initialize input
    	GameInput input = new GameInput(this);
    	Gdx.input.setInputProcessor(input);

    	music = Assets.getMusic("menu");
    	music.play();
    	

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
    }
    
    public void renderRun(float delta) {
    	circle.render(delta);
    	Score.render(delta);
    }
    
    public void renderPause(float delta) {
    }

	@Override
	public void touchDown(int screenX, int screenY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void touchUp(int screenX, int screenY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void backKey() {
		// TODO Auto-generated method stub
		
	}

}
