package com.bilgeadam.xox.game;

import android.util.Log;

import java.io.Serializable;

public class Logic implements Serializable{

    private final int[][] board; //0->Empty; 1->X; 2->O
    private Players currentPlayer;

    public Logic() {
        this.board = new int[3][3];
        currentPlayer = Players.generateRandomPlayer();
    }

    public boolean processTurn(int x, int y){
        Log.i(this.getClass().getSimpleName(), String.format("Process turn for x:%d y:%d", x, y));

        board[x][y] = currentPlayer.getValue();

        // TODO - Check game condition
        // Check if someone win
        if (false){
            return false;
        }

        // Check for draw
        if (Players.X.getCurrentTurn() + Players.O.getCurrentTurn() > board.length * board[0].length)
            return false;


        // Game continues
        currentPlayer.incrementTurn();
        currentPlayer = currentPlayer.getNextPlayer();
        return true;
    }

    public Players getCurrentPlayer(){
        return currentPlayer;
    }

}
