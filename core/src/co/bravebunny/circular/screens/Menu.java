package co.bravebunny.circular.screens;

import co.bravebunny.circular.entities.objects.Circle;
import co.bravebunny.circular.entities.objects.Level;
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
import com.badlogic.gdx.utils.Json;

public class Menu extends GameScreen implements Screen {
	GestureDetector gestures;
	
	//objects
	private Music music;
	
	//groups (object layers)
	public Group layerCircles = new Group();
	public Group layerLevels = new Group();
	public Group layerButtons = new Group();
	private Group layerLocked = new Group();
	private Circle circle;
		
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
    	
    	/*Level l[] = {
    		new Level("Tutorial", 0),
    		new Level("Easy", 0),
    		new Level("Medium", 100),
    		new Level("Hard", 300),
    		new Level("Crazy", 600),
    		new Level("Why", 1000)
    	};*/
    	
    	Json json = new Json();
    	for (int i = 0; i < levels.length; i++) {
    		levels[i] = new Level();
    		String levelJson = Gdx.files.internal("levels/level" + i + ".json").readString();
    		levels[i] = json.fromJson(Level.class, levelJson);
        	levels[i].setLayer(layerLevels);
        	levels[i].setPolarPosition(1000*i - 1000*selectedLevel, 0);
    	}
    	
    	// Not sure if this info should be here or in the json files
    	// since it isn't directly related to the music.
    	// It will stay here for now
    	levels[0].setLevelName("Tutorial");
    	levels[0].setMinTotalScore(0);
    	levels[1].setLevelName("Easy");
    	levels[1].setMinTotalScore(0);
    	levels[2].setLevelName("Medium");
    	levels[2].setMinTotalScore(100);
    	levels[3].setLevelName("Hard");
    	levels[3].setMinTotalScore(300);
    	levels[4].setLevelName("Crazy");
    	levels[4].setMinTotalScore(600);
    	levels[5].setLevelName("Why");
    	levels[5].setMinTotalScore(1000);
    	/*levels[6].setMusicFile("round");
    	levels[6].setBpm(128.36f);*/

        getStage().addActor(layerCircles);
        getStage().addActor(layerLevels);
        getStage().addActor(layerButtons);
        getStage().addActor(layerLocked);
    	
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
    	circle.dispose();
    	for (Level level: levels) {
    		level.dispose();
    	}
    }
    
    public void renderRun(float delta) {
    	circle.render(delta);
    	
    }
    
    public void renderPause(float delta) {
    }
	
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
