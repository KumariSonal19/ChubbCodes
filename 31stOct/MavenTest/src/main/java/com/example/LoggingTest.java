package com.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingTest {
    private static final Logger logger = LogManager.getLogger(LoggingTest.class);

    public static void main(String[] args) {
        logger.trace("Trace Message — Verbose details for debugging");
        logger.debug("Debug Message — For developer analysis");
        logger.info("Info Message — Normal flow information");
        logger.warn("Warning Message — Something might be off");
        logger.error("Error Message — Something failed");
        logger.fatal("Fatal Message — Critical system failure");

        try {
            int i = 10 / 0; // This will cause an exception
        } catch (Exception e) {
            logger.error("Error occurred: " + e.getMessage());
        }

        logger.debug("End of program");
    }
}
