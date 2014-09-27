package co.bravebunny.circular.managers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;

import co.bravebunny.circular.objects.single.Ship;

public class GameInput extends InputAdapter implements InputProcessor{
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Ship.moveDown();
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Ship.moveUp();
		return false;
	}
}
