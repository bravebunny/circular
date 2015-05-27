package co.bravebunny.circular.entities.objects;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import aurelienribon.tweenengine.Tween;
import co.bravebunny.circular.managers.ActorTween;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.screens.GameScreen;

public class Score extends GameObject {
	Table table;
	Label label;
	float angleVariation = 10;
	int score = 0;

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
		label.setText(Integer.toString(score));
	}

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
		score += value;
		label.setText(Integer.toString(score));
	}
	public void inc() {
		inc(1);
	}
	public void reset() {
		score = 0;
		label.setText(Integer.toString(score));
	}
	
	public void render(float delta) {
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
