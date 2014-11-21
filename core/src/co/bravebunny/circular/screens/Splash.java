package co.bravebunny.circular.screens;

import co.bravebunny.circular.Circular.CurrentScreen;
import co.bravebunny.circular.Circular.State;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.managers.Positions;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Splash extends Common implements Screen {
	
	private Texture bunnyTexture = new Texture(Gdx.files.internal("img/splash/bunny0.png"));
	private Image bunny = new Image(bunnyTexture);
	private boolean animationDone = false;
    
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
    	screen = CurrentScreen.SPLASH;
    	
    	bgRed = 104;
    	bgGreen = 156;
    	bgBlue = 59;
    	
    	stage = new Stage();
    	
    	super.show();
    	
    	//placing the bunny in the center of the screen
    	Positions.setPolarPosition(bunny);
		Positions.setPolarPosition(bunny);
        stage.addActor(bunny);
        
        //Bunny fading
        bunny.addAction(Actions.sequence(
    		Actions.alpha(0),
    		Actions.fadeIn(0.5f),
    		Actions.delay(2),
    		Actions.run(new Runnable() {
	            @Override
	            public void run() {
	                //((Game)Gdx.app.getApplicationListener()).setScreen(new Level());
	            	animationDone = true;
	            }
    		})
        ));
        
        //Start loading assets
        Assets.queueLoading();
    }

    @Override
    public void hide() {    
    	dispose();
    }

    @Override
    public void pause() {       
    }

    @Override
    public void resume() {      
    }

    @Override
    public void dispose() {
    	bunnyTexture.dispose();
    	super.dispose();
    }
    
    public void renderRun(float delta) {
    	
    	if(Assets.update() && animationDone) {
    		Assets.setMenuSkin();
    		((Game)Gdx.app.getApplicationListener()).setScreen(new Menu());
    	}
        
    }
    
    public void renderPause(float delta){
    	renderRun(delta);
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
