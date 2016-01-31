package co.bravebunny.circular.entities.objects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Back;
import aurelienribon.tweenengine.equations.Circ;
import aurelienribon.tweenengine.equations.Expo;
import aurelienribon.tweenengine.equations.Quad;
import co.bravebunny.circular.managers.ActorTween;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.managers.Particles;
import co.bravebunny.circular.managers.Positions;
import co.bravebunny.circular.screens.GameScreen;

public class Coin extends Solid implements Recyclable {
    //constants:
    String PCOLOR = "FFDE00FF";	//color of effect particles
    //values:
    private float h;
	private float angle = 0;
    private float elapsedTime = 0;
    private boolean dead;
    //external values:
    private float bpm;
    private int type;
    //assets:
    private AnimatedImage animation;
    private Sound collectSound;

    private TweenCallback collectCallback = new TweenCallback() {
        @Override
        public void onEvent(int type, BaseTween<?> source) {
            setDead(true);
        }
    };

    public void setBPM(float bpm) {
        this.bpm = bpm;
    }

	public void init() {
        System.out.println("---CREATED COIN---");
        collectSound = Assets.getSound("coin");
		animation = Assets.getAnimation("level/coin");
        actors.addActor(animation);
        reset();
    }

    public void reset() {
        actors.getColor().a = 1;
        setDead(false);
        coll_on = false;
        type = MathUtils.random(1);
        h = 400 - type * 60;
        actors.setScale(0);
        animation.setSpeed(1);
    }

    public void grow() {
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
        /*Timer.schedule(new Task() {
            @Override
            public void run() {
                if (coll_on) destroy();
            }
        }, 3 * 60 / bpm);*/
    }

    //called when coin disappears on its on (not collected)
    //returns true if destroyed
    public boolean destroy() {
        if (coll_on) {
            coll_on = false;
            Tween.to(actors, ActorTween.SCALE, 60 / bpm).target(2 - type * 0.6f)
                    .ease(Quad.IN).start(GameScreen.getTweenManager());

            actors.addAction(Actions.sequence(
                    Actions.fadeOut(60 / bpm),
                    Actions.delay(60 / bpm),
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            setDead(true);
                        }
                    })
            ));
            return true;
        } else return false;
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

    public void collect() {
        coll_on = false;
		collectSound.play();

        //make coin rotate faster after collecting for a pretty effect
		animation.setSpeed(3);

        //coin effect when collecting: first grows, then shrinks
        Timeline.createSequence()
        .push(Tween.to(actors, ActorTween.SCALE, 0.3f).target(actors.getScaleX()*1.2f, actors.getScaleX()*1.2f).ease(Circ.OUT))
                .push(Tween.to(actors, ActorTween.SCALE, 0.5f).target(0f, 0f).ease(Expo.IN))
                .start(GameScreen.getTweenManager()).setCallback(collectCallback);

        //show pretty coin particles
        Particles.create(getX(), getY(), "collect", PCOLOR);
	}

	public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

}
