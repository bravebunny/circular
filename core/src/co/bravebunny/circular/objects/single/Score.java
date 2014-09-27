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
	Table table = new Table();
	Label label = new Label("45", Assets.skin);
	float angleVariation = 10;
	
	public void show() {
		Positions.setPolarPosition(table);
		table.add(label);
		//table.debug();
	    Common.getStage().addActor(table);
	    //Assets.skin.getFont("font144").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    table.setTransform(true);
	    
	    table.setRotation(-angleVariation);

	    Tween.from(table, ActorAccessor.ANGLE, 60/Level.getBPM())
        .target(angleVariation).repeatYoyo(Tween.INFINITY, 0)
        .start(Common.getTweenManager());
	    
	}
	
	public void render(float delta) {

	}
}
