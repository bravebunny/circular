package co.bravebunny.circular.entities.objects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Elastic;
import co.bravebunny.circular.managers.ActorTween;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.managers.Particles;
import co.bravebunny.circular.managers.Positions;
import co.bravebunny.circular.screens.GameScreen;

public class Ship extends Solid {
    public ShipState state = ShipState.DEAD;
    //constants
    String PCOLOR = "FFDE00FF";	//the color of the explosion particles
	//values
	float radius = 400;		//radius of the circular trajectory of the ship
	float colRadius = 60;	//radius of the collision circle
    //external values
    float bpm;
    //assets
    private Image fire, body;
    private Sound moveSFX_1, moveSFX_2, explosionSFX;

    public void setBPM(float bpm) {
        this.bpm = bpm;
    }

	public void init() {
		fire = Assets.getImage("level/ship_fire1");
		body = Assets.getImage("level/ship_body");

		moveSFX_1 = Assets.getSound("move1");
		moveSFX_2 = Assets.getSound("move2");
		explosionSFX = Assets.getSound("explosion");

        actors.addActor(fire);
        actors.addActor(body);

        fire.setX(-100);
        fire.setY(0);

        setVisibility(false);
    }

    public void moveDown() {
        moveSFX_1.play();
        Tween.to(actors, ActorTween.SCALE, 0.8f)
                .target(0.60f, 0.60f).ease(Elastic.OUT)
                .start(GameScreen.getTweenManager());
    }

    public void moveUp() {
		moveSFX_2.play();
        Tween.to(actors, ActorTween.SCALE, 0.8f)
        .target(1f, 1f).ease(Elastic.OUT)
        .start(GameScreen.getTweenManager());
	}
	
	public void destroy() {
		explosionSFX.play();
		Particles.create(getX(), getY(), "explosion", PCOLOR);
		state = ShipState.DEAD;
		setVisibility(false);
	}
	
	public void reset() {
		state = ShipState.ALIVE;
		setVisibility(true);
	}
	
	public boolean collidesWith(Solid object) {
		if (Positions.getDistance(actors, object.getX(), object.getY()) < (colRadius + object.coll_radius)) {
			return object.actors.isVisible();
		}
		return false;
	}
	
	public void renderAlive (float delta) {
        actors.rotateBy(-3 * 60 * (bpm / 100) * delta);
        //set the rotation/scaling origin of the ship the the center of the screen
		//body.setOrigin(body.getWidth()/2, -(body.getY() - Common.getViewport().getWorldHeight()/2));

		Positions.setPolarPosition(actors, radius*actors.getScaleX(), actors.getRotation() + 90);
	}
	
	public void render (float delta) {
		switch (state) {
		case ALIVE:
            setVisibility(true);
			renderAlive(delta);
			break;
		case DEAD:
            setVisibility(false);
			break;
		}
    }

    public enum ShipState {
        ALIVE,
        DEAD,
    }
}
