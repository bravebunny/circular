package co.bravebunny.circular.managers;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorAccessor implements TweenAccessor<Actor> {

	//possible tween types:
	public static final int SIZE = 1;
	public static final int SCALE = 2;
	public static final int ANGLE = 3;
	public static final int RADIUS = 4;
	
	@Override
	public int getValues(Actor target, int tweenType, float[] returnValues) {
		switch (tweenType) {
			case SIZE:
				returnValues[0] = target.getWidth(); 
				returnValues[0] = target.getHeight(); 
				return 1;
			case SCALE:
				returnValues[0] = target.getScaleX();
				return 2;
			case ANGLE:
				returnValues[0] = target.getRotation();
				return 3;
			case RADIUS:
				returnValues[0] = Positions.getDistance(target, 0, 0);
				
			default: assert false; return -1;
		}
	}

	@Override
	public void setValues(Actor target, int tweenType, float[] newValues) {
		switch (tweenType) {
		case SIZE:
			target.setWidth(newValues[0]); 
			target.setHeight(newValues[0]);
			break;
		case SCALE:
			target.setScale(newValues[0]);
			break;
		case ANGLE:
			target.setRotation(newValues[0]);
		case RADIUS:
			//Ship.setRadius(newValues[0]);
			break;
		default: assert false; break;
	}
		
	}

}
