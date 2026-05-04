package core;

import org.openqa.selenium.WebDriver;

public class Utils {

    private Utils() {}

    public static String getConfigPropertyByKey(String key) {

        String value = System.getProperty(key);

        if (value != null) {
            return value;
        }

        return PropertiesManager.get(key);
    }

    public static int getTimeout() {
        return Integer.parseInt(getConfigPropertyByKey("timeoutSeconds"));
    }
}