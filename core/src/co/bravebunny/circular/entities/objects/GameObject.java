package co.bravebunny.circular.entities.objects;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import co.bravebunny.circular.managers.Positions;

public abstract class GameObject {
	
	/**
	 * Group of actors that represent this object
	 */
	public Group actors;
	
	public GameObject() {
		actors = new Group();
		init();
		centerActors();
	}
	
	abstract public void init();

	public float getRotation() {
		if (actors != null) {
			return actors.getRotation();
		} else {
			return 0;
		}
	}

	public void setRotation(float angle) {
		actors.setRotation(angle);
	}
	
	public float getX() {
		if (actors != null) {
			return Positions.getCenterX(actors);
		} else {
			return 0;
		}
	}

	public float getY() {
		if (actors != null) {
			return Positions.getCenterY(actors);
		} else {
			return 0;
		}
	}

	public void setPosition(float x, float y) {
		actors.setPosition(x, y);
	}

	public void setLayer(Group layer) {
		layer.addActor(actors);
	}
	
	public void setPolarPosition(float radius, float angle) {
		Positions.setPolarPosition(actors, radius, angle);
	}
	
	/**
	 * By default, centers all the actors at the origin of the group.
	 * Should be implemented differently in each object in case
	 * any of the actors need a different position or rotation
	 */
	public void centerActors() {
		for (Actor actor : actors.getChildren()) {
			Positions.setPolarPosition(actor);
		}
	}

	public void setVisibility(boolean visibility) {
		actors.setVisible(visibility);
	}

	public boolean isVisible() {
		return actors.isVisible();
	}
	
	public void dispose() {
		actors.remove();
	}
}
