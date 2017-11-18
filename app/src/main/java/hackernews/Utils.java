package hackernews;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final String TAG = "Utils";
    private static Gson gson = new Gson();

    /**
     * Open connection
     * @param urlString
     * @return
     */
    public static String readInputFromURL(String urlString) throws IOException {
        Log.i(TAG, "Fetching resource from URL: %s" + urlString);
        URL url = new URL(urlString);
        return IOUtils.toString(url, "UTF-8");
    }


    public static <T> T loadJSON(final String input, final Class<T> clazz) throws JsonSyntaxException {
        Log.i(TAG, String.format("Loading JSON %s as class %s", input, clazz.getSimpleName()));
        return gson.fromJson(input, clazz);
    }

    public static List<Integer> convertDoubleToIntegerList(List<Double> list) {
        List<Integer> newList = new ArrayList<>();
        for(Double s : list) {
            newList.add(s.intValue());
        }
        return newList;
    }
}
