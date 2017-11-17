package hackernews;

import android.util.Log;

import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
    public static String readInputFromURL(String urlString) {
        try {
            URL url = new URL(urlString);
            return IOUtils.toString(url, "UTF-8");
        } catch (MalformedURLException e) {
            Log.e(TAG, String.format("Hacker Story API endpoint is invalid: %s", urlString), e);
        } catch (IOException e) {
            Log.e(TAG, "Failed to download top news stories from Hacker Story", e);
        }
        return null;
    }


    public static <T> T loadJSON(final String input, final Class<T> clazz) {
        if (input != null) {
            return gson.fromJson(input, clazz);
        }
        Log.w(TAG, "Input stream from API endpoint is empty");
        return null;
    }

    public static List<Integer> convertDoubleToIntegerList(List<Double> list) {
        List<Integer> newList = new ArrayList<>();
        for(Double s : list) {
            newList.add(s.intValue());
        }
        return newList;
    }
}
