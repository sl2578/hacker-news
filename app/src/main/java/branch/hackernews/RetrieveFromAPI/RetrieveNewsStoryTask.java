package branch.hackernews.RetrieveFromAPI;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import branch.hackernews.HackerNewsMainActivity;
import branch.hackernews.JSONObject.Story;
import branch.hackernews.api.HackerNewsAPIInterface;
import retrofit2.Call;

public class RetrieveNewsStoryTask extends AsyncTask<Void, Void, Map<Integer, Story>> {
    private final String TAG = RetrieveNewsStoryTask.class.getName();

    private final Activity mainActivity;
    private final List<Integer> topStoryIds;
    private final int offset;
    private final HackerNewsAPIInterface apiService;
    private int maxLoadSize = 15;

    public RetrieveNewsStoryTask(HackerNewsMainActivity hackerNewsMainActivity,
                                 List<Integer> topStoryIds,
                                 int offset,
                                 HackerNewsAPIInterface apiService) {
        super();
        this.mainActivity = hackerNewsMainActivity;
        this.topStoryIds = topStoryIds;
        this.offset = offset;
        this.apiService = apiService;
    }

    @Override
    protected Map<Integer, Story> doInBackground(Void... params) {
        Map<Integer, Story> storyMap = new HashMap<>();
        final int upperLimit =  Math.min(offset + maxLoadSize, topStoryIds.size());

        for (final int storyId : topStoryIds.subList(offset, upperLimit)) {
            try {
                storyMap.put(storyId, retrieveNewsStory(storyId));
            } catch (IOException e) {
                Log.w(TAG, "Unable to retrieve story from HackerNewsMainActivity API: "
                        + storyId);
            }
        }

        return storyMap;
    }

    @Override
    protected void onPostExecute(Map<Integer, Story> storyJsons) {
        ((HackerNewsMainActivity) mainActivity).loadStories(storyJsons);
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
     * Set max number of stories to load from HackerNewsMainActivity API
     */
    public void setMaxLoadSize(int maxLoadSize) {
        this.maxLoadSize = maxLoadSize;
    }
}
