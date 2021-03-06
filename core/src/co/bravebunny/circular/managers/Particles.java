package co.bravebunny.circular.managers;

import co.bravebunny.circular.screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class Particles {
	
	static private SpriteBatch batch;
	static private ParticleEffect prototype;
	static private ParticleEffectPool pool;
	static private Array<PooledEffect> effects;
	/**
	 * Creates a particle explosion effect at the given coordinates
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param effect - name of the effect file (assets/effects directory)
	 * 					to use, without the .p extension
	 * @param tint - color to tint the particles with (#RGBA)
	 */
	public static void create(float x, float y, String effect, String tint) {
		
		Color color = Color.valueOf(tint);
		float[] rgb = {(float)color.r, (float)color.g, (float)color.b};
		
		prototype = new ParticleEffect();
		prototype.load(Gdx.files.internal("effects/" + effect + ".p"), Gdx.files.internal("img"));
		prototype.getEmitters().get(0).getTint().setColors(rgb);
		pool = new ParticleEffectPool(prototype, 0, 70);
		
		prototype.start();
		
		PooledEffect pooledEffect = pool.obtain();
        Vector2 position = new Vector2(x, y).rotate(-GameScreen.getAngle());
        float effectX = GameScreen.getViewport().getWorldWidth()/2 + position.x;
        float effectY = GameScreen.getViewport().getWorldHeight()/2 + position.y;
		pooledEffect.setPosition(effectX, effectY);
		effects.add(pooledEffect);
	}

	public static void render(float delta) {
		batch.getProjectionMatrix().setToOrtho2D(0, 0, GameScreen.getViewport().getWorldWidth(), GameScreen.getViewport().getWorldHeight());
		batch.begin();
		for(PooledEffect effect : effects) {
			effect.draw(batch, delta);
			if(effect.isComplete()) {
				effects.removeValue(effect, true);
				effect.free();
			}
		}
		batch.end();
	}
	
	public static void show() {
		batch = new SpriteBatch();
		effects = new Array<PooledEffect>();
	}
	
	public static void dispose() {
		if(batch != null) {
			batch.dispose();
		}
		if (prototype != null) {
			prototype.dispose();
		}
		
	}
}
