package stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import utils.DriverManager;
import utils.LogHelper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Hooks {

    private static final Logger LOGGER = LogHelper.getLogger(Hooks.class);

    @Before
    public void setUp() {
        DriverManager.getDriver();
        LOGGER.log(Level.INFO, "Initializing browser");
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
        LOGGER.log(Level.INFO, "Browser close correctly");
    }
}
