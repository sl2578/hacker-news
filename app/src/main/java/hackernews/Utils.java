package hackernews;

import android.util.Log;

import com.google.gson.Gson;

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

    public static InputStream readInputFromURL(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("HTTP Status Code: " + responseCode);
            }
            return conn.getInputStream();
        } catch (MalformedURLException e) {
            Log.e(TAG, String.format("Hacker News API endpoint is invalid: %s", urlString), e);
        } catch (IOException e) {
            Log.e(TAG, "Failed to download top news stories from Hacker News", e);
        }
        return null;
    }


    public static <T> T loadJSON(final InputStream inputStream, final Class<T> clazz) {
        if (inputStream != null) {
            return gson.fromJson(new InputStreamReader(inputStream), clazz);
        }
        Log.w(TAG, "Input stream from API endpoint is empty");
        return null;
    }
}
