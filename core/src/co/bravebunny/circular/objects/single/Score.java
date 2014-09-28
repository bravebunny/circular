package co.bravebunny.circular.objects.single;

import aurelienribon.tweenengine.Tween;
import co.bravebunny.circular.managers.ActorAccessor;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.managers.Positions;
import co.bravebunny.circular.screens.Common;
import co.bravebunny.circular.screens.Level;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Score {
	static Table table = new Table();
	static Label label = new Label("45", Assets.skin);
	static float angleVariation = 10;
	
	public static void show() {
		Positions.setPolarPosition(table);
		table.add(label);
		//table.debug();
	    Level.layerGUI.addActor(table);
	    //Assets.skin.getFont("font144").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    table.setTransform(true);
	    
	    table.setRotation(-angleVariation);

	    Tween.from(table, ActorAccessor.ANGLE, 60/Level.getBPM())
        .target(angleVariation).repeatYoyo(Tween.INFINITY, 0)
        .start(Common.getTweenManager());
	    
	}
	
	//add a value to the beat counter
	public static void inc(int value) {
		Level.score += value;
		label.setText(Integer.toString(Level.score));
	}
	public static void inc() {
		inc(1);
	}
	
	public static void render(float delta) {
		
	}
}
