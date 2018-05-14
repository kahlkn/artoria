package com.github.kahlkn.artoria.logging;

import com.github.kahlkn.artoria.exception.UncheckedException;
import org.junit.Test;

public class LoggerTest {
    private static Logger log = LoggerFactory.getLogger(LoggerTest.class.getName());

    @Test
    public void logMessage() {
        log.trace("Hello, World!");
        log.debug("Hello, World!");
        log.info("Hello, World!");
        log.warn("Hello, World!");
        log.error("Hello, World!");
        System.err.println("set level INFO");
        LoggerFactory.setLevel(Level.INFO);
        log.trace("Hello, World!");
        log.debug("Hello, World!");
        log.info("Hello, World!");
        log.warn("Hello, World!");
        log.error("Hello, World!");
    }

    @Test
    public void logException() {
        log.trace("Hello, World!", new UncheckedException());
        log.debug("Hello, World!", new UncheckedException());
        log.info("Hello, World!", new UncheckedException());
        log.warn("Hello, World!", new UncheckedException());
        log.error("Hello, World!", new UncheckedException());
    }

}
