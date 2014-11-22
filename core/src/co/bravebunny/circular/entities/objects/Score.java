package co.bravebunny.circular.entities.objects;

import aurelienribon.tweenengine.Tween;
import co.bravebunny.circular.managers.ActorTween;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.managers.Positions;
import co.bravebunny.circular.screens.GameScreen;
import co.bravebunny.circular.screens.Play;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Score {
	static Table table = new Table();
	static Label label = new Label("0", Assets.skin);
	static float angleVariation = 10;
	
	public static void show() {
		Positions.setPolarPosition(table);
		table.add(label);
		//table.debug();
	    Play.layerHUD.addActor(table);
	    //Assets.skin.getFont("font144").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    table.setTransform(true);
	    
	    table.setRotation(-angleVariation);

	    Tween.from(table, ActorTween.ANGLE, 60/Play.getBPM())
        .target(angleVariation).repeatYoyo(Tween.INFINITY, 0)
        .start(GameScreen.getTweenManager());
	    
	}
	
	//add a value to the beat counter
	public static void inc(int value) {
		Play.score += value;
		label.setText(Integer.toString(Play.score));
	}
	public static void inc() {
		inc(1);
	}
	
	public static void render(float delta) {
		
	}
}
