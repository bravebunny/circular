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
	Label label = new Label("Sample Text", Assets.skin);
	
	public TextBlock() {
		body = new Table();
		((Table)body).add(label);
	    Play.layerHUD.addActor(body);
	    ((Table)body).setTransform(true);
	}
	
	public void setText(String text) {
		label.setText(text);
	}
	
	public void moveTo(float target) {
    	Tween.to(body, ActorTween.POSITION, 0.5f)
        .target(target).ease(Back.OUT)
        .start(GameScreen.getTweenManager());
	}

	@Override
	public void dispose() {
		label.remove();
		body.remove();
	}
}
