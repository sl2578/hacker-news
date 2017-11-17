package hackernews.RetrieveFromAPI;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hackernews.AppState;
import hackernews.HackerNews;
import hackernews.JSONObject.Story;
import hackernews.NewsAdapter;
import hackernews.Utils;

/**
 * Retrieve top stories list from the HackerNews /topstories endpoint and retrieve each story info
 * to store as a Story object in {@link AppState.showNewsList}
 */
public class RetrieveTopStoriesTask extends RetrieveFromAPITask<List> {

    public RetrieveTopStoriesTask(AppState appState) {
        super(appState);
    }

    @Override
    protected List doInBackground(String... urls) {
        AppState appState = getAppState();
        String input = urls == null || urls.length == 0 ? null : Utils.readInputFromURL(urls[0]);

        // Gson handles integer inputs as doubles, so convert to integer list
        //noinspection unchecked
        final List<Double> storyIdsDouble = Utils.loadJSON(input, List.class);
        final List<Integer> newsIds = Utils.convertDoubleToIntegerList(storyIdsDouble);

        appState.setTopStories(newsIds);

        for (final int storyId : newsIds.subList(0,2)) {
            Log.i(TAG, "Getting story " + storyId);
            String storyUrl = String.format(HackerNews.GET_STORY_URL, storyId);
            retrieveNewsStory(storyUrl);
        }

        return newsIds;
    }

    private void retrieveNewsStory(String storyUrl) {
        String input = Utils.readInputFromURL(storyUrl);
        if (input != null) {
            Story story = Utils.loadJSON(input, Story.class);
            Log.i(TAG, "Adding story to list: " + story.getTitle());
            this.getAppState().addNews(story);
        }

    }

    @Override
    protected void executeOnRetrieved(final List newsObjects, final AppState appState) {
        Log.i(TAG, "Story list: " + appState.getShowStoryList());
        HashMap<Integer, List<String>> newsInfo = new HashMap<>();
        List<String> newsInfoText = new ArrayList<>();
        newsInfoText.add("View Article");
        newsInfoText.add("View User");
        newsInfoText.add("View Comments");
        for (Story story : appState.getShowStoryList()) {
            newsInfo.put(story.getId(), newsInfoText);
        }
        appState.getNewsList().setAdapter(
                new NewsAdapter(appState.getContext(), appState.getShowStoryList(), newsInfo));
    }

    @Override
    protected Class<List> getRetrievedClass() {
        return List.class;
    }

}