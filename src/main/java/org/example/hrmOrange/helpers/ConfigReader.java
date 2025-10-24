package org.example.hrmOrange.helpers;

import java.io.FileInputStream;
import java.net.InetAddress;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigReader {
    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);
    private static final Properties properties = new Properties();
    public static final String ENV = System.getProperty("activated-env");
    public static final String HOST_NAME;
    public static final String OS_NAME = System.getProperty("os.name");
    public static String BROWSER_VERSION = "Not Set";

    static {
        try {
            // Load properties based on the environment
            HOST_NAME = System.getProperty("user.name") + " - " + InetAddress.getLocalHost().getHostName();

//            String propertiesFile = "src/test/resources/config/" + ENV + ".properties";
            String propertiesFile = "src/test/resources/config/config.properties";
            properties.load(new FileInputStream(propertiesFile));
        } catch (Exception e) {
            throw new RuntimeException("Could not load properties file for environment: " + ENV);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

}