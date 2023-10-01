package com.jpmorgan.marsrover;

import org.springframework.stereotype.Component;

//The GUI displayed on the console
//Note: Coordinates (0,0) is the bottom left space. (10,10) is the top right space.
@Component
public class MarsRoverGrid {

    private char[][] grid;

    // Creating the grid space
    public MarsRoverGrid() {
        grid = new char[11][11];
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                grid[row][col] = ' ';
            }
        }
    }

    // Add the borders around the grid to make it more realistic
    public void drawGrid() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (col == grid[0].length - 1) {
                    System.out.print("|" + grid[row][col] + "|");
                } else {
                    System.out.print("|" + grid[row][col]);
                }

            }
            System.out.println();

            for (int col = 0; col < grid[0].length; col++) {
                if (col == grid[0].length - 1) {
                    System.out.print("---");
                } else {
                    System.out.print("--");
                }
            }
            System.out.println();
        }
    }

    // When rovers are initialized or moved, place them in the grid
    public void updateGrid(int row, int col, char roverId) {
        try {
            if (row >= 0 && row <= 10 && col >= 0 && col <= 10) {
                grid[grid[0].length - col - 1][row] = roverId;
            } else {
                System.out.println("Error: Rover tried to move out of bounds. Ignoring the move.");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: Rover tried to move out of bounds. Ignoring the move.");
        }
    }
}
