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
	TextBlock levelText, score, description, linkText;
	String musicFile, title, artist, url;
	int minTotalScore;
	float bpm;

	public void init() {
		levelText = new TextBlock();
        levelText.setPosition(0, 50);
        score = new TextBlock();
        score.setPosition(0, -100);
        score.setScale(0.8f);
        description = new TextBlock();
        description.setPosition(0, -400);
        description.setScale(0.4f);
        linkText = new TextBlock();
        linkText.setPosition(0, -460);
        linkText.setScale(0.4f);
	}
	
	public void setLevelName(String levelName) {
		this.levelText.setText(levelName);
	}

    public void setScore(int score) {
        this.score.setText("" + score);
    }
	
	public void setMinTotalScore(int minTotalScore) {
		this.minTotalScore = minTotalScore;
	}

	public int getMinTotalScore() {
		return minTotalScore;
	}

	public String getMusicFile() {
		return musicFile;
	}

	public String getArtist() {
		return artist;
	}

	public String getUrl() {
		return url;
	}

	public float getBpm() {
		return bpm;
	}
	
	@Override
	public void setPosition(float x, float y) {
		levelText.setPosition(x, levelText.getY());
        score.setPosition(x, score.getY());
        description.setPosition(x, description.getY());
        linkText.setPosition(x, linkText.getY());
	}

	@Override
	public void setRotation(float angle) {
		levelText.setRotation(angle);
	}
	
	@Override
	public void setLayer(Group layer) {
		levelText.setLayer(layer);
        score.setLayer(layer);
        description.setLayer(layer);
        linkText.setLayer(layer);
	}

	@Override
	public void dispose() {
		levelText.dispose();
        score.dispose();
        description.dispose();
        linkText.dispose();
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
        score.moveTo(target);
        description.moveTo(target);
        linkText.moveTo(target);
	}

	@Override
	public void write(Json json) {
		json.writeValue("level", levelText.getText());
        json.writeValue("min_score", minTotalScore);
		json.writeValue("music", musicFile);
		json.writeValue("bpm", bpm);
		json.writeValue("title", title);
		json.writeValue("artist", artist);
		json.writeValue("link_text", linkText.getText());
		json.writeValue("url", url);
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
        levelText.setText(jsonData.getString("level"));
        minTotalScore = jsonData.getInt("min_score");
		musicFile = jsonData.getString("music");
		bpm = jsonData.getInt("bpm");
		title = jsonData.getString("title");
		artist = jsonData.getString("artist");
		linkText.setText(jsonData.getString("link_text"));
        url = jsonData.getString("url");
        description.setText(title + " by " + artist);
	}
}
