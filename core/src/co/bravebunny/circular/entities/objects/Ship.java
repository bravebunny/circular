package co.bravebunny.circular.entities.objects;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Elastic;
import co.bravebunny.circular.managers.ActorTween;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.managers.Particles;
import co.bravebunny.circular.managers.Positions;
import co.bravebunny.circular.screens.GameScreen;
import co.bravebunny.circular.screens.Play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Ship extends Solid {
	//constants
	String PCOLOR = "FFDE00FF";	//the color of the explosion particles
	
	//assets
	private Image fire, body;
	private Sound moveSFX_1, moveSFX_2, explosionSFX;
	
	//values
	float radius = 400;		//radius of the circular trajectory of the ship
	float colRadius = 60;	//radius of the collision circle
	public ShipState state = ShipState.DEAD;
	
	
	public void init() {
		fire = Assets.getImage("level/ship_fire1");
		body = Assets.getImage("level/ship_body");
		
		moveSFX_1 = Assets.getSound("move1");
		moveSFX_2 = Assets.getSound("move2");
		explosionSFX = Assets.getSound("explosion");
		
		actors.addActor(body);
		
		actors.setX(GameScreen.getViewport().getWorldWidth()/2 - actors.getWidth()/2);
		actors.setY(GameScreen.getViewport().getWorldHeight()/2 + 350);
        
        fire.setX(-100);
        fire.setY(0);
        
        centerActors();
        //Common.getStage().addActor(fire);
	}
	
	public static enum ShipState
	{
	    ALIVE, 
	    DEAD,
	}
	
	public void moveDown () {
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
		Particles.create(getX(), getY(), PCOLOR);
		state = ShipState.DEAD;
		actors.setVisible(false);
	}
	
	public void reset() {
		state = ShipState.ALIVE;
		actors.setVisible(true);
	}
	
	public boolean collidesWith(Solid object) {
		if (Positions.getDistance(actors, object.getX(), object.getY()) < (colRadius + object.radius)) {
			return object.actors.isVisible();
		}
		return false;
	}
	
	public void renderAlive (float delta) {
		actors.rotateBy(-3*60*delta);
        //set the rotation/scaling origin of the ship the the center of the screen
		//body.setOrigin(body.getWidth()/2, -(body.getY() - Common.getViewport().getWorldHeight()/2));
		
		Positions.setPolarPosition(actors, radius*actors.getScaleX(), actors.getRotation() + 90);
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
}
