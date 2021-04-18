package com.bilgeadam.xox.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.bilgeadam.xox.R;
import com.bilgeadam.xox.data.Score;
import com.bilgeadam.xox.data.ScoreCommunicator;
import com.bilgeadam.xox.fragments.ScoreBoard;
import com.bilgeadam.xox.fragments.ScoreResult;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class ScoreActivity extends FragmentActivity {
    private String playerName;
    private Float score;
    private Boolean isFragmentCalled;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isFragmentCalled = false;

        // Get values from Game activity
        score = getIntent().getFloatExtra(GameActivity.SCORE_KEY, -1000F);
        playerName = getIntent().getStringExtra(GameActivity.PLAYER_KEY);
        Log.v(this.getClass().getSimpleName(), String.format("Acquired score is %.4f, player name is %s", score, playerName));

        setContentView(R.layout.activity_scoreboard);

        if (score == -1000F || playerName == null){
//            findViewById(R.id.score_board).setVisibility(View.GONE);
            generateDrawCase();
            Log.v(this.getClass().getSimpleName(), "Game ended in a draw.");
        } else {
            // Check for Internet permission
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PERMISSION_GRANTED)
                requestPermissions(new String[]{Manifest.permission.INTERNET}, PermissionRequestConfig.INTERNET.ordinal());
            else
                processScores();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionRequestConfig.INTERNET.ordinal()) {
            if (grantResults[0] == PERMISSION_GRANTED)
                processScores();
            else
               setEmptyScoreFragment();
        } else
            Log.d(this.getClass().getSimpleName(), String.format("Unknown onRequestPermissionsResult for %d", requestCode));
    }

    private void processScores(){
        ScoreCommunicator scoreCommunicator = ScoreCommunicator.getInstance(getApplicationContext());
        scoreCommunicator.processScoreAndGetResults(playerName, score,this);
    }

    private void generateDrawCase(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.score_board, new ScoreResult());
        transaction.commit();
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

    /**
     * Generates score fragment only with current player name & score.
     */
    public void setEmptyScoreFragment(){
        List<Score> score = new ArrayList<>();
        score.add(new Score(1, playerName, this.score));

        setScoreFragment(score);
    }

    /**
     * Generates score fragment with values.
     * @param scoreList Score list acquired from database
     */
    public void setScoreFragment(List<Score> scoreList) {
        if(!isFragmentCalled) {
            scoreList.forEach(e -> Log.v(this.getClass().getSimpleName(), String.format("Score id:%d, name:%s, score:%f", e.getId(), e.getName(), e.getScore())));

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.score_board, new ScoreBoard(scoreList));
            transaction.commit();

            isFragmentCalled = true;
        }
    }
}
