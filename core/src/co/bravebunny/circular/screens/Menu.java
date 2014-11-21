package co.bravebunny.circular.screens;

import co.bravebunny.circular.Circular.CurrentScreen;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.managers.GameInput;
import co.bravebunny.circular.objects.Circle;
import co.bravebunny.circular.objects.Score;
import co.bravebunny.circular.objects.TextBlock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Menu extends Common implements Screen {
	//objects
	private Music music;
	
	//values
	public float previousTouchX;
	public boolean touching = false;
	public int totalScore = 0;
	public int selectedLevel = 0;
	public String[] levelNames = {"Tutorial", "Easy", "Medium", "Hard", "Why"};
	public TextBlock[] textBlocks = {null, null, null, null, null};
	
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
    	screen = CurrentScreen.MENU;
    	
    	bgRed = 0;
    	bgGreen = 89;
    	bgBlue = 118;
    	stage = new Stage();
    	
    	super.show();
    	circle = new Circle();
    	circle.setLayer(layerCircles);
    	
    	for (int i = 0; i < levelNames.length; i++) {
    		TextBlock text = new TextBlock();
        	text.setLayer(layerLevels);
        	text.setPolarPosition(1000*i - 1000*selectedLevel, 0);
        	text.setText(levelNames[i]);
        	textBlocks[i] = text;
    	}
    	
        Score.show();

        getStage().addActor(layerCircles);
        getStage().addActor(layerLevels);
        getStage().addActor(layerButtons);
        getStage().addActor(layerLocked);
        
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
    	
    	if (touching == true) {
    		float deltaX = Gdx.input.getX() - previousTouchX;
        	for (int i = 0; i < textBlocks.length; i++) {
            	textBlocks[i].setPolarPosition(1000*i - 1000*selectedLevel + deltaX*2000/Common.viewport.getScreenWidth(), 0);
        	}
    	}

    	
    }
    
    public void renderPause(float delta) {
    }

	@Override
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
    
	}

	@Override
	public void backKey() {
		// TODO Auto-generated method stub 
		
	}
}
