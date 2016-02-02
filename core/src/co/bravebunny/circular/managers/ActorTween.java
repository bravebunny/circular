package co.bravebunny.circular.managers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;

import aurelienribon.tweenengine.TweenAccessor;

public class ActorTween implements TweenAccessor<Actor> {

	//possible tween types:
	public static final int SIZE = 1;
	public static final int SCALE = 2;
	public static final int ANGLE = 3;
	public static final int POSITION = 4;
	public static final int OPACITY = 5;
	
	@Override
	public int getValues(Actor target, int tweenType, float[] returnValues) {
		switch (tweenType) {
			case SIZE:
				returnValues[0] = target.getWidth();
				return 1;
			case SCALE:
				returnValues[0] = target.getScaleX();
				return 2;
			case ANGLE:
				returnValues[0] = target.getRotation();
				return 3;
			case POSITION:
				returnValues[0] = Positions.getCenterX(target);
				returnValues[1] = Positions.getCenterY(target);
				return 4;
			case OPACITY:
				returnValues[0] = target.getColor().a;
				return 5;
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
			break;
		case POSITION:
			target.setPosition(newValues[0], newValues[1]);
			break;
			case OPACITY:
				Color c = target.getColor();
				target.setColor(c.r, c.g, c.b, newValues[0]);
			break;
		default: assert false; break;
		}
	}
}
