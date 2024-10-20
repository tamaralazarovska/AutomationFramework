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

    public static String getBrowser() {
        return properties.getProperty("browser", "chrome"); // Default to chrome if not specified
    }

//    public boolean isHeadless() {
//        return Boolean.parseBoolean(properties.getProperty("headless", "false")); // Default to false
//    }
//
//
//    public String getBaseURL() {
//        return properties.getProperty("baseURL", "https://www.bugaboo.com/us-en"); // Default to Bugaboo URL
//    }

}
