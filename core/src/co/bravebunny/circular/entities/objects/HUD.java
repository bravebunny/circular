package co.bravebunny.circular.entities.objects;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Back;
import co.bravebunny.circular.managers.ActorTween;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.managers.Positions;
import co.bravebunny.circular.screens.GameScreen;
import co.bravebunny.circular.screens.Play;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class HUD extends GameObject {
	public Image restart = Assets.getImage("level/hud_restart");
	
	public HUD() {
		Positions.setPolarPosition(restart);
	    Play.layerHUD.addActor(restart);
	    restart.setScale(0, 0);
	    restart.setVisible(false);
	}
	
	public void render(float delta) {
		restart.rotateBy(delta*30);
	}
	
	//Grows the restart symbol into sight
	public void restartShow() {
		restart.setVisible(true);
		Tween.to(restart, ActorTween.SCALE, 60/Play.getBPM())
        .target(1.0f).ease(Back.OUT).delay(60/Play.getBPM())
        .start(GameScreen.getTweenManager());
		
	}
	
	//Shrinks the restart symbol back 
	public void restartHide() {
		Tween.to(restart, ActorTween.SCALE, 60/Play.getBPM())
        .target(0.0f).ease(Back.IN)
        .start(GameScreen.getTweenManager());
		
		Timer.schedule(new Task(){
    	    @Override
    	    public void run() {
    	    	restart.setVisible(false);
    	    }
    	}, 60/Play.getBPM());
		
		
	}

	@Override
	public void dispose() {
		restart.remove();
	}
	
}



