package co.bravebunny.circular.managers;

import co.bravebunny.circular.screens.Common;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Positions {
	
	/**
	 * Positions an actor in the center of the screen with polar coordinates.
	 * The origin of the referential is the center of the screen.
	 * Angle presumed to be in degrees.
	 * @param actor - actor to be positioned
	 * @param radius - distance from the center of the screen
	 * @param angle - angle (degrees) to be used to position the actor
	 */
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
	
	/**
	 * Positions an actor in the center of the screen.
	 * @param actor - actor to be positioned
	 */
	public static void setPolarPosition(Actor actor) {
		setPolarPosition(actor, 0f, 0f);
	}
	
	/**
	 * Positions an actor at a distance from the center of the screen (radius)
	 * @param actor - actor to be positioned
	 * @param radius - distance from the center of the screen
	 */
	public static void setPolarPosition(Actor actor, float radius) {
		setPolarPosition(actor, radius, 0f);
	}
	
	/**
	 * Gets the X position of an actor taking into account disparities caused by rotation
	 * and the origin of that actor.
	 * @param actor
	 * @return Absolute X coordinate of the center of the actor
	 */
	public static float getAbsoluteX(Actor actor) {
		return Common.getViewport().getWorldWidth()/2 - actor.getWidth()/2
				+ (float)MathUtils.cos(MathUtils.degreesToRadians*(actor.getRotation()+90))
				* getDistance(actor, 0, 0);
	}
	
	/**
	 * Gets the Y position of an actor taking into account disparities caused by rotation
	 * and the origin of that actor.
	 * @param actor
	 * @return Absolute Y coordinate of the center of the actor
	 */
	public static float getAbsoluteY(Actor actor) {
		return Common.getViewport().getWorldHeight()/2 - actor.getHeight()/2
				+ (float)MathUtils.sin(MathUtils.degreesToRadians*(actor.getRotation()+90))
				* getDistance(actor, 0, 0);
	}
	
	/**
	 * @param actor1
	 * @param actor2
	 * @return distance between actor1 and actor2
	 */
	public static float getDistance(Actor actor1, Actor actor2) {
		return (float)
		Math.sqrt( Math.pow(getCenterX(actor1) - getCenterX(actor2), 2)
		+ Math.pow(getCenterY(actor1) - getCenterY(actor2), 2));
	}
	
	/**
	 * @param actor
	 * @param x
	 * @param y
	 * @return Distance from actor to point (x, y)
	 */
	public static float getDistance(Actor actor, float x, float y) {
		return (float)
		Math.sqrt( Math.pow(getCenterX(actor) - x, 2)
		+ Math.pow(getCenterY(actor) - y, 2));
	}
	
	/**
	 * @param actor
	 * @return X coordinate of the center of the actor.
	 */
	public static float getCenterX(Actor actor) {
		return actor.getX() + actor.getWidth()/2;
	}
	
	/**
	 * @param actor
	 * @return Y coordinate of the center of the actor.
	 */
	public static float getCenterY(Actor actor) {
		return actor.getY() + actor.getHeight()/2;
	}
	
}
