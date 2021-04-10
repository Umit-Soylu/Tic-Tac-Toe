package com.bilgeadam.xox.game;

import android.util.Log;

import java.io.Serializable;

public class Logic implements Serializable{

    private final int[][] board; //0->Empty; 1->X; 2->O
    private final ScoreCalculator scoreGenerator;

    private Players currentPlayer;

    public Logic() {
        this.board = new int[3][3];
        currentPlayer = Players.generateRandomPlayer();
        currentPlayer.startTurnTime();
        scoreGenerator = new ScoreCalculator(board.length);
        Players.X.resetParameters();
        Players.O.resetParameters();
    }

    /**
     * Process current turn to decide next action
     * @param x Horizontal location clicked
     * @param y Vertical location clicked
     * @return The state of the game
     */
    public GameState processTurn(int x, int y){
        Log.v(this.getClass().getSimpleName(), String.format("Process turn for x:%d y:%d", x, y));

        board[x][y] = currentPlayer.getValue();
        currentPlayer.endTurnTime();

        // Check if someone win
        if (currentPlayer.getCurrentTurn() >= board.length &&
                (checkStraight(x, 0, true) || checkStraight(0, y, false) ||
                        checkCross(0, 0, true) || checkCross(board.length-1, 0, false))){
            currentPlayer.setScore(scoreGenerator.generateScore(currentPlayer.getCurrentTurn(), currentPlayer.getTotalTurnTime()));
            return GameState.WIN;
        }

        // Check for draw
        if (Players.X.getCurrentTurn() + Players.O.getCurrentTurn() > board.length * board[0].length)
            return GameState.DRAW;

        // Game continues
        currentPlayer.incrementTurn();
        currentPlayer = currentPlayer.getNextPlayer();
        currentPlayer.startTurnTime();
        return GameState.CONTINUE;
    }

    private boolean checkStraight(int row, int column, final boolean isRowCheck){
        if (row >= board.length || column >= board.length)
            return true;
        else if (board[row][column] != currentPlayer.getValue())
            return false;
        else
            return isRowCheck ? checkStraight(row, column+1, true) : checkStraight(row+1, column, false);
    }

    private boolean checkCross(int row, int column, final boolean isSameElementCheck){
        if (row >= board.length || column >= board.length)
            return true;
        else if (board[row][column] != currentPlayer.getValue())
            return false;
        else
            return isSameElementCheck ? checkCross(row+1, column+1, true) : checkCross(row-1, column+1, false);
    }

    public Players getCurrentPlayer(){
        return currentPlayer;
    }
}
