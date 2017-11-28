package branch.hackernews.RetrieveFromAPI;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import branch.hackernews.HackerNews;
import branch.hackernews.JSONObject.Story;
import branch.hackernews.api.HackerRankAPIInterface;
import retrofit2.Call;

public class RetrieveNewsStoryTask extends AsyncTask<Void, Void, Map<Integer, Story>> {
    public final String TAG = RetrieveNewsStoryTask.class.getName();

    Activity mainActivity;
    private List<Integer> topStoryIds;
    private int offset;
    private int maxLoadSize = 15;
    private HackerRankAPIInterface apiService;

    public RetrieveNewsStoryTask(HackerNews hackerNews,
                                 List<Integer> topStoryIds,
                                 int offset,
                                 HackerRankAPIInterface apiService) {
        super();
        this.mainActivity = hackerNews;
        this.topStoryIds = topStoryIds;
        this.offset = offset;
        this.apiService = apiService;
    }

    @Override
    protected Map<Integer, Story> doInBackground(Void... params) {
        Map<Integer, Story> storyJsons = new HashMap<>();
        int upperLimit =  Math.min(offset + maxLoadSize, topStoryIds.size());

        for (final int storyId : topStoryIds.subList(offset, upperLimit)) {
            try {
                storyJsons.put(storyId, retrieveNewsStory(storyId));
            } catch (IOException e) {
                Log.w(TAG, "Unable to retrieve story from HackerNews API: "
                        + storyId);
            }
        }

        return storyJsons;
    }

    @Override
    protected void onPostExecute(Map<Integer, Story> storyJsons) {
        ((HackerNews) mainActivity).loadStories(storyJsons);
    }

    /**
     * Retrieve news story from their respective API endpoint and deserialize
     * the incoming json as {@link Story}
     * @param storyId id of story to retrieve from API endpoint
     * @throws IOException
     */
    protected Story retrieveNewsStory(int storyId) throws IOException {
        Call<Story> call = apiService.getStory(storyId);
        return call.execute().body();
    }

    /**
     * Set max number of stories to load from HackerNews API
     */
    public void setMaxLoadSize(int maxLoadSize) {
        this.maxLoadSize = maxLoadSize;
    }
}