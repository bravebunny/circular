package co.bravebunny.circular.entities.objects;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

import co.bravebunny.circular.entities.objects.Clickable;
import co.bravebunny.circular.entities.objects.TextBlock;

/**
 * Container for all the information related to a game level
 */
public class Level extends Clickable implements Serializable{
	TextBlock levelText;
	String musicFile, title, artist, linkText, url;
	int minTotalScore;
	float bpm;

	/**
	 * @param levelName - Title to display on the level select menu
	 * @param mintotalScore - Total score required to unlock this level
	 */
	public void init() {
		this.levelText = new TextBlock();
	}
	
	public void setLevelName(String levelName) {
		this.levelText.setText(levelName);
	}
	
	public void setMinTotalScore(int minTotalScore) {
		this.minTotalScore = minTotalScore;
	}
	
	public String getMusicFile() {
		return musicFile;
	}

	public String getArtist() {
		return artist;
	}

	public String getLinkText() {
		return linkText;
	}

	public String getUrl() {
		return url;
	}

	public float getBpm() {
		return bpm;
	}
	
	@Override
	public void setPosition(float x, float y) {
		levelText.setPosition(x, y);
	}

	@Override
	public void setRotation(float angle) {
		levelText.setRotation(angle);
	}
	
	@Override
	public void setLayer(Group layer) {
		levelText.setLayer(layer);
	}
	
	@Override
	public void setPolarPosition(float radius, float angle) {
		levelText.setPolarPosition(radius, angle);
	}

	@Override
	public void dispose() {
		levelText.dispose();
	}
	
	@Override
	public float getRotation() {
		return levelText.getRotation();
	}
	
	@Override
	public float getX() {
		return levelText.getX();
	}
	
	@Override
	public float getY() {
		return levelText.getY();
	}
	
	public void moveTo(float target) {
    	levelText.moveTo(target);
	}

	@Override
	public void write(Json json) {
		json.writeValue("music", musicFile);
		json.writeValue("bpm", bpm);
		json.writeValue("title", title);
		json.writeValue("artist", artist);
		json.writeValue("link_text", linkText);
		json.writeValue("url", url);
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		musicFile = jsonData.getString("music");
		bpm = jsonData.getInt("bpm");
		title = jsonData.getString("title");
		artist = jsonData.getString("artist");
		linkText = jsonData.getString("link_text");
		url = jsonData.getString("url");

	}
}
