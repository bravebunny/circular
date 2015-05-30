package co.bravebunny.circular.entities.objects;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Back;
import co.bravebunny.circular.managers.ActorTween;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.managers.Positions;
import co.bravebunny.circular.screens.GameScreen;

public class HUD extends GameObject {
	public Image restart;
	public Array<Combo> combos = new Array<Combo>();


    private boolean isRestarting = false;

    public void init() {
		restart = Assets.getImage("level/hud_restart");
		actors.addActor(restart);
		Positions.setPolarPosition(actors);
	    restart.setScale(0, 0);
	    restart.setVisible(false);

	}
	
	public void render(float delta) {
		restart.rotateBy(delta*30);
	}
	
	//Grows the restart symbol into sight
	public void restartShow() {
        isRestarting = false;
        restart.setVisible(true);
		Tween.to(restart, ActorTween.SCALE, 0.5f)
        .target(1.0f).ease(Back.OUT).delay(0.5f)
        .start(GameScreen.getTweenManager());
		
	}
	
	//Shrinks the restart symbol back 
	public void restartHide() {
        if (!isRestarting) {
            isRestarting = true;
            Tween.to(restart, ActorTween.SCALE, 0.5f)
                    .target(0.0f).ease(Back.IN)
                    .start(GameScreen.getTweenManager());

            Timer.schedule(new Task() {
                @Override
                public void run() {
                    restart.setVisible(false);
                }
            }, 60 / 0.5f);
        }
    }

	@Override
	public void dispose() {
		restart.remove();
        for (Combo c : combos) {
            c.dispose();
        }
    }
	
}



