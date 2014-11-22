package co.bravebunny.circular.screens;

import co.bravebunny.circular.Circular.CurrentScreen;
import co.bravebunny.circular.entities.objects.Circle;
import co.bravebunny.circular.entities.objects.Level;
import co.bravebunny.circular.entities.objects.Score;
import co.bravebunny.circular.entities.objects.TextBlock;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.managers.GameGestures;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Menu extends GameScreen implements Screen {
	GestureDetector gestures;
	
	//objects
	private Music music;
	
	//groups (object layers)
	public Group layerCircles = new Group();
	public Group layerLevels = new Group();
	public Group layerButtons = new Group();
	public Group layerLocked = new Group();
	public Circle circle;
	
	
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
    	circle.setLayer(layerCircles);
    	
    	Level l[] = {
    		new Level("Tutorial", 0),
    		new Level("Easy", 0),
    		new Level("Medium", 100),
    		new Level("Hard", 300),
    		new Level("Crazy", 600),
    		new Level("Why", 1000)
    	};
    	levels = l;
    	
    	for (int i = 0; i < levels.length; i++) {
        	levels[i].setLayer(layerLevels);
        	levels[i].setPolarPosition(1000*i - 1000*selectedLevel, 0);
    	}
    	
    	// Fill in the info for each level.
    	// Will eventually be done via external file
    	// using a sexy 'for' cycle.
    	// Not now, though. I don't feel like it.
    	levels[0].setMusicFile("music0");
    	levels[0].setBpm(107);
    	levels[1].setMusicFile("music1");
    	levels[1].setBpm(107);
    	levels[2].setMusicFile("music2");
    	levels[2].setBpm(100);
    	levels[3].setMusicFile("music3");
    	levels[3].setBpm(126);
    	levels[4].setMusicFile("music4");
    	levels[4].setBpm(130);
    	levels[5].setMusicFile("music5");
    	levels[5].setBpm(140);
    	/*levels[6].setMusicFile("round");
    	levels[6].setBpm(128.36f);*/
    	
        Score.show();

        getStage().addActor(layerCircles);
        getStage().addActor(layerLevels);
        getStage().addActor(layerButtons);
        getStage().addActor(layerLocked);
        
    	//initialize input
    	/*GameInput input = new GameInput(this);
    	Gdx.input.setInputProcessor(input);*/
    	
        //initialize gestures
        gestures = new GestureDetector(new GameGestures(this));
    	Gdx.input.setInputProcessor(gestures);

    	music = Assets.getMusic("menu");
    	music.play();
    	music.setLooping(true);
    	

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
    	
    	/*if (touching == true) {
    		float deltaX = Gdx.input.getX() - previousTouchX;
    		Vector3 vector_center_screen = viewport.getCamera().project(
    				new Vector3(0, 0, 0));
    		Vector3 vector_delta = viewport.getCamera().unproject(
    				new Vector3(deltaX + vector_center_screen.x, 0, 0));
    		
        	for (int i = 0; i < textBlocks.length; i++) {
        		
            	textBlocks[i].setPolarPosition(1000*i - 1000*selectedLevel + vector_delta.x, 0);
            	
        	}
    	}*/

    	
    }
    
    public void renderPause(float delta) {
    }

	/*@Override
	public void touchDown(int screenX, int screenY) {
		touching = true;
		previousTouchX = screenX;
	}

	@Override
	public void touchUp(int screenX, int screenY) {
		touching = false;
		if (textBlocks[selectedLevel].getX() < -300) {
			selectedLevel = (selectedLevel + 1)%levelNames.length;
		} else if (textBlocks[selectedLevel].getX() > 300){
			selectedLevel = (selectedLevel - 1)%levelNames.length;
			if (selectedLevel < 0) {
				selectedLevel += levelNames.length;
			}
		}
		
    	for (int i = 0; i < textBlocks.length; i++) {
    		textBlocks[i].moveTo(1000*i - 1000*selectedLevel);
    	}
    
	}*/
	
	public void pan(float x, float y, float deltaX, float deltaY) {
		Vector3 vector_center_screen = viewport.getCamera().project(
				new Vector3(0, 0, 0));
		Vector3 vector_delta = viewport.getCamera().unproject(
				new Vector3(deltaX + vector_center_screen.x, 0, 0));
		
		//layerLevels.setX(layerLevels.getX() + vector_delta.x);
    	for (int i = 0; i < levels.length; i++) {
    		levels[i].setPosition(levels[i].getX() + vector_delta.x, levels[i].getY());
        }
	}
	
	public void panStop() {
		
		if (levels[selectedLevel].getX() < -500) {
			selectedLevel = selectedLevel + 1;
			if (selectedLevel > levels.length -1) {
				selectedLevel = levels.length -1;
			}
		} else if (levels[selectedLevel].getX() > 500){
			selectedLevel = (selectedLevel - 1)%levels.length;
			if (selectedLevel < 0) {
				selectedLevel = 0;
			}
		}
		
    	for (int i = 0; i < levels.length; i++) {
    		levels[i].moveTo(1000*i - 1000*selectedLevel);
    	}
	}
	
	public void fling(float velocityX, float velocityY) {
		if (!gestures.isPanning() & Math.abs(velocityX) > 100) {
			if (velocityX < 0) {
				selectedLevel = selectedLevel + 1;
				if (selectedLevel > levels.length -1) {
					selectedLevel = levels.length -1;
				}
			} else if (velocityX > 0){
				selectedLevel = (selectedLevel - 1)%levels.length;
				if (selectedLevel < 0) {
					selectedLevel = 0;
				}
			}
		}
		
		
    	for (int i = 0; i < levels.length; i++) {
    		levels[i].moveTo(1000*i - 1000*selectedLevel);
    	}
	}
	
	public void tap(float x, float y, int count) {
		//if (circle.isTouching(x, y)) {
			music.stop();
			((Game)Gdx.app.getApplicationListener()).setScreen(new Play());
		//}
	}

	@Override
	public void backKey() {
		// TODO Auto-generated method stub 
		
	}
}
