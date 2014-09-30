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

public class Ship {
	//constants
	static String PCOLOR = "FFDE00FF";	//the color of the explosion particles
	
	//images
	static private Image body = Assets.load("level/ship_body");
	static private Image fire = Assets.load("level/ship_fire1");
	
	//sounds
	static private Sound moveSFX_1 = Gdx.audio.newSound(Gdx.files.internal("media/sfx/move1.ogg"));
	static private Sound moveSFX_2 = Gdx.audio.newSound(Gdx.files.internal("media/sfx/move2.ogg"));
	static private Sound explosionSFX = Gdx.audio.newSound(Gdx.files.internal("media/sfx/explosion.ogg"));
	
	//values
	static float radius = 400;		//radius of the circular trajectory of the ship
	static float colRadius = 60;	//radius of the collision circle
	public static ShipState state = ShipState.ALIVE;
	
	
	public static enum ShipState
	{
	    ALIVE, 
	    DEAD,
	}
	
	public static Image getBody() {
		return body;
	}

	public static void setBody(Image body) {
		Ship.body = body;
	}

	public static float getRotation() {
		return getBody().getRotation();
	}
	
	public static float getX() {
		return body.getCenterX();
	}
	
	public static float getY() {
		return body.getCenterY();
	}
	
	public static void moveDown () {
		moveSFX_1.play();
        Tween.to(getBody(), ActorAccessor.SCALE, 0.8f)
        .target(0.60f, 0.60f).ease(Elastic.OUT)
        .start(Common.getTweenManager());
	}
	
	public static void moveUp() {
		moveSFX_2.play();
        Tween.to(getBody(), ActorAccessor.SCALE, 0.8f)
        .target(1f, 1f).ease(Elastic.OUT)
        .start(Common.getTweenManager());
	}
	
	public static void destroy() {
		explosionSFX.play();
		Particles.create(getX(), getY(), PCOLOR);
		state = ShipState.DEAD;
		body.setVisible(false);
		Circle.grow();
		HUD.restartShow();
	}
	
	public static void reset() {
		state = ShipState.ALIVE;
		body.setVisible(true);
	}
	
	public static boolean collidesWith(Solid object) {
		if (Positions.getDistance(getBody(), object.getActor()) < (colRadius + object.radius)) {
			return body.isVisible();
		}
		return false;
	}
	
	public static void renderAlive (float delta) {
		getBody().rotateBy(-3*60*delta);
        //set the rotation/scaling origin of the ship the the center of the screen
		//body.setOrigin(body.getWidth()/2, -(body.getY() - Common.getViewport().getWorldHeight()/2));
		
		Positions.setPolarPosition(getBody(), radius*getBody().getScaleX(), getBody().getRotation() + 90);
	}
	
	public static void render (float delta) {
		switch (state) {
		case ALIVE:
			renderAlive(delta);
			break;
		case DEAD:
			break;
		}
	}
	
	public static void show () {
        getBody().setX(Common.getViewport().getWorldWidth()/2 - getBody().getWidth()/2);
        getBody().setY(Common.getViewport().getWorldHeight()/2 + 350);
        Level.layerShip.addActor(getBody());
        
        fire.setX(-100);
        fire.setY(0);
        //Common.getStage().addActor(fire);
      
	}
}
