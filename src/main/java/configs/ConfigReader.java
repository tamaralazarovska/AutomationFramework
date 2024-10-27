package configs;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    public static Properties properties;

    // Constructor that takes a config file path
    public ConfigReader(String configFilePath) {
        properties = new Properties();
        try (FileInputStream input = new FileInputStream(configFilePath)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getChromeBrowser() {
        return properties.getProperty("browser", "chrome"); // Default to chrome if not specified
    }
    public static String getEdgeBrowser() {
        return properties.getProperty("browser", "edge"); // Default to chrome if not specified
    }
    public static String getFirefoxBrowser() {
        return properties.getProperty("browser", "firefox"); // Default to chrome if not specified
    }

    public static String getBaseURL() {
        return properties.getProperty("baseURL");
    }

}
