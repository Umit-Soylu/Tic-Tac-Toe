package com.bilgeadam.xox.data;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bilgeadam.xox.activities.ScoreActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.IntStream;

/**
 * This class reads & writes scores to database
 */
public class ScoreCommunicator {
    private static ScoreCommunicator INSTANCE;
    private static final String URI = String.format("http://%s:8080/score", "192.168.1.81");

    private final RequestQueue requestQueue;

    public static synchronized ScoreCommunicator getInstance(Context context){
        if (INSTANCE == null)
            INSTANCE = new ScoreCommunicator(context);

        return INSTANCE;
    }

    private ScoreCommunicator(Context context){
        requestQueue = Volley.newRequestQueue(context);
    }

    public void save(String name, Float score){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URI,
                new Score(name, score).toJson(),
                listener -> Log.v(this.getClass().getSimpleName(), String.format("Player '%s' with score '%f' put to database", name, score)),
                error -> Log.e(this.getClass().getSimpleName(), String.format("Cannot put %s with score %f to database", name, score), error));
        requestQueue.add(request);
    }

    public void getAllScores(ScoreActivity activity){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URI,
                null,
                listener -> {
                    List<Score> scores = new ArrayList<>();
                    IntStream.range(0, listener.length()).
                        forEach(i-> {
                            try {
                                JSONObject object = listener.getJSONObject(i);
                                scores.add(
                                        new Score(object.getInt("id"),
                                                object.optString("name"),
                                                (float) object.getDouble("score")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                    activity.setScoreList(scores);
                },
                error -> Log.e(this.getClass().getSimpleName(), "Cannot read scores from database", error));

        requestQueue.add(request);
    }
}
