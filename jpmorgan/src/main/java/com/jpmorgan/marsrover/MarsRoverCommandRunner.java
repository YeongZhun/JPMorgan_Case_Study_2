package com.jpmorgan.marsrover;

import java.util.Arrays;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MarsRoverCommandRunner implements CommandLineRunner {

    private final MarsRoverService marsRoverService;
    private final MarsRoverGrid marsRoverGrid;

    public MarsRoverCommandRunner(MarsRoverService marsRoverService, MarsRoverGrid marsRoverGrid) {
        this.marsRoverService = marsRoverService;
        this.marsRoverGrid = marsRoverGrid;
    }

    @Override
    public void run(String... args) throws Exception {
        marsRoverGrid.drawGrid();
        Scanner scanner = new Scanner(System.in);
        boolean exitRequested = false;

        try {
            while (!exitRequested) {
                System.out.print("Enter your command (or type 'exit' to quit the loop): ");

                String input = scanner.nextLine().trim();
                // It exits this while loop, but spring app will continue to run in IDE
                if ("exit".equalsIgnoreCase(input)) {
                    exitRequested = true;
                } else {
                    String[] commandArgs = input.split(" ");
                    processCommand(commandArgs);
                }
            }
        } catch (Exception e) {
            // Handle the exception as needed
            e.printStackTrace();
        } finally {
            // Close the scanner in the finally block to ensure it gets closed even if an
            // exception occurs
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    // Display list of possible inputs for user
    public void processCommand(String[] args) {
        if (args.length == 0) {
            printHelp();
            return;
        }

        String command = args[0];

        switch (command) {
            case "init":
                try {
                    initRover(args);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case "batch-init":
                try {
                    batchInitRovers(Arrays.copyOfRange(args, 1, args.length));
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case "move":
                try {
                    moveRover(args);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case "status":
                try {
                    getRoverStatus(args);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                break;

            default:
                System.out.println("Invalid command.");
                printHelp();
        }
        // update the GUI after the command
        marsRoverGrid.drawGrid();
    }

    // Initialize a single rover
    private void initRover(String[] args) throws IllegalArgumentException {
        if (args.length != 5) {
            throw new IllegalArgumentException("Usage: init <roverId> <x> <y> <direction> e.g. init 1 2 3 N");
        }

        int roverId = Integer.parseInt(args[1]);
        int x = Integer.parseInt(args[2]);
        int y = Integer.parseInt(args[3]);
        Direction direction = Direction.valueOf(args[4]);

        marsRoverService.initRover(roverId, x, y, direction);
        System.out.println("Rover " + roverId + " initialized successfully.");
    }

    // Initialize multiple rovers
    private void batchInitRovers(String[] roverParams) {
        for (String params : roverParams) {
            String[] paramArray = params.split(",");

            if (paramArray.length != 4) {
                throw new IllegalArgumentException("Invalid rover initialization: " + params +
                        ". Usage: batch-init <roverId,x,y,direction> e.g. batch-init 1,2,3,N 4,5,6,S");
            }

            int roverId = Integer.parseInt(paramArray[0]);
            int x = Integer.parseInt(paramArray[1]);
            int y = Integer.parseInt(paramArray[2]);
            Direction direction = Direction.valueOf(paramArray[3]);

            marsRoverService.initRover(roverId, x, y, direction);
            System.out.println("Rover " + roverId + " initialized successfully.");
        }
    }

    // Move a rover
    private void moveRover(String[] args) {
        int roverId = Integer.parseInt(args[1]);
        String commands = args[2];
        marsRoverService.moveRover(roverId, commands);
    }

    // Get coordinate and direction of a rover
    private void getRoverStatus(String[] args) {
        int roverId = Integer.parseInt(args[1]);

        MarsRover rover = marsRoverService.getRover(roverId);
        System.out.println("Rover " + roverId + " status:");
        System.out.println("Position: (" + rover.getX() + "," + rover.getY() + ")");
        System.out.println("Direction: " + rover.getDirection());
    }

    // List of commands for user to see
    private void printHelp() {
        System.out.println("Usage:");
        System.out.println("  init <roverId> <x> <y> <direction>");
        System.out.println("  batch-init <rover1_params> <rover2_params> ...");
        System.out.println("  move <roverId> <commands>");
        System.out.println("  status <roverId>");
    }
}
