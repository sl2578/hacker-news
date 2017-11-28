package branch.hackernews.RetrieveFromAPI;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Collections;
import java.util.List;

import branch.hackernews.Utils;

/**
 * Retrieve top stories list from the HackerNews /topstories endpoint and retrieve each story info
 * to store as a Story object
 */
public class RetrieveTopStoriesTask extends AsyncTask<Void, Void, List<Integer>> {
    public final String TAG = RetrieveTopStoriesTask.class.getName();

    String url;

    public RetrieveTopStoriesTask(String url) {
        this.url = url;
    }

    @Override
    protected List<Integer> doInBackground(Void... params) {
        String json = Utils.fetchResource(TAG, url);

        if (json == null) {
            Log.w(TAG, "Input stream from API endpoint is empty: " + url);
            return Collections.emptyList();
        }

        // Gson handles integer inputs as doubles, so convert to integer list
        final List<Double> storyIdsDouble = Utils.loadJSON(json, List.class);
        final List<Integer> newsIds = Utils.convertDoubleToIntegerList(storyIdsDouble);

        return newsIds;
    }

    @Override
    protected void onPostExecute(final List<Integer> result) {
    }
}