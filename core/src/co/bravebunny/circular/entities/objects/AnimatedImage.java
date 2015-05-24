package co.bravebunny.circular.entities.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class AnimatedImage extends Image {
	protected Animation animation = null;
	private float stateTime = 0;
	private float originalFrameDuration = 0.1f;
	
	public AnimatedImage(Animation animation) {
	    super(animation.getKeyFrame(0));
	    this.animation = animation;
	    this.originalFrameDuration = animation.getFrameDuration();
	}
	
	@Override
	public void act(float delta) {
		TextureRegion keyFrame = animation.getKeyFrame(stateTime+=delta, true);
	    ((TextureRegionDrawable)getDrawable()).setRegion(keyFrame);
	
	    super.act(delta);
	}

	/**
	 * Multiplies the current animation speed.
	 * @param speed - a value of 1 uses the original speed, 2 means double the original speed, 0.5 means half the speed, etc.
	 */
	public void setSpeed(float speed) {
		animation.setFrameDuration(originalFrameDuration/speed);
	}
}