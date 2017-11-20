package branch.hackernews.RetrieveFromAPI;

import android.util.Log;
import android.widget.ExpandableListView;

import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import branch.hackernews.AppState;
import branch.hackernews.JSONObject.Story;
import branch.hackernews.Utils;
import branch.hackernews.adapter.NewsAdapter;

public class RetrieveNewsStoryTask extends RetrieveFromAPITask<AppState> {
    private static int LOAD_NUM_ITEM = 15;

    Map<Integer, List<String>> newsInfo = new HashMap<>();

    List<String> newsInfoText = new ArrayList<>();
    public RetrieveNewsStoryTask(AppState appState) {
        super(appState);
        // Initialize newsInfoText list
        newsInfoText.add("View Article");
        newsInfoText.add("View User");
        newsInfoText.add("View Comments");
    }

    @Override
    protected AppState doInBackground(String... urls) {
        AppState appState = getAppState();
        List<Integer> topStoryIds = appState.getIdsList();
        int offset = appState.getIdsListIndex();

        //TODO: deal with out of bounds
        for (final int storyId : topStoryIds.subList(offset, offset + LOAD_NUM_ITEM)) {
            String storyUrl = String.format(urls[0], storyId);
            retrieveNewsStory(storyUrl);
        }
        appState.setIdsListIndex(offset + LOAD_NUM_ITEM);

        return appState;
    }

    @Override
    protected void onPostExecute(AppState appState) {
        Log.i(TAG, "Top news stories retrieved, displaying " + appState.getShowStoryList().size());
        for (Story story : appState.getShowStoryList()) {
            newsInfo.put(story.getId(), newsInfoText);
        }
        ((ExpandableListView) appState.getView()).setAdapter(
                new NewsAdapter(appState.getContext(), appState.getShowStoryList(), newsInfo));
    }

    @Override
    protected Class<AppState> getRetrievedClass() {
        return null;
    }

    /**
     * Retrieve news story from their respective API endpoint and deserialize the incoming json
     * as {@link Story}
     * @param storyUrl API Endpoint to get data for the specific story
     */
    protected void retrieveNewsStory(String storyUrl) {
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
}
