package co.bravebunny.circular.entities.objects;

import aurelienribon.tweenengine.Tween;
import co.bravebunny.circular.managers.ActorTween;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.managers.Positions;
import co.bravebunny.circular.screens.GameScreen;
import co.bravebunny.circular.screens.Play;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Score extends GameObject {
	Table table;
	Label label;
	float angleVariation = 10;
	
	public void init() {
		table = new Table();
		label = new Label("0", Assets.skin);
		table.add(label);
		actors.addActor(table);
		table.setTransform(true);
	}
	
	public void startTween(float bpm) {
		table.setRotation(-angleVariation);
	    Tween.to(table, ActorTween.ANGLE, 60/bpm)
        .target(angleVariation).repeatYoyo(Tween.INFINITY, 0)
        .start(GameScreen.getTweenManager());
	}
	
	//add a value to the beat counter
	public void inc(int value) {
		Play.score += value;
		label.setText(Integer.toString(Play.score));
	}
	public void inc() {
		inc(1);
	}
	public void reset() {
		Play.score = 0;
		label.setText(Integer.toString(Play.score));
	}
	
	public void render(float delta) {
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
