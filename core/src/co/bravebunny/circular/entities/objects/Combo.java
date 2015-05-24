package co.bravebunny.circular.entities.objects;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import co.bravebunny.circular.managers.Assets;

public class Combo extends GameObject {
    private Image body;
    private int multiplier;

    public Combo(int multiplier) {
        super();
        this.multiplier = multiplier;
    }

    @Override
    public void init() {
        body = Assets.getImage("level/combo_x" + multiplier);
        actors.addActor(body);
    }
}
