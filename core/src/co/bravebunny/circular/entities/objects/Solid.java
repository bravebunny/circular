package co.bravebunny.circular.entities.objects;

import co.bravebunny.circular.managers.Positions;

public abstract class Solid extends GameObject {
	
	protected float coll_radius; //radius of the collision circle
	protected boolean coll_on = true; //true if collisions are enabled
	
	public boolean collidesWith(Solid object) {
		return (Positions.getDistance(actors, object.getX(), object.getY()) < (coll_radius + object.getCollRadius())) 
				&&
				coll_on && object.isCollidable();

	}
	
	public float getCollRadius() {
		return coll_radius;
	}
	
	public boolean isCollidable() {
		return coll_on;
	}
	
	public void setCollidable(boolean bool) {
		coll_on = bool;
	}
	
	public void setCollRadius(float coll_radius) {
		this.coll_radius = coll_radius;
	}


}
