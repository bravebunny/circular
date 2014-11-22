package co.bravebunny.circular.entities.objects;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Back;
import co.bravebunny.circular.managers.ActorTween;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.screens.GameScreen;
import co.bravebunny.circular.screens.Play;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class TextBlock extends GameObject {
	Label label;
	Table table;
	
	public void init() {
		label = new Label("Sample Text", Assets.skin);
		table = new Table();
		actors.addActor(table);
		table.add(label);
	    table.setTransform(true);
	}
	
	public void setText(String text) {
		label.setText(text);
	}
	
	public void moveTo(float target) {
    	Tween.to(actors, ActorTween.POSITION, 0.5f)
        .target(target).ease(Back.OUT)
        .start(GameScreen.getTweenManager());
	}

	@Override
	public void dispose() {
		label.remove();
		table.remove();
	}

	public String getText() {
		return label.getText().toString();
	}
}
