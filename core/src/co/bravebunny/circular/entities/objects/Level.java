package co.bravebunny.circular.entities.objects;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Back;

import com.badlogic.gdx.scenes.scene2d.Group;

import co.bravebunny.circular.entities.objects.Clickable;
import co.bravebunny.circular.entities.objects.TextBlock;
import co.bravebunny.circular.managers.ActorTween;
import co.bravebunny.circular.managers.Positions;
import co.bravebunny.circular.screens.GameScreen;

/**
 * Container for all the information related to a game level
 */
public class Level extends Clickable {
	TextBlock nameText;
	String musicFile, artist, linkText, url;
	int minTotalScore;
	float bpm;

	/**
	 * @param levelName - Title to display on the level select menu
	 * @param mintotalScore - Total score required to unlock this level
	 */
	public Level (String levelName, int minTotalScore) {
		this.nameText = new TextBlock();
		this.nameText.setText(levelName);
		this.minTotalScore = minTotalScore;
	}
	
	public String getMusicFile() {
		return musicFile;
	}

	public void setMusicFile(String musicFile) {
		this.musicFile = musicFile;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getLinkText() {
		return linkText;
	}

	public void setLinkText(String linkText) {
		this.linkText = linkText;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public float getBpm() {
		return bpm;
	}

	public void setBpm(float bpm) {
		this.bpm = bpm;
	}
	
	@Override
	public void setPosition(float x, float y) {
		nameText.setPosition(x, y);
	}

	@Override
	public void setRotation(float angle) {
		nameText.setRotation(angle);
	}
	
	@Override
	public void setLayer(Group layer) {
		nameText.setLayer(layer);
	}
	
	@Override
	public void setPolarPosition(float radius, float angle) {
		nameText.setPolarPosition(radius, angle);
	}

	@Override
	public void dispose() {
		nameText.dispose();
	}
	
	@Override
	public float getRotation() {
		return nameText.getRotation();
	}
	
	@Override
	public float getX() {
		return nameText.getX();
	}
	
	@Override
	public float getY() {
		return nameText.getY();
	}
	
	public void moveTo(float target) {
    	nameText.moveTo(target);
	}
}
