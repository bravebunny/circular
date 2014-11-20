package co.bravebunny.circular;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import co.bravebunny.circular.screens.*;

public class Circular extends Game {
	public static final String TITLE = "Circular";
	
	@Override
	public void create () {
		Gdx.input.setCatchBackKey(true);
		setScreen(new Splash());
	}
	
	public enum State {
	    PAUSE,
	    RUN,
	    RESUME,
	    STOPPED
	}
	
	public enum CurrentScreen {
		SPLASH,
		MENU,
		LEVEL
	}

}
