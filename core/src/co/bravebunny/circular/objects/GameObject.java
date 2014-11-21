package co.bravebunny.circular.objects;

import co.bravebunny.circular.managers.Positions;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public abstract class GameObject {
	
	/**
	 * Actor that better represents the entire entity.
	 * Used as a reference for position, angle, collisions and more fun stuff
	 */
	public Actor body;

	public float getRotation() {
		if (body != null) {
			return body.getRotation();
		} else {
			return 0;
		}
	}
	
	public float getX() {
		if (body != null) {
			return Positions.getCenterX(body);
		} else {
			return 0;
		}
	}
	
	public float getY() {
		if (body != null) {
			return Positions.getCenterY(body);
		} else {
			return 0;
		}
	}

	public void setPosition(float x, float y) {
		body.setPosition(x, y);
	}

	public void setRotation(float angle) {
		body.setRotation(angle);
	}

	public void setLayer(Group layer) {
		layer.addActor(body);
	}
	
	public void setPolarPosition(float radius, float angle) {
		Positions.setPolarPosition(body, radius, angle);
	}
	
	public abstract void dispose();
}
