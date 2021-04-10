package com.bilgeadam.xox.game;

import android.util.Log;

import java.io.Serializable;

class ScoreCalculator implements Serializable {
    private static final int MAX_TURN_POINT = 5, SHORTEST_TIME_POINT = 5;
    private static final long SHORTEST_GAME_LENGTH = 3000L;

    private final int minTurn;

    protected ScoreCalculator(int minTurn) {
        this.minTurn = minTurn;
    }

    protected float generateScore(int currentTurn, long totalTurnTime){
        float turnScore = (float) MAX_TURN_POINT * minTurn / currentTurn;
        float timeScore = (float) SHORTEST_GAME_LENGTH * SHORTEST_TIME_POINT / totalTurnTime;

        Log.v(this.getClass().getSimpleName(), String.format("Calculated turn point %f, calculated time point %f", turnScore, timeScore));

        return turnScore * timeScore;
    }
}
