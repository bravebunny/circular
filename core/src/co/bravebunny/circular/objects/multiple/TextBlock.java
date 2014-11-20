package co.bravebunny.circular.objects.multiple;

import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.managers.Positions;
import co.bravebunny.circular.screens.Level;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class TextBlock {
	Table table = new Table();
	Label label = new Label("0", Assets.skin);
	
	public TextBlock() {
		Positions.setPolarPosition(table);
		table.add(label);
	    Level.layerHUD.addActor(table);
	    table.setTransform(true);
	    
	}
	
	public void setText(String text) {
		label.setText(text);
	}
}
