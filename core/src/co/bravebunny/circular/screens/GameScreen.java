package co.bravebunny.circular.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import co.bravebunny.circular.Circular.State;
import co.bravebunny.circular.entities.objects.Level;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.managers.ActorTween;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class GameScreen {
	
	protected static Viewport viewport;
	protected static TweenManager tweenManager = new TweenManager();
	protected static Stage stage;
	protected static State state = State.RUN;
	protected static int totalScore = 0;
	protected static int selectedLevel = 0;
	
	protected static Level levels[];

	
	//color to be used as background for each screen [0, 255]
	protected float bgRed, bgGreen, bgBlue;
	
	public static Viewport getViewport() {
		return viewport;
	}
	public Stage getStage() {
		return stage;
	}
	public static TweenManager getTweenManager() {
		return tweenManager;
	}
	public static TextureAtlas getAtlas() {
		return Assets.skin.getAtlas();
	}

	//input handlers
	//do absolutely nothing by default
	public void touchDown(int screenX, int screenY) {}
	public void touchUp(int screenX, int screenY) {}
	public void backKey() {}
	public void fling(float velocityX, float velocityY) {}
	public void pan(float x, float y, float deltaX, float deltaY){}
	public void panStop() {}
	public void tap(float x, float y, int count) {}
	
	protected void render(float delta) {
    	Gdx.gl.glClearColor(bgRed/255f ,bgGreen/255f ,bgBlue/255f ,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clear the batch
        
        stage.draw(); //draw all actors on the Stage.getBatch()
        
        switch (state)
        {
        case RUN:
        	stage.act(); //update all actors
        	tweenManager.update(delta);
        	renderRun(delta);
            break;
        case PAUSE:
        	renderPause(delta);
            break;
        }
    }
    
    protected void show() {
    	viewport = new ExtendViewport(1080, 1080, stage.getCamera());
    	
    	stage.setViewport(viewport);
    	
    	//initialize tween engine
    	Tween.setCombinedAttributesLimit(4);
    	Tween.registerAccessor(Actor.class, new ActorTween());
    	
    	//Assets.manager.finishLoading();
    	//atlas = Assets.manager.get("img/pack.atlas");
    	
    }
    
    protected void resize(int width, int height) {
    	viewport.update(width, height, true);
    	//stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    	stage.getCamera().position.set(0, 0, 0);
    }
    
    public void pause() {
    	state = State.PAUSE;
    }

    public void resume() {
    	state = State.RUN;
    }
    
    protected void dispose() {
    	stage.dispose();
    }
    
    protected abstract void renderRun(float delta);
    protected abstract void renderPause(float delta);

}
