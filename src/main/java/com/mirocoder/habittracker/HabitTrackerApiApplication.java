package com.mirocoder.habittracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableScheduling
@SpringBootApplication
public class HabitTrackerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HabitTrackerApiApplication.class, args);
    }

}
