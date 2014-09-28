package co.bravebunny.circular.objects.single;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Back;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import co.bravebunny.circular.managers.ActorAccessor;
import co.bravebunny.circular.managers.Positions;
import co.bravebunny.circular.screens.Common;
import co.bravebunny.circular.screens.Level;


public class Circle {
	static AtlasRegion circleOuterRegion = Common.getAtlas().findRegion("level/circle_outer");
	static private Image circleOuter = new Image(circleOuterRegion);
	static AtlasRegion circleInnerRegion = Common.getAtlas().findRegion("level/circle_inner");
	static private Image circleInner = new Image(circleInnerRegion);
	
	
	public static  void show() {
		Positions.setPolarPosition(circleOuter);
		Positions.setPolarPosition(circleInner);
		Level.layerGame.addActor(circleOuter);
	    Level.layerGame.addActor(circleInner);
	    beat();
	}
	
	public static  void render(float delta) {
		//nothing here brotha
	}
	
	public static  void beat() {
        Tween.from(circleOuter, ActorAccessor.SCALE, 60/107f)
        .target(1.1f).ease(Back.OUT)
        .start(Common.getTweenManager());
        
        Tween.from(circleInner, ActorAccessor.SCALE, 60/107f)
        .target(0.9f).ease(Back.OUT)
        .start(Common.getTweenManager());
	}
	
	//fills the screen with the inner circle
	public static  void grow() {
		Level.layerGame.removeActor(circleInner);
		Level.layerOverlay.addActor(circleInner);
		
		float target = Positions.getDistance(circleInner, Common.getViewport().getWorldWidth(), Common.getViewport().getWorldHeight())
				/ (circleInner.getWidth()/2);
		
		Tween.to(circleInner, ActorAccessor.SCALE, 0.8f)
        .target(target, target).ease(Back.IN)
        .start(Common.getTweenManager());
	}

}
