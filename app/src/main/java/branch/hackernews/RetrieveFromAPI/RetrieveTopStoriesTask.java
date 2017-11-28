package branch.hackernews.RetrieveFromAPI;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import branch.hackernews.api.HackerNewsAPIInterface;
import retrofit2.Call;

/**
 * Retrieve top stories list from the HackerNews /topstories endpoint and retrieve each story info
 * to store as a Story object
 */
public class RetrieveTopStoriesTask extends AsyncTask<Void, Void, List<Integer>> {
    private final String TAG = RetrieveTopStoriesTask.class.getName();

    private final HackerNewsAPIInterface apiService;

    public RetrieveTopStoriesTask(HackerNewsAPIInterface apiService) {
        this.apiService = apiService;
    }

    @Override
    protected List<Integer> doInBackground(Void... params) {
        Call<List<Integer>> call = apiService.getTopStories();

        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.w(TAG, "Failed to get top stories from API");
            return Collections.emptyList();
        }
    }

    @Override
    protected void onPostExecute(final List<Integer> result) {
    }
}