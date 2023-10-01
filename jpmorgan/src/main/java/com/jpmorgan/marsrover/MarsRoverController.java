package com.jpmorgan.marsrover;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//REST Apis, but only for a initializing and moving a single rover
@RestController
public class MarsRoverController {
    @RequestMapping("/")
    public String mainPage() {
        return "Welcome to Mars Rover App!";
    }

    @GetMapping("/mars-rover/{initialPosition}/{commands}")
    // e.g. http://localhost:8080/mars-rover/1,0,0,N/rff
    // It will initialize a rover with roverId = 1, x = 0, y = 0, direction = N
    // It will turn right, then forward twice. Final Coordinate (2,0), Final Direction: E
    public String marsRover(@PathVariable String initialPosition, @PathVariable String commands) {

        // Split the initial position into an array of strings.
        String[] initialPositionParts = initialPosition.split(",");

        // Get the initial position.
        int roverId = Integer.parseInt(initialPositionParts[0]);
        int x = Integer.parseInt(initialPositionParts[1]);
        int y = Integer.parseInt(initialPositionParts[2]);
        Direction direction = Direction.valueOf(initialPositionParts[3]);

        MarsRover rover = new MarsRover(roverId, x, y, direction);

        for (int i = 0; i < commands.length(); i++) {
            switch (commands.charAt(i)) {
                case 'f':
                    rover.moveForward();
                    break;
                case 'b':
                    rover.moveBackwards();
                    break;
                case 'r':
                    rover.turnRight(rover);
                    break;
                case 'l':
                    rover.turnLeft(rover);
                    break;
            }
        }

        return "Final Coordinate: (" + rover.getX() + ", " + rover.getY() + ")\nFinal Direction: " + rover.getDirection();
    }
}
