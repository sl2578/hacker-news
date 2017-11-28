package branch.hackernews.RetrieveFromAPI;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import branch.hackernews.HackerNews;
import branch.hackernews.JSONObject.Story;
import branch.hackernews.Utils;

public class RetrieveNewsStoryTask extends AsyncTask<Void, Void, Map<Integer, String>> {
    public final String TAG = RetrieveNewsStoryTask.class.getName();

    Activity mainActivity;
    private List<Integer> topStoryIds;
    private String url;
    private int offset;
    private int maxLoadSize = 15;


    public RetrieveNewsStoryTask(HackerNews hackerNews, List<Integer> topStoryIds, String url, int offset) {
        super();
        this.mainActivity = hackerNews;
        this.topStoryIds = topStoryIds;
        this.url = url;
        this.offset = offset;
    }

    @Override
    protected Map<Integer, String> doInBackground(Void... params) {
        Map<Integer, String> storyJsons = new HashMap<>();
        int upperLimit =  Math.min(offset + maxLoadSize, topStoryIds.size());

        for (final int storyId : topStoryIds.subList(offset, upperLimit)) {
            storyJsons.put(storyId, retrieveNewsStory(storyId));
        }

        return storyJsons;
    }

    @Override
    protected void onPostExecute(Map<Integer, String> storyJsons) {
        ((HackerNews) mainActivity).loadStoriesFromJSON(storyJsons);
    }

    /**
     * Retrieve news story from their respective API endpoint and deserialize the incoming json
     * as {@link Story}
     * @param storyId id of story to retrieve from API endpoint
     */
    protected String retrieveNewsStory(int storyId) {
        String storyUrl = String.format(url, storyId);
        String input = Utils.fetchResource(TAG, storyUrl);

        Log.d(TAG, "StoryId: " + storyId + ": " + input);

        if (input == null) {
            Log.w(TAG, "Input stream from API endpoint is empty: " + storyUrl);
            return null;
        }

        return input;
    }


    /**
     * Set max number of stories to load from HackerNews API
     */
    public void setMaxLoadSize(int maxLoadSize) {
        this.maxLoadSize = maxLoadSize;
    }
}
