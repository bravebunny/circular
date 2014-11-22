package co.bravebunny.circular.entities.objects;

public abstract class Clickable extends GameObject{
	float click_width;
	float click_height;
	
	public boolean isTouching(float x, float y) {
		return (this.getX() - click_width/2 < x &&
				x < this.getX() + click_width/2 &&
				this.getY() - click_height/2 < y &&
				y < this.getY() + click_height/2);
	}
	
	public void setClickBox(float w, float h) {
		click_width = w;
		click_height = h;
	}
}
