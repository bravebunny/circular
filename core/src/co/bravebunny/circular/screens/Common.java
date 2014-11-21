package co.bravebunny.circular.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import co.bravebunny.circular.Circular.CurrentScreen;
import co.bravebunny.circular.Circular.State;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.managers.ActorAccessor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class Common {
	
	protected static Viewport viewport;
	protected static Stage stage;
	protected static TweenManager tweenManager = new TweenManager();
	public static State state = State.RUN;
	public static CurrentScreen screen = CurrentScreen.SPLASH;
	
	//color to be used as background for each screen [0, 255]
	protected float bgRed, bgGreen, bgBlue;
	
	public static Viewport getViewport() {
		return viewport;
	}
	public static Stage getStage() {
		return stage;
	}
	public static TweenManager getTweenManager() {
		return tweenManager;
	}
	public static TextureAtlas getAtlas() {
		return Assets.skin.getAtlas();
	}

	public abstract void touchDown(int screenX, int screenY);
	
	public abstract void touchUp(int screenX, int screenY);
	
	public abstract void backKey();
	
	public void fling(float velocityX, float velocityY) {
		//Nothing interesting, just this boring comment.
	}
	
	public void pan(float x, float y, float deltaX, float deltaY){
		//Also boring
	}
	
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
    	this.viewport = new ExtendViewport(1080, 1080, stage.getCamera());
    	
    	this.stage.setViewport(viewport);
    	
    	//initialize tween engine
    	Tween.setCombinedAttributesLimit(4);
    	Tween.registerAccessor(Actor.class, new ActorAccessor());
    	
    	//Assets.manager.finishLoading();
    	//atlas = Assets.manager.get("img/pack.atlas");
    }
    
    protected void resize(int width, int height) {
    	this.viewport.update(width, height, true);
    	//stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    	stage.getCamera().position.set(0, 0, 0);
    }
    
    public void pause() {
    	this.state = State.PAUSE;
    }

    public void resume() {
    	this.state = State.RUN;
    }
    
    protected void dispose() {
    	this.stage.dispose();
    }
    
    protected abstract void renderRun(float delta);
    protected abstract void renderPause(float delta);

}
