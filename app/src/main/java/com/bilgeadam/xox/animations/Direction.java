package com.bilgeadam.xox.animations;

enum Direction {
    Left,
    Right,
    Up,
    Bottom;

    static Direction generateRandomDirection(){
        Direction[] directions = Direction.values();
        return directions[(int) Math.round(Math.random() * (directions.length - 1))];
    }
}
