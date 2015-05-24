package co.bravebunny.circular.entities.objects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Back;
import co.bravebunny.circular.managers.ActorTween;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.managers.Particles;
import co.bravebunny.circular.managers.Positions;
import co.bravebunny.circular.screens.GameScreen;

public class Enemy extends Solid{
	private float h;
	private float angle = 0;
	private int type;
	private Image body;
    private float bpm;

    private TweenCallback destroyCallback = new TweenCallback() {
		@Override
		public void onEvent(int type, BaseTween<?> source)
		{
			if(type == TweenCallback.END) {
				dispose();
			}
		}
	};

	public void init() {
		body = Assets.getImage("level/enemy");
		actors.addActor(body);
		coll_on = false;
		type = MathUtils.random(1);
		h = 400 - type*150;


		//actors.setOrigin(actors.getWidth()/2, actors.getHeight()/2);
		actors.setScale(0);
	}

    public void setBPM(float bpm) {
        this.bpm = bpm;
    }

    public void grow() {
        //grow to initial size
		Tween.to(actors, ActorTween.SCALE, 60/bpm).target(1 - type*0.3f)
		.ease(Back.OUT).start(GameScreen.getTweenManager());
		
        //turn on collisions
        Timer.schedule(new Task(){
            @Override
            public void run() {
                coll_on = true;
            }
        }, 60/bpm);
		
        //destroy the enemy after some time
        Timer.schedule(new Task(){
            @Override
            public void run() {
                destroy();
            }
        }, 3*60/bpm);
	}
	
	public void destroy() {
        Tween t = Tween.to(actors, ActorTween.SCALE, 60 / bpm).target(0)
                .ease(Back.IN).start(GameScreen.getTweenManager());
        t.setCallback(destroyCallback);
    }
	
	//enemy beat effect
	public void beat(){
		//TODO
	}

	public void explode() {
		if (actors.isVisible()) {
			Particles.create(Positions.getCenterX(actors), Positions.getCenterY(actors), "explosion", "B71C1CFF");
			actors.setVisible(false);
		}
		destroy();
	}
	
	public void render(float delta) {
		//place the enemy in the game screen, and rotate it to an angle in front of the ship
		Positions.setPolarPosition(actors, h, angle);
		coll_radius = 60*actors.getScaleX();
	}
	
	@Override
	public void setRotation(float degrees) {
		super.setRotation(degrees - 90);
		this.angle = degrees;
		
	}
	
	public void dispose() {
		if (actors != null) {
			actors.remove();
		}
		actors = null;
	}
	
	public boolean isDead() {
		return actors == null;
	}
}