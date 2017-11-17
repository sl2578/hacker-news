package hackernews;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;


abstract class RetrieveFromAPITask<T> extends AsyncTask<String, Void, T> {
    public final String TAG = "RetrieveFromAPI";

    private final Gson gson = new Gson();
    private final AppState appState;

    public RetrieveFromAPITask(final AppState appState) {
        this.appState = appState;
    }

    public AppState getAppState() {
        return appState;
    }

    @Override
    protected T doInBackground(String... urls) {
        InputStream stream = urls == null || urls.length == 0 ? null : Utils.readInputFromURL(urls[0]);
        if (stream != null) {
            return gson.fromJson(new InputStreamReader(stream), getRetrievedClass());
        }
        Log.w(TAG, "Input stream from API endpoint is empty");
        return null;
        // TODO: close inputstream?
    }

    @Override
    protected void onPostExecute(final T thing) {
        executeOnRetrieved(thing, this.appState);
    }

    protected abstract void executeOnRetrieved(final T retrieved, final AppState appState);

    protected abstract Class<T> getRetrievedClass();
}