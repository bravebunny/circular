package co.bravebunny.circular.objects;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Back;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import co.bravebunny.circular.managers.ActorAccessor;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.managers.Positions;
import co.bravebunny.circular.screens.Common;
import co.bravebunny.circular.screens.Level;


public class Circle extends GameObject {
	
	private Image circleOuter = Assets.getImage("level/circle_outer");
	private Image circleInner = Assets.getImage("level/circle_inner");
	
	public Circle() {
		Positions.setPolarPosition(circleOuter);
		Positions.setPolarPosition(circleInner);
	    beat();
	}
	
	public void setLayer(Group layer) {
		layer.addActor(circleOuter);
	    layer.addActor(circleInner);
	}
	
	public void render(float delta) {
		//nothin here brotha
	}
	
	public void beat() {
        Tween.from(circleOuter, ActorAccessor.SCALE, 60/Level.getBPM())
        .target(1.1f).ease(Back.OUT)
        .start(Common.getTweenManager());
        
        Tween.from(circleInner, ActorAccessor.SCALE, 60/Level.getBPM())
        .target(0.9f).ease(Back.OUT)
        .start(Common.getTweenManager());
	}
	
	//fills the screen with the inner circle
	public void grow() {
		Level.layerGame.removeActor(circleInner);
		Level.layerOverlay.addActor(circleInner);
		
		float target = Positions.getDistance(circleInner, Common.getViewport().getWorldWidth(), Common.getViewport().getWorldHeight())
				/ (circleInner.getWidth());
		
		Tween.to(circleInner, ActorAccessor.SCALE, 60/Level.getBPM())
        .target(target).ease(Back.IN)
        .start(Common.getTweenManager());
		
		
	}
	
	public void shrink() {
		Tween.to(circleInner, ActorAccessor.SCALE, 60/Level.getBPM())
        .target(1).ease(Back.IN)
        .start(Common.getTweenManager());
		
    	Timer.schedule(new Task(){
    	    @Override
    	    public void run() {
    	    	Level.layerOverlay.removeActor(circleInner);
    			Level.layerGame.addActor(circleInner);
    	    }
    	}, 60/Level.getBPM());
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
