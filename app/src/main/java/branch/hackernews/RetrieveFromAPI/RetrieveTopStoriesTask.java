package branch.hackernews.RetrieveFromAPI;

import android.util.Log;
import android.widget.ExpandableListView;

import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import branch.hackernews.AppState;
import branch.hackernews.HackerNews;
import branch.hackernews.JSONObject.Story;
import branch.hackernews.Utils;
import branch.hackernews.adapter.NewsAdapter;

/**
 * Retrieve top stories list from the HackerNews /topstories endpoint and retrieve each story info
 * to store as a Story object
 */
public class RetrieveTopStoriesTask extends RetrieveFromAPITask<AppState> {
    Map<Integer, List<String>> newsInfo = new HashMap<>();
    List<String> newsInfoText = new ArrayList<>();

    public RetrieveTopStoriesTask(AppState appState) {
        super(appState);
        // Initialize newsInfoText list
        newsInfoText.add("View Article");
        newsInfoText.add("View User");
        newsInfoText.add("View Comments");
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

        for (final int storyId : newsIds.subList(0,2)) {
            Log.i(TAG, "Getting story " + storyId);
            String storyUrl = String.format(HackerNews.GET_STORY_URL, storyId);
            retrieveNewsStory(storyUrl);
        }

        return appState;
    }

    /**
     * Retrieve news story from their respective API endpoint and deserialize the incoming json
     * as {@link Story}
     * @param storyUrl API Endpoint to get data for the specific story
     */
    private void retrieveNewsStory(String storyUrl) {
        String input = Utils.fetchResource(TAG, storyUrl);

        if (input == null) {
            Log.w(TAG, "Input stream from API endpoint is empty: " + storyUrl);
            return;
        }

        try {
            Story story = Utils.loadJSON(input, Story.class);
            Log.i(TAG, "Adding story to list: " + story.getTitle());
            this.getAppState().addNews(story);
        } catch (JsonSyntaxException e) {
            Log.e(TAG, "Unable to parse input as JSON" + input, e);
        }
    }
    @Override
    protected void onPostExecute(final AppState appState) {
        Log.i(TAG, "Top news stories retrieved, displaying " + appState.getShowStoryList().size());
        for (Story story : appState.getShowStoryList()) {
            newsInfo.put(story.getId(), newsInfoText);
        }
        ((ExpandableListView) appState.getView()).setAdapter(
                new NewsAdapter(appState.getContext(), appState.getShowStoryList(), newsInfo));
    }

    @Override
    protected Class<AppState> getRetrievedClass() {
        return AppState.class;
    }

}