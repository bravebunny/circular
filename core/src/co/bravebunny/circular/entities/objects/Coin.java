package co.bravebunny.circular.entities.objects;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.*;
import co.bravebunny.circular.managers.ActorTween;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.managers.Positions;
import co.bravebunny.circular.screens.GameScreen;
import co.bravebunny.circular.screens.Play;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Coin extends Solid {
	private float h;
	private float angle = 0;
	private int type;
	private AnimatedImage animation;
	private float elapsedTime = 0;
	private Sound collectSound;
	
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

	public void init() {
		coll_on = false;
		type = MathUtils.random(1);
		h = 400 - type*60;
		
		collectSound = Assets.getSound("coin");
		
		//actors.setOrigin(actors.getWidth()/2, actors.getHeight()/2);
		actors.setScale(0);
		animation = Assets.getAnimation("level/coin");
		actors.addActor(animation);
		Play.layerOverlay.addActor(actors);
		
		
	}
	
	public void grow(float bpm) {
		//grow to initial size
		Tween.to(actors, ActorTween.SCALE, 60/bpm).target(1 - type*0.3f).delay(30/bpm)
		.ease(Back.OUT).start(GameScreen.getTweenManager());
		
        //turn on collisions
        Timer.schedule(new Task(){
            @Override
            public void run() {
                coll_on = true;
            }
        }, 90/bpm);
		
        //destroy the coin after some time
        /*Timer.schedule(new Task(){
            @Override
            public void run() {
                destroy();
            }
        }, 3*60/bpm);*/
	}
	
	public void destroy() {
		//doesn't do much right now. should be prettier.
		dispose();
	}
	
	//coin beat effect
	public void beat(){
		//TODO
	}
	
	public void render(float delta) {
		//place the coin in the game screen, and rotate it to an angle in front of the ship
		Positions.setPolarPosition(actors, h*actors.getScaleX(), angle);
		coll_radius = 100*actors.getScaleX();

	}
	
	@Override
	public void setRotation(float degrees) {
		super.setRotation(degrees - 90);
		this.angle = degrees;
	}
	
	public void collect(float bpm) {
		coll_on = false;
		collectSound.play();
		animation.setSpeed(2);
        Timeline.createSequence()
        .push(Tween.to(actors, ActorTween.SCALE, 0.3f).target(actors.getScaleX()*1.2f, actors.getScaleX()*1.2f).ease(Circ.OUT))
        .push(Tween.to(actors, ActorTween.SCALE, 0.5f).target(0f, 0f).ease(Expo.IN))
        .start(GameScreen.getTweenManager());
	}
	
	public boolean isDead() {
		return actors == null;
	}

}
