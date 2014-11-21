package co.bravebunny.circular.objects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import co.bravebunny.circular.managers.ActorAccessor;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.managers.Particles;
import co.bravebunny.circular.managers.Positions;
import co.bravebunny.circular.screens.Common;
import co.bravebunny.circular.screens.Level;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Back;

public class Enemy extends Solid{
	private float h;
	private float angle = 0;
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

		
		body = Assets.getImage("level/enemy");
		coll_on = false;
		type = MathUtils.random(1);
		h = 400 - type*150;
		radius = 10;
		
		body.setOrigin(body.getWidth()/2, body.getHeight()/2);
		body.setScale(0);
		Level.layerObjects.addActor(body);
		
		//grow to initial size
		Tween.to(body, ActorAccessor.SCALE, 60/Level.getBPM()).target(1 - type*0.3f)
		.ease(Back.OUT).start(Common.getTweenManager());
		
        //turn on collisions
        Timer.schedule(new Task(){
            @Override
            public void run() {
                coll_on = true;
            }
        }, 60/Level.getBPM());
		
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

	public void explode() {
		if (body.isVisible()) {
			Particles.create(Positions.getCenterX(body), Positions.getCenterY(body), "B71C1CFF");
			body.setVisible(false);
		}
		destroy();
	}
	
	public void render(float delta) {
		super.render(delta);
		//place the enemy in the game screen, and rotate it to an angle in front of the ship
		Positions.setPolarPosition(body, h, angle);

	}
	
	@Override
	public void setRotation(float degrees) {
		super.setRotation(degrees - 90);
		this.angle = degrees;
		
	}
	
	public void dispose() {
		body.remove();
		body = null;
	}
	
	public boolean isDead() {
		return body == null;
	}
}