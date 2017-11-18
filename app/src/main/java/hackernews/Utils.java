package hackernews;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final String TAG = "Utils";
    private static Gson gson = new Gson();

    /**
     * Open connection to URL and fetch input as {@link String}
     * @param urlString
     * @return Input of connection stream as a String
     * @throws IOException when url string is incorrect or there's no response from the connection
     */
    public static String readInputFromURL(String urlString) throws IOException {
        Log.i(TAG, "Fetching resource from URL: %s" + urlString);
        URL url = new URL(urlString);
        return IOUtils.toString(url, "UTF-8");
    }

    /**
     * Deserializes the json input string into the object of the specified class.
     * @param input JSON string
     * @param clazz the class of T
     * @param <T> the type of the desired object
     * @return an object of type T that was
     * @throws JsonSyntaxException
     */
    public static <T> T loadJSON(final String input, final Class<T> clazz) throws JsonSyntaxException {
        return gson.fromJson(input, clazz);
    }

    /**
     * Convert {@link List<Double>} into {@link List<Integer>}
     * @param list List of doubles
     * @return List of integers
     */
    public static List<Integer> convertDoubleToIntegerList(List<Double> list) {
        List<Integer> newList = new ArrayList<>();
        for(Double s : list) {
            newList.add(s.intValue());
        }
        return newList;
    }
}
