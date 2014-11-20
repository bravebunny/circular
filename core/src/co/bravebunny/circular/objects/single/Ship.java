package co.bravebunny.circular.objects.single;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Elastic;
import co.bravebunny.circular.managers.ActorAccessor;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.managers.Particles;
import co.bravebunny.circular.managers.Positions;
import co.bravebunny.circular.objects.Solid;
import co.bravebunny.circular.screens.Common;
import co.bravebunny.circular.screens.Level;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Ship extends Solid {
	//constants
	String PCOLOR = "FFDE00FF";	//the color of the explosion particles
	
	//images
	private Image fire = Assets.getImage("level/ship_fire1");
	
	//sounds
	private Sound moveSFX_1 = Assets.getSound("move1");
	private Sound moveSFX_2 = Assets.getSound("move2");
	private Sound explosionSFX = Assets.getSound("explosion");
	
	//values
	float radius = 400;		//radius of the circular trajectory of the ship
	float colRadius = 60;	//radius of the collision circle
	public ShipState state = ShipState.DEAD;
	
	
	public Ship() {
		body = Assets.getImage("level/ship_body");
		
		body.setX(Common.getViewport().getWorldWidth()/2 - body.getWidth()/2);
		body.setY(Common.getViewport().getWorldHeight()/2 + 350);
        Level.layerShip.addActor(body);
        
        fire.setX(-100);
        fire.setY(0);
        //Common.getStage().addActor(fire);
	}
	
	public static enum ShipState
	{
	    ALIVE, 
	    DEAD,
	}
	
	public void moveDown () {
		moveSFX_1.play();
        Tween.to(body, ActorAccessor.SCALE, 0.8f)
        .target(0.60f, 0.60f).ease(Elastic.OUT)
        .start(Common.getTweenManager());
	}
	
	public void moveUp() {
		moveSFX_2.play();
        Tween.to(body, ActorAccessor.SCALE, 0.8f)
        .target(1f, 1f).ease(Elastic.OUT)
        .start(Common.getTweenManager());
	}
	
	public void destroy() {
		explosionSFX.play();
		Particles.create(getX(), getY(), PCOLOR);
		state = ShipState.DEAD;
		body.setVisible(false);
		HUD.restartShow();
	}
	
	public void reset() {
		state = ShipState.ALIVE;
		body.setVisible(true);
	}
	
	public boolean collidesWith(Solid object) {
		if (Positions.getDistance(body, object.getX(), object.getY()) < (colRadius + object.radius)) {
			return object.body.isVisible();
		}
		return false;
	}
	
	public void renderAlive (float delta) {
		body.rotateBy(-3*60*delta);
        //set the rotation/scaling origin of the ship the the center of the screen
		//body.setOrigin(body.getWidth()/2, -(body.getY() - Common.getViewport().getWorldHeight()/2));
		
		Positions.setPolarPosition(body, radius*body.getScaleX(), body.getRotation() + 90);
	}
	
	public void render (float delta) {
		switch (state) {
		case ALIVE:
			renderAlive(delta);
			break;
		case DEAD:
			break;
		}
	}

	@Override
	public void dispose() {
		// TODO BOM
		
	}
}
