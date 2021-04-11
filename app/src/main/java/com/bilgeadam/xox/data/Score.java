package com.bilgeadam.xox.data;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class keep track of scores
 */
public class Score {
    private final Integer id;
    private final String name;
    private final float score;

    /**
     * This constructor is for reading scores
     * @param id Id of the entry
     * @param name name of the player
     * @param score score of the player
     */
    public Score(int id, String name, float score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    /**
     * This constructor is for adding scores to database
     * @param name Name of the player
     * @param score Score of the player
     */
    protected Score(String name, float score) {
        this.name = name;
        this.score = score;
        this.id = null;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getScore() {
        return score;
    }

    protected JSONObject toJson(){
        JSONObject object = new JSONObject();
        try {
            object.put("name", getName());
            object.put("score", getScore());
        } catch (JSONException e){
            Log.e(this.getClass().getSimpleName(), "Cannot convert to JSon Object", e);
        }
        return object;
    }
}
