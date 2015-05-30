package co.bravebunny.circular.entities.objects;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Back;
import co.bravebunny.circular.managers.ActorTween;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.managers.Positions;
import co.bravebunny.circular.screens.GameScreen;

public class Circle extends Clickable {
	
	private Image circleOuter;
	private Image circleInner;

    private boolean isShrinking = false;

    public void init() {
        System.out.println("---CREATED AN ENEMY---");
        circleOuter = Assets.getImage("level/circle_outer");
        circleInner = Assets.getImage("level/circle_inner");
        actors.addActor(circleOuter);
        actors.addActor(circleInner);
        Positions.setPolarPosition(actors);

        click_height = click_width = actors.getWidth();

        //beat();
    }

    public void setLayer(Group layer) {
		layer.addActor(circleOuter);
	    layer.addActor(actors);
	}
	
	public void render(float delta) {
		//nothin here brotha
	}
	
	/**
	 * Triggers rhythm-related visual effects in the circle
	 */
	public void beat(float bpm) {
        Tween.from(circleOuter, ActorTween.SCALE, 60/bpm)
        .target(1.1f).ease(Back.OUT)
        .start(GameScreen.getTweenManager());
        
        Tween.from(circleInner, ActorTween.SCALE, 60/bpm)
        .target(0.9f).ease(Back.OUT)
        .start(GameScreen.getTweenManager());
	}
	
	/**
	 * Expands inner circle until it covers a specified point (x, y)
	 */
	public void growToCover(float x, float y, Group layerGame, Group layerOverlay) {
		layerGame.removeActor(circleInner);
		layerOverlay.addActor(circleInner);

		
		float target = Positions.getDistance(circleInner, x, y)
				/ (circleInner.getWidth());
		
		Tween.to(circleInner, ActorTween.SCALE, 0.5f)
        .target(target).ease(Back.IN)
        .start(GameScreen.getTweenManager());
	}
	
	/**
	 * Makes the inner circle shrink back to its default size
	 */
	public void shrink(final Group layerGame, final Group layerOverlay) {
        if (!isShrinking) {
            isShrinking = true;
            Tween.to(circleInner, ActorTween.SCALE, 0.5f)
                    .target(1).ease(Back.IN)
                    .start(GameScreen.getTweenManager());
            Timer.schedule(new Task() {
                @Override
                public void run() {
                    isShrinking = false;
                    layerOverlay.removeActor(circleInner);
                    layerGame.addActor(circleInner);
                }
            }, 0.5f);
        }


		
	}

}
