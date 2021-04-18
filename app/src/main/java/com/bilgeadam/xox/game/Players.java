package com.bilgeadam.xox.game;

import androidx.annotation.NonNull;
import com.bilgeadam.xox.R;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public enum Players {
    X(1, R.drawable.x),
    O(2, R.drawable.o);

    private final int value, drawable;
    private String name;

    private int currentTurn;
    private Float score;
    private long totalTurnTime, turnStartTime;

    Players(int value, int drawable) {
        this.value = value;
        this.drawable = drawable;
        resetParameters(null);
    }

    protected int getValue() {
        return value;
    }

    public int getDrawable() {
        return drawable;
    }

    protected Players getNextPlayer(){
        return this.ordinal() == X.ordinal() ? O : X;
    }

    protected void incrementTurn(){
        this.currentTurn++;
    }

    protected void resetParameters(String name){
        currentTurn = 1;
        score = 0F;
        totalTurnTime = 0L;
        this.name = name;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    protected void setScore(Float score) {
        this.score = score;
    }

    public Float getScore() {
        return score;
    }

    protected void startTurnTime(){
        turnStartTime = new Date().getTime();
    }

    protected void endTurnTime(){
        totalTurnTime += new Date().getTime() - turnStartTime;
    }

    protected long getTotalTurnTime() {
        return totalTurnTime;
    }

    public String getName() {
        return name == null ? name() : name;
    }

    /**
     *
     * @return random player
     */
    static Players generateRandomPlayer(){
        Players[] players = Players.values();

        return players[(int) Math.round(Math.random() * (players.length - 1))];
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return name == null ? name() : String.format("%s (%s)", name(), this.name);
    }
}
