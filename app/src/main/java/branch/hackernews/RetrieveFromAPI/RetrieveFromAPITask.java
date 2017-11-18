package branch.hackernews.RetrieveFromAPI;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import branch.hackernews.AppState;
import branch.hackernews.Utils;

/**
 * Abstract class to retrieve JSON data from HackerNews API and parse the JSON into object T
 *
 * @param <T> The object type that the JSON data is parsed as
 */
abstract class RetrieveFromAPITask<T> extends AsyncTask<String, Void, T> {
    public final String TAG = RetrieveFromAPITask.class.getName();

    private final AppState appState;

    public RetrieveFromAPITask(final AppState appState) {
        this.appState = appState;
    }

    public AppState getAppState() {
        return appState;
    }

    @Override
    protected T doInBackground(String... urls) {
        String input = null;
        try {
            Log.i(TAG, "Fetching resource from URL: %s" + urls[0]);
            input = urls == null || urls.length == 0 ? null : Utils.readInputFromURL(urls[0]);
        } catch (IOException e) {
            Log.e(TAG, "Failed to get data from Hacker News: " + urls[0], e);
        }

        if (input != null) {
            return Utils.loadJSON(input, getRetrievedClass());
        } else {
            Log.w(TAG, "Input stream from API endpoint is empty");
            return null;
        }
    }

    protected abstract void onPostExecute(final T returnObject);

//        executeOnRetrieved(returnObject, this.appState);
    // TODO: Do we need this?
    /** Once data is retrieved from the API, save into {@link AppState} */
//    protected abstract void executeOnRetrieved(final T retrieved, final AppState appState);

    /** Define the class the JSON data will be retrieved as */
    protected abstract Class<T> getRetrievedClass();
}