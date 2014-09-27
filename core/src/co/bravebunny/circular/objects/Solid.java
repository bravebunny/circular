package co.bravebunny.circular.objects;


import co.bravebunny.circular.objects.single.Ship;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public abstract class Solid {
	public static Array<Solid> solids = new Array<Solid>();
	
	public Solid() {
		solids.add(this);
	}
	
	//radius of the collision circle
	public float radius;
	
	//called when there is a collision with this object
	public abstract void collide();
	
	//actor to be used for the collision
	public abstract Actor getActor();
	
	public void render(float delta) {
		if (Ship.collidesWith(this)) {
			this.collide();
			Ship.destroy();
		}
	}
	
	public void dispose() {
		solids.removeValue(this, true);
	}
	


}
