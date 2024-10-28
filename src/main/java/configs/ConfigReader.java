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
        return properties.getProperty("browser", "chrome");
    }
    public static String getEdgeBrowser() {

        return properties.getProperty("browser", "edge");
    }
    public static String getFirefoxBrowser() {

        return properties.getProperty("browser", "firefox");
    }

    public static String getBaseURL() {
        return properties.getProperty("baseURL");
    }

}
