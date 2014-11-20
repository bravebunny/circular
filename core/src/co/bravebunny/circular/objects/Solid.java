package co.bravebunny.circular.objects;

import co.bravebunny.circular.managers.Positions;

public abstract class Solid extends GameObject {
	
	public float radius; //radius of the collision circle
	public boolean coll_on = true; //true if collisions are enabled
	
	public void render(float delta) {

	}
	
	public boolean collidesWith(Solid object) {
		return (Positions.getDistance(body, object.getX(), object.getY()) < (radius + object.radius)) 
				&&
				coll_on;

	}


}
