package co.bravebunny.circular.managers;

import co.bravebunny.circular.screens.Common;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Positions {

	//positions an actor in the center of the screen with polar coordinates.
	//the origin of the referential is the center of the screen
	//angle presumed to be in degrees
	public static void setPolarPosition(Actor actor, float radius, float angle) {
		actor.setX(-actor.getWidth()/2
				+ (float)MathUtils.cos(MathUtils.degreesToRadians*(angle))
				* radius);
		
		actor.setY(-actor.getHeight()/2
				+ (float)MathUtils.sin(MathUtils.degreesToRadians*(angle))
				* radius);
		
		//sets the origin to the center of the actor
		//only affects rotation and scaling
		//probably shouldn't be here since it doesn't affect the position at all
		//but this is my game so I do what I want
		actor.setOrigin(actor.getWidth()/2, actor.getHeight()/2);
	}
	public static void setPolarPosition(Actor actor) {
		setPolarPosition(actor, 0f, 0f);
	}
	public static void setPolarPosition(Actor actor, float radius) {
		setPolarPosition(actor, radius, 0f);
	}
	
	
	//these getters get the X/Y positions of an actor, 
	//taking into account disparities caused by rotation
	//and the origin of that actor
	public static float getAbsoluteX(Actor actor) {
		return Common.getViewport().getWorldWidth()/2 - actor.getWidth()/2
				+ (float)MathUtils.cos(MathUtils.degreesToRadians*(actor.getRotation()+90))
				* getDistance(actor, 0, 0);
	}
	
	public static float getAbsoluteY(Actor actor) {
		return Common.getViewport().getWorldHeight()/2 - actor.getHeight()/2
				+ (float)MathUtils.sin(MathUtils.degreesToRadians*(actor.getRotation()+90))
				* getDistance(actor, 0, 0);
	}
	
	//get the distance between the center of two actors
	public static float getDistance(Actor actor1, Actor actor2) {
		return (float)
		Math.sqrt( Math.pow(getCenterX(actor1) - getCenterX(actor2), 2)
		+ Math.pow(getCenterY(actor1) - getCenterY(actor2), 2));
	}
	
	//get the distance between the center of an actor and a position (cartesian coordinates)
	public static float getDistance(Actor actor, float x, float y) {
		return (float)
		Math.sqrt( Math.pow(getCenterX(actor) - x, 2)
		+ Math.pow(getCenterY(actor) - y, 2));
	}
	
	
	//these two get the X/Y positions of the center of the actor
	public static float getCenterX(Actor actor) {
		return actor.getX() + actor.getWidth()/2;
	}
	public static float getCenterY(Actor actor) {
		return actor.getY() + actor.getHeight()/2;
	}
	
}
