package com.jpmorgan.marsrover;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarsRoverApp {

    public static void main(String[] args) {
        try {
            SpringApplication.run(MarsRoverApp.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}