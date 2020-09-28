package ru.pchelnikov.SpringBootDemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggingController {
    Logger logger = LoggerFactory.getLogger(LoggingController.class);

    @RequestMapping("/")
    public String index() {
        logger.trace("TRACE message from LoggingController");
        logger.debug("DEBUG message from LoggingController");
        logger.info("INFO message from LoggingController");
        logger.warn("WARN message from LoggingController");
        logger.error("ERROR message from LoggingController");

        return "Check your logs in console and file target/testlog.log";
    }
}
