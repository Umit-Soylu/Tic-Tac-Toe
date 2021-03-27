package com.bilgeadam.xox.game;

import java.io.Serializable;

public class Logic implements Serializable{

    public String getCurrentPlayer(){
        return "X";
    }

    public Integer getCurrentTurn(){
        return 5;
    }
}
