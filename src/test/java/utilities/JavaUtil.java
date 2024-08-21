package utilities;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Random;

public class JavaUtil {

    private static String propertiesFilePath = "/src/test/java/config/Config.properties";

    public static String readJSON(String filePath) {
        String json = "";
        try {
            json = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (Exception e) {
            e.getMessage();
        }

        return json;
    }

    public static String readProperties(String key) {
        Properties prop = new Properties();
        try {
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + propertiesFilePath);
            prop.load(fis);
        } catch (Exception e) {
            e.getMessage();
        }
        String value = prop.getProperty(key);
        return value;
    }

    public static int getRandomIndex(int size) {
        Random random = new Random();
        int index = random.nextInt(size);

        return index;
    }

    public static String replaceQueryWithString(String endpointValue, String replacement) {
        String updatedEndpointWithQuery = null;
        try {
            String query = endpointValue.substring(endpointValue.indexOf("{"), endpointValue.indexOf("}") + 1);
            updatedEndpointWithQuery = endpointValue.replace(query, replacement);

        } catch (NullPointerException e) {
            System.out.println("Message: " + e.getMessage());
        }

        return updatedEndpointWithQuery;
    }

}
