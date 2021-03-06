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

    public void processScoreAndGetResults(String name, float score, ScoreActivity activity){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URI,
                new Score(name, score).toJson(),
                listener -> {
                    getAllScores(activity);
                    Log.v(this.getClass().getSimpleName(), String.format("Player '%s' with score '%f' put to database", name, score));
                },
                error -> {
                    getAllScores(activity);
                    Log.e(this.getClass().getSimpleName(), String.format("Cannot put %s with score %f to database", name, score), error);
                });
        requestQueue.add(request);
    }

    private void getAllScores(ScoreActivity activity){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URI,
                null,
                listener -> {
                    Log.v(this.getClass().getSimpleName(), "Scores received.");
                    List<Score> scores = new ArrayList<>();
                    IntStream.range(0, listener.length()).
                        forEach(i -> {
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
                    activity.setScoreFragment(scores);
                },
                error -> {
                    Log.e(this.getClass().getSimpleName(), "Cannot read scores from database.", error);
                    activity.setEmptyScoreFragment();
                });
        requestQueue.add(request);
    }
}
