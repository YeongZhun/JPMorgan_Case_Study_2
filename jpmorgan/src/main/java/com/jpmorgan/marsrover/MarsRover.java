package com.jpmorgan.marsrover;

public class MarsRover {
    private final int roverId;
    private int x;
    private int y;
    private Direction direction;

    public MarsRover(int roverId, int x, int y, Direction direction) {
        this.roverId = roverId;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void moveForward() {
        switch (direction) {
            case N:
                y++;
                break;
            case S:
                y--;
                break;
            case E:
                x++;
                break;
            case W:
                x--;
                break;
        }
    }

    public void moveBackwards() {
        switch (direction) {
            case N:
                y--;
                break;
            case S:
                y++;
                break;
            case E:
                x--;
                break;
            case W:
                x++;
                break;
        }
    }

    public void turnRight(MarsRover rover) {
        switch (direction) {
            case N:
                rover.setDirection(Direction.E);
                break;
            case S:
                rover.setDirection(Direction.W);
                break;
            case E:
                rover.setDirection(Direction.S);
                break;
            case W:
                rover.setDirection(Direction.N);
                break;
        }
    }

    public void turnLeft(MarsRover rover) {
        switch (direction) {
            case N:
                rover.setDirection(Direction.W);
                break;
            case S:
                rover.setDirection(Direction.E);
                break;
            case E:
                rover.setDirection(Direction.N);
                break;
            case W:
                rover.setDirection(Direction.S);
                break;
        }
    }

    public int calculateNextForwardX() {
        int nextX = x;

        switch (direction) {
            case N:
                break;
            case S:
                break;
            case E:
                nextX++;
                break;
            case W:
                nextX--;
                break;
            // For N and S, X remains the same.
        }

        return nextX;
    }

    public int calculateNextForwardY() {
        int nextY = y;

        switch (direction) {
            case N:
                nextY++;
                break;
            case S:
                nextY--;
                break;
            case E:
                break;
            case W:
                break;
            // For E and W, Y remains the same.
        }

        return nextY;
    }

    public int calculateNextBackwardX() {
        int backwardX = x;

        switch (direction) {
            case N:
                break;
            case S:
                break;
            case E:
                backwardX--;
                break;
            case W:
                backwardX++;
                break;
            // For N and S, X remains the same.
        }

        return backwardX;
    }

    public int calculateNextBackwardY() {
        int backwardY = y;

        switch (direction) {
            case N:
                backwardY--;
                break;
            case S:
                backwardY++;
                break;
            case E:
                break;
            case W:
                break;
            // For E and W, Y remains the same.
        }

        return backwardY;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getRoverId() {
        return roverId;
    }

}
