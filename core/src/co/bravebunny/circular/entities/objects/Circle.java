package co.bravebunny.circular.entities.objects;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Back;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import co.bravebunny.circular.managers.ActorTween;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.managers.Positions;
import co.bravebunny.circular.screens.GameScreen;
import co.bravebunny.circular.screens.Play;

public class Circle extends Clickable {
	
	private Image circleOuter = Assets.getImage("level/circle_outer");
	
	public Circle() {
		
		body = Assets.getImage("level/circle_inner");
		Positions.setPolarPosition(circleOuter);
		Positions.setPolarPosition(body);

		click_height = click_width = body.getWidth();
		
	    beat();
	}
	
	public void setLayer(Group layer) {
		layer.addActor(circleOuter);
	    layer.addActor(body);
	}
	
	public void render(float delta) {
		//nothin here brotha
	}
	
	/**
	 * Triggers rhythm-related visual effects in the circle
	 */
	public void beat() {
        Tween.from(circleOuter, ActorTween.SCALE, 60/Play.getBPM())
        .target(1.1f).ease(Back.OUT)
        .start(GameScreen.getTweenManager());
        
        Tween.from(body, ActorTween.SCALE, 60/Play.getBPM())
        .target(0.9f).ease(Back.OUT)
        .start(GameScreen.getTweenManager());
	}
	
	/**
	 * Expands inner circle until it covers a specified point (x, y)
	 */
	public void growToCover(float x, float y) {
		Play.layerGame.removeActor(body);
		Play.layerOverlay.addActor(body);
		
		float target = Positions.getDistance(body, x, y)
				/ (body.getWidth());
		
		Tween.to(body, ActorTween.SCALE, 60/Play.getBPM())
        .target(target).ease(Back.IN)
        .start(GameScreen.getTweenManager());
		
		
	}
	
	/**
	 * Makes the inner circle shrink back to its default size
	 */
	public void shrink() {
		Tween.to(body, ActorTween.SCALE, 60/Play.getBPM())
        .target(1).ease(Back.IN)
        .start(GameScreen.getTweenManager());
		
    	Timer.schedule(new Task(){
    	    @Override
    	    public void run() {
    	    	Play.layerOverlay.removeActor(body);
    			Play.layerGame.addActor(body);
    	    }
    	}, 60/Play.getBPM());
		
	}

	@Override
	public void dispose() {
		body.remove();
		circleOuter.remove();
		
	}

}
