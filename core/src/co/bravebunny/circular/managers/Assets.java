package co.bravebunny.circular.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

import co.bravebunny.circular.entities.objects.AnimatedImage;

public class Assets {
    public static AssetManager manager = new AssetManager();
    public static Skin skin;

    // In here we'll put everything that needs to be loaded in this format:
    // manager.load("file location in assets", fileType.class);
    // 
    // libGDX AssetManager currently supports: Pixmap, Texture, BitmapFont,
    //     TextureAtlas, TiledAtlas, TiledMapRenderer, Music and Sound.
    public static void queueLoading() {
        manager.load("img/pack.atlas", TextureAtlas.class);
        manager.load("media/sfx/coin.ogg", Sound.class);
        manager.load("media/sfx/combo.ogg", Sound.class);
        manager.load("media/sfx/combobreak.ogg", Sound.class);
        manager.load("media/sfx/destroy.ogg", Sound.class);
        manager.load("media/sfx/move1.ogg", Sound.class);
        manager.load("media/sfx/move2.ogg", Sound.class);
        manager.load("media/sfx/explosion.ogg", Sound.class);
        
        manager.load("media/music/menu.ogg", Music.class);
    }

    //In here we'll create our skin, so we only have to create it once.
    public static void setMenuSkin() {
        if (skin == null)
        	skin = new Skin(Gdx.files.internal("img/skin.json"),
                    manager.get("img/pack.atlas", TextureAtlas.class));
    }
    // This function gets called every render() and the AssetManager pauses the loading each frame
    // so we can still run menus and loading screens smoothly
    public static boolean update() {
        return manager.update();
    }
    
    //Loads an image from the atlas
    public static Image getImage (String file) {
    	AtlasRegion region = skin.getAtlas().findRegion(file);
    	return new Image(region);
    }
    
    public static AnimatedImage getAnimation(String file) {
    	Array<AtlasRegion> regions = skin.getAtlas().findRegions(file);
    	return new AnimatedImage(new Animation(0.06f, regions));
        
        //all aboard the animation train
        //choo-choo
    }
    
    //Loads a sound. input string examples:
    // "sfx/move1"
    // "music/music1"
    public static Sound getSound (String file) {
    	return manager.get("media/sfx/" + file + ".ogg", Sound.class);
    }
    
    public static Music getMusic (String file) {
    	return manager.get("media/music/" + file + ".ogg", Music.class);
    }
    
    public static void loadMusic (int level) {
    	manager.load("media/music/menu.ogg", Music.class);
    }
}