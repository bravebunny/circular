package co.bravebunny.circular.objects.multiple;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import co.bravebunny.circular.managers.ActorAccessor;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.managers.Particles;
import co.bravebunny.circular.managers.Positions;
import co.bravebunny.circular.objects.Solid;
import co.bravebunny.circular.objects.single.Ship;
import co.bravebunny.circular.screens.Common;
import co.bravebunny.circular.screens.Level;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Back;

public class Enemy extends Solid{
	private Image image = Assets.loadImage("level/enemy");
	private float h;
	private float angle;
	private int type;
	
	private TweenCallback tweenCallback = new TweenCallback()
	{
		@Override
		public void onEvent(int type, BaseTween<?> source)
		{
			if(type == TweenCallback.END) {
				dispose();
			}
		}
	};

	public Enemy() {
		this.angle = Ship.getRotation() + 180;
		this.type = MathUtils.random(1);
		this.h = 400 - type*150;
		this.radius = 10;
		
		
		//place the enemy in the game screen, and rotate it to an angle in front of the ship
		image.setOrigin(image.getWidth()/2, image.getHeight()/2);
		image.setRotation(angle - 90);
		Positions.setPolarPosition(image, h, angle);
		image.setScale(0);
		Level.layerObjects.addActor(getActor());
		
		//grow to initial size
		Tween.to(image, ActorAccessor.SCALE, 60/Level.getBPM()).target(1 - type*0.3f)
		.ease(Back.OUT).start(Common.getTweenManager());
		
        //destroy the enemy after some time
        Timer.schedule(new Task(){
            @Override
            public void run() {
                destroy();
            }
        }, 3*60/Level.getBPM());
        
	}
	
	public void destroy() {
		//doesn't do much right now. should be prettier.
		dispose();
	}
	
	//enemy beat effect
	public void beat(){
		//TODO
	}

	@Override
	public void collide() {
		if (image.isVisible()) {
			Particles.create(Positions.getCenterX(image), Positions.getCenterY(image), "B71C1CFF");
			image.setVisible(false);
		}
		destroy();
	}

	@Override
	public Actor getActor() {
		return image;
	}
	
	public void render(float delta) {
		super.render(delta);
	}
	
	public void dispose() {
		super.dispose();
		image.remove();
	}
}