package branch.hackernews.RetrieveFromAPI;

import android.util.Log;

import java.util.List;

import branch.hackernews.AppState;
import branch.hackernews.HackerNews;
import branch.hackernews.Utils;

/**
 * Retrieve top stories list from the HackerNews /topstories endpoint and retrieve each story info
 * to store as a Story object
 */
public class RetrieveTopStoriesTask extends RetrieveFromAPITask<AppState> {


    public RetrieveTopStoriesTask(AppState appState) {
        super(appState);
    }

    @Override
    protected AppState doInBackground(String... urls) {
        AppState appState = getAppState();
        String input = Utils.fetchResource(TAG, urls[0]);

        if (input == null) {
            Log.w(TAG, "Input stream from API endpoint is empty: " + urls[0]);
            return appState;
        }

        // Gson handles integer inputs as doubles, so convert to integer list
        //noinspection unchecked
        final List<Double> storyIdsDouble = Utils.loadJSON(input, List.class);
        final List<Integer> newsIds = Utils.convertDoubleToIntegerList(storyIdsDouble);

        appState.setIdsList(newsIds);

        return appState;
    }

    @Override
    protected void onPostExecute(final AppState appState) {
        new RetrieveNewsStoryTask(appState).execute(HackerNews.GET_STORY_URL);
    }

    @Override
    protected Class<AppState> getRetrievedClass() {
        return AppState.class;
    }

}