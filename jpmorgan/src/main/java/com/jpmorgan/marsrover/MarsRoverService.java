package com.jpmorgan.marsrover;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarsRoverService {

    private final List<MarsRover> rovers = new ArrayList<>();

    private final MarsRoverGrid marsRoverGrid; 

    @Autowired
    public MarsRoverService(MarsRoverGrid marsRoverGrid) {
        this.marsRoverGrid = marsRoverGrid;
    }

    // Initialize a single rover
    public MarsRover initRover(int roverId, int x, int y, Direction direction) throws IllegalArgumentException {
        // check if roverId already exist
        boolean roverIdExists = false;
        for (MarsRover rover : rovers) {
            if (rover.getRoverId() == roverId) {
                roverIdExists = true;
                break;
            }
        }

        if (roverIdExists) {
            throw new IllegalArgumentException("Rover ID already exists.");
        }

        // I limit the Id to single digit, because of my console grid GUI limitation. It can only display one character.
        if (roverId < 0 | roverId > 9) {
            throw new IllegalArgumentException("RoverId should only be from 0 to 9");
        }

        // I limit the coordinates to 10x10 grid, but it can be adjusted accordingly.
        if ((x < 0 | x > 10) | (y < 0 | y > 10)) {
            throw new IllegalArgumentException("Coordinates should only be from 0 to 10. e.g. (0,0) to (10,10)");
        }

        // Check if another rover is already at the specified coordinates
        for (MarsRover rover : rovers) {
            if (rover.getX() == x && rover.getY() == y) {
                throw new IllegalArgumentException("Another rover is already at position (" + x + ", " + y + ").");
            }
        }

        MarsRover rover = new MarsRover(roverId, x, y, direction);
        rovers.add(rover);

        System.out.println("\nCurrent list of rovers: ");
        for (MarsRover r : rovers) {
            System.out.println("Rover ID: " + r.getRoverId());
            System.out.println("Position: (" + r.getX() + ", " + r.getY() + ")");
            System.out.println("Direction: " + r.getDirection() + "\n");
        }

        // Update the grid with the new rover's position
        marsRoverGrid.updateGrid(x, y, Integer.toString(roverId).charAt(0));

        return rover;
    }

    // Move a single rover
    public void moveRover(int roverId, String commands) {
        //Find rover based on roverId 
        MarsRover roverToMove = rovers.stream()
                .filter(r -> r.getRoverId() == roverId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Rover not found"));

        int newX = roverToMove.getX();
        int newY = roverToMove.getY();

        // Remove current position on grid first, since rover is moving.
        // If there is collision or hit the boundary, it is ok, because at the end we updateGrid to the same newX and newY again. 
        marsRoverGrid.updateGrid(newX, newY, ' ');

        for (int i = 0; i < commands.length(); i++) {
            char command = commands.charAt(i);

            // Calculate the new coordinates after executing the command.
            switch (command) {
                case 'f':
                    newX = roverToMove.calculateNextForwardX();
                    newY = roverToMove.calculateNextForwardY();
                    break;
                case 'b':
                    newX = roverToMove.calculateNextBackwardX();
                    newY = roverToMove.calculateNextBackwardY();
                    break;
                case 'r':
                    roverToMove.turnRight(roverToMove);
                    break;
                case 'l':
                    roverToMove.turnLeft(roverToMove);
                    break;
            }

            // Check if the new position is within bounds. If we change the grid size, this needs to be changed accordingly.
            if (newX < 0 || newX > 10 || newY < 0 || newY > 10) {
                System.out.println("Error: Rover tried to move out of bounds. Ignoring the move.");
                break; // Skip the move
            }

            // Check for collision with other rovers.
            if (isOccupiedByOtherRover(roverToMove, newX, newY)) {
                System.out.println("Collision detected! Rover " + roverToMove.getRoverId() + " stopped moving.");
                break; // Skip the move
            }

            // Update the rover's position after checking for collisions.
            roverToMove.setX(newX);
            roverToMove.setY(newY);
        }

        // Update the grid with the new rover's position
        marsRoverGrid.updateGrid(roverToMove.getX(), roverToMove.getY(), Integer.toString(roverId).charAt(0));

        System.out.println("Current position of Rover " + roverToMove.getRoverId() + ": (" + roverToMove.getX() + ", "
                + roverToMove.getY() + ")");
    }

    // Check for collision
    private boolean isOccupiedByOtherRover(MarsRover movingRover, int newX, int newY) {
        for (MarsRover rover : rovers) {
            if (rover != movingRover && rover.getX() == newX && rover.getY() == newY) {
                return true; // Coordinates are occupied by another rover.
            }
        }
        return false; // No collision detected.
    }

    // return rover based on roverId
    public MarsRover getRover(int roverId) {
        try {
            for (MarsRover rover : rovers) {
                if (rover.getRoverId() == roverId) {
                    return rover;
                }
            }
            throw new Exception("Rover not found");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // returns the whole list of rovers
    public List<MarsRover> getRovers() {
        return rovers;
    }

}
