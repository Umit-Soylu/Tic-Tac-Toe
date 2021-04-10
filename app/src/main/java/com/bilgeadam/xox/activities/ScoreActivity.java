package com.bilgeadam.xox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import com.bilgeadam.xox.R;

public class ScoreActivity extends FragmentActivity {
    private float score;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        score = getIntent().getFloatExtra(GameActivity.SCORE_KEY, -1000F);
        Log.v(this.getClass().getSimpleName(), String.format("Acquired score is %.4f", score));

        setContentView(R.layout.activity_scoreboard);

        if (score == -1000F){
            findViewById(R.id.score_board).setVisibility(View.GONE);
            Log.v(this.getClass().getSimpleName(), "Scoreboard is removed because there is no score to show");
        }
    }


    public void finishGame(View view){
        finishAffinity();
    }

    public void restartGame(View view){
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
