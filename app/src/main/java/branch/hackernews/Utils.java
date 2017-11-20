package branch.hackernews;

import android.text.format.DateUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    private static Gson gson = new Gson();

    /**
     * Open connection to URL and fetch input as {@link String}
     * @param urlString
     * @return Input of connection stream as a String
     * @throws IOException when url string is incorrect or there's no response from the connection
     */
    public static String readInputFromURL(String urlString) throws IOException {
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

    /**
     * Fetch resource from url as string
     * @param tag TAG for logging
     * @param url URL to connect to get the resource
     * @return String containing resource attained from url
     */
    public static String fetchResource(String tag, String url) {
        String input = null;
        try {
            Log.i(tag, "Fetching resource from URL: %s" + url);
            input = url == null ? null : Utils.readInputFromURL(url);
        } catch (IOException e) {
            Log.e(tag, "Failed to download top news stories from Hacker News: " + url, e);
        }
        return input;
    }

    /**
     * Get how much time passed since the input unix timestamp
     * @param past Initial time to calculate the time "since"
     * @param current Time to calculate to
     * @return String containing how "long ago" the input timestamp was
     */
    public static String timeSince(long past, long current) {
        return DateUtils.getRelativeTimeSpanString(past, current, 0).toString();
    }
}