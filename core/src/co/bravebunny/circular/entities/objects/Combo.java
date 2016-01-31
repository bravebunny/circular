package co.bravebunny.circular.entities.objects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Back;
import aurelienribon.tweenengine.equations.Linear;
import co.bravebunny.circular.managers.ActorTween;
import co.bravebunny.circular.managers.Assets;
import co.bravebunny.circular.managers.Positions;
import co.bravebunny.circular.screens.GameScreen;

public class Combo extends GameObject {
    private Array<Array<Image>> body;
    private Sound comboSFX, breakSFX;

    private int multiplier = 1;
    private int counter;
    private float bpm;

    @Override
    public void init() {
        body = new Array<Array<Image>>();
        comboSFX = Assets.getSound("combo");
        breakSFX = Assets.getSound("combobreak");
        for (int i = 0; i < 5; i++) {
            body.add(new Array<Image>());
            for (int e = 1; e <= (i+1) * 4; e++) {
                Image part = Assets.getImage("level/combo_x" + (i+1));
                part.setScale(0);
                Color c = part.getColor();
                part.setColor(c.r, c.g, c.b, 0.7f);
                part.setRotation(-(360 / ((i + 1) * 4)) * (e - 1));
                actors.addActor(part);
                body.get(i).add(part);
            }
        }
    }

    public void setBPM(float bpm) {
        this.bpm = bpm;
    }

    public void render(float delta) {
        for (Image part : body.get(multiplier-1)) {
            float angle = 90 - (360 / (multiplier * 8)) + part.getRotation();
            Positions.setPolarPosition(part, 118 * part.getScaleX(), angle);
        }
    }

    //sets combo counter back to zero
    public void reset() {
        if (counter > 0 || multiplier > 1) {
            hideVisible();
            breakSFX.play();
            counter  = 0;
            multiplier = 1;
            System.out.println("Combo reset");
        }
    }

    //called every time a coin is connected
    public void incCounter() {
        if (counter >= multiplier * 4) incMultiplier();

            Image part = body.get(multiplier-1).get(counter);
            //TODO bpm here
            Tween.to(part, ActorTween.SCALE, 60/bpm).target(1).delay(90/bpm)
                    .ease(Back.OUT).start(GameScreen.getTweenManager());
            counter++;

        System.out.println("Combo counter: " + counter);
    }

    //advances to the next combo level
    public void incMultiplier() {
        hideVisible();
        comboSFX.play();
        multiplier++;
        counter = 0;
        System.out.println("Combo multiplier: " + multiplier);
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void hideVisible() {
        Array<Image> parts = body.get(multiplier - 1);
        for (int i = 0; i < parts.size; i++) {
            Image part = parts.get(i);
            Tween.to(part, ActorTween.SCALE, 60/bpm).target(0).delay(i*0.1f)
                    .ease(Back.IN).start(GameScreen.getTweenManager());
        }
    }
}
