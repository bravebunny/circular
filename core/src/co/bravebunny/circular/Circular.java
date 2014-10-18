package co.bravebunny.circular;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import co.bravebunny.circular.screens.*;

/* old imports
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
*/

public class Circular extends Game {
	public static final String TITLE = "Circular";
	
	@Override
	public void create () {
		Gdx.input.setCatchBackKey(true);
		setScreen(new Splash());
	}
	
	public enum State
	{
	    PAUSE,
	    RUN,
	    RESUME,
	    STOPPED
	}

}
