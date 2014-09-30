package co.bravebunny.circular.managers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import co.bravebunny.circular.objects.single.Circle;
import co.bravebunny.circular.objects.single.HUD;
import co.bravebunny.circular.objects.single.Ship;
import co.bravebunny.circular.screens.Common;
import co.bravebunny.circular.screens.Level;

public class GameInput extends InputAdapter implements InputProcessor{
	
	@Override
	public boolean touchDown(int screenX, int screenY, final int pointer, final int button) {
		switch (Ship.state)
        {
        case ALIVE:
        	Ship.moveDown();
            break;
        case DEAD:
        	Level.restart();
            break;
        }
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		switch (Ship.state)
        {
        case ALIVE:
        	Ship.moveUp();
            break;
        case DEAD:
        	
            break;
        }
		return false;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.BACK) {
			// Do your optional back button handling (show pause menu?)
		}
		return false;
	}
}
