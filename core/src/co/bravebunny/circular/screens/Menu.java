package co.bravebunny.circular.screens;

import co.bravebunny.circular.entities.objects.Circle;
import co.bravebunny.circular.entities.objects.Level;
import co.bravebunny.circular.entities.objects.TextBlock;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.managers.GameGestures;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;

public class Menu extends GameScreen implements Screen {
	GestureDetector gestures;
	
	//objects
	private Music music;
	
	//groups (object layers)
	private Group layerCircles = new Group();
	private Group layerLevels = new Group();
	private Group layerButtons = new Group();
	private Group layerLocked = new Group();
	private Circle circle;
    private ShapeRenderer shapes = new ShapeRenderer();
    private TextBlock score = new TextBlock();
    private int totalScore = 0, nextMin = 0;
		
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

    	Json json = new Json();
    	for (int i = 0; i < levels.length; i++) {
    		levels[i] = new Level();
    		String levelJson = Gdx.files.internal("levels/level" + i + ".json").readString();
    		levels[i] = json.fromJson(Level.class, levelJson);
        	levels[i].setLayer(layerLevels);
            levels[i].setPosition(1000*i - 1000*selectedLevel, 0);
    	}

        setupScore();

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

    public void setupScore() {
        for (int i = 0; i < levels.length; i++) {
            Preferences prefs = Gdx.app.getPreferences("Circular");
            int score = prefs.getInteger("hs" + i, 0);
            levels[i].setScore(score);
            totalScore += score;
        }

        for (int i = levels.length-1; i > -1; i--) {
            int min = levels[i].getMinTotalScore();
            if (totalScore >= min) break;
            nextMin = min;
            levels[i].setLocked(true);
        }
        
        score.setText(totalScore + " / " + nextMin);
        score.setScale(0.3f);
        score.setAlignment(Align.center);
        score.setLayer(layerCircles);
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

		float progressWidth = viewport.getCamera().viewportWidth / ((float) nextMin / (float) totalScore);
		score.setPosition(progressWidth / 2 - viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 - 25);

		Gdx.gl.glEnable(GL20.GL_BLEND);
		drawProgress(viewport.getCamera().viewportWidth); //progress background rectangle
        drawProgress(progressWidth); //actual progress rectangle
		drawGradients(); //simulate a fade out effect when scrolling
        Gdx.gl.glDisable(GL20.GL_BLEND);
	}

	public void drawGradients() {
		float vpWidth = viewport.getCamera().viewportWidth;
		float vpHeight = viewport.getCamera().viewportHeight;
        float gradY = -vpHeight / 2;
        float gradHeight = vpHeight - 50;

		shapes.setProjectionMatrix(viewport.getCamera().projection);
		shapes.begin(ShapeRenderer.ShapeType.Filled);
		float r = bgRed / 255;
		float g = bgGreen / 255;
		float b = bgBlue / 255;

		float gradWidth = 300;
		float gradX = 800;
		Color blue = new Color(r, g, b, 1);
		Color transparent = new Color(r, g, b, 0);
		shapes.rect(-gradX, gradY, gradWidth, gradHeight, blue, transparent, transparent, blue);
        shapes.rect(gradX - gradWidth, gradY, gradWidth, gradHeight, transparent, blue, blue, transparent);
        shapes.setColor(blue);
		shapes.rect(-vpWidth / 2, gradY, vpWidth / 2 - gradX, gradHeight);
		shapes.rect(gradX, gradY, vpWidth / 2 - gradX, gradHeight);
		shapes.end();
	}

    public void drawProgress(float width) {
        float height = 50;
        float vpWidth = viewport.getCamera().viewportWidth;
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(1, 1, 1, 0.5f);
        shapes.rect(-vpWidth / 2, viewport.getCamera().viewportHeight / 2 - height, width, height);
        shapes.end();
    }
    
    public void renderPause(float delta) {}
	
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
        repositionLevels();
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
        repositionLevels();
	}

    public void repositionLevels() {
        for (int i = 0; i < levels.length; i++) {
            levels[i].moveTo(1000*i - 1000*selectedLevel);
        }
    }

	public void tap(float x, float y, int count) {
		if (!levels[selectedLevel].getLocked()) {
			music.stop();
			((Game)Gdx.app.getApplicationListener()).setScreen(new Play());
		}
	}

	@Override
	public void backKey() {
		// TODO Auto-generated method stub 
		
	}
}
