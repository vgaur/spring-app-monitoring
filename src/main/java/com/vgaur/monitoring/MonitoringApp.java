package com.vgaur.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableMBeanExport;

/**
 * Staring point of the application.
 */
@SpringBootApplication @EnableMBeanExport public class MonitoringApp {

    public static void main(String[] args) {
        SpringApplication.run(MonitoringApp.class, args);
    }



}
