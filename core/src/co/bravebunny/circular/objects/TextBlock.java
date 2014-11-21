package co.bravebunny.circular.objects;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Back;
import co.bravebunny.circular.managers.ActorAccessor;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.screens.Common;
import co.bravebunny.circular.screens.Level;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class TextBlock extends GameObject {
	Label label = new Label("Sample Text", Assets.skin);
	
	public TextBlock() {
		body = new Table();
		((Table)body).add(label);
	    Level.layerHUD.addActor(body);
	    ((Table)body).setTransform(true);
	}
	
	public void setText(String text) {
		label.setText(text);
	}
	
	public void moveTo(float target) {
    	Tween.to(body, ActorAccessor.POSITION, 0.5f)
        .target(target).ease(Back.OUT)
        .start(Common.getTweenManager());
	}

	@Override
	public void dispose() {
		label.remove();
		body.remove();
	}
}
