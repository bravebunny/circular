package co.bravebunny.circular.entities.objects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import co.bravebunny.circular.managers.Assets;

public class Combo extends GameObject {
    private Image body[];
    private Sound comboSFX, breakSFX;

    private int multiplier = 1;
    private int counter;

    @Override
    public void init() {
        comboSFX = Assets.getSound("combo");
        breakSFX = Assets.getSound("combobreak");
        for (int i = 0; i <= 5; i++) {
            //body[i] = Assets.getImage("level/combo_x" + i);
            //actors.addActor(body[i]);
        }
    }

    //sets combo counter back to zero
    public void reset() {
        if (counter + multiplier > 0) {
            breakSFX.play();
            counter  = 0;
            multiplier = 0;
        }
        System.out.println("Combo reset");
    }

    //called every time a coin is connected
    public void incCounter() {
        if (counter >= multiplier * 4) incMultiplier();
        else counter++;
        System.out.println("Combo counter: " + counter);
    }

    //advances to the next combo level
    public void incMultiplier() {
        comboSFX.play();
        multiplier++;
        counter = 0;
        System.out.println("Combo multiplier: " + multiplier);
    }
}
