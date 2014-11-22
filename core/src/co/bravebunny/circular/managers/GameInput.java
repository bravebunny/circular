package co.bravebunny.circular.managers;

import co.bravebunny.circular.screens.GameScreen;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class GameInput extends InputAdapter implements InputProcessor{
	
	GameScreen screen;
	
	public GameInput(GameScreen common) {
		screen = common;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, final int pointer, final int button) {
		screen.touchDown(screenX, screenY);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		screen.touchUp(screenX, screenY);
		return false;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		
		if (keycode == Keys.BACK) {
			screen.backKey();
		}
		return false;
	}
}
