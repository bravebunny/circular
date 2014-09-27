package co.bravebunny.circular.managers;

import co.bravebunny.circular.screens.Common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.utils.Array;

public class Particles {
	
	static private SpriteBatch batch;
	static private ParticleEffect prototype;
	static private ParticleEffectPool pool;
	static private Array<PooledEffect> effects;
	
	public static void create(float x, float y, String tint) {
		
		Color color = Color.valueOf(tint);
		float[] rgb = {(float)color.r, (float)color.g, (float)color.b};
		
		prototype = new ParticleEffect();
		prototype.load(Gdx.files.internal("effects/ship_explosion.p"), Gdx.files.internal("img"));
		prototype.getEmitters().get(0).getTint().setColors(rgb);
		pool = new ParticleEffectPool(prototype, 0, 70);
		
		prototype.start();
		
		PooledEffect effect = pool.obtain();
		effect.setPosition(Common.getViewport().getWorldWidth()/2 + x, Common.getViewport().getWorldHeight()/2 + y);
		effects.add(effect);
	}
	
	public static void render(float delta) {
		batch.getProjectionMatrix().setToOrtho2D(0, 0, Common.getViewport().getWorldWidth(), Common.getViewport().getWorldHeight());
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
		batch.dispose();
		prototype.dispose();
	}
}
