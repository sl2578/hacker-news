package hackernews;

import android.util.Log;

import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import hackernews.JSONObject.News;


public class RetrieveTopStoriesTask extends RetrieveFromAPITask<List> {

    public RetrieveTopStoriesTask(AppState appState) {
        super(appState);
    }

    @Override
    protected List doInBackground(String... urls) {
        AppState appState = getAppState();
        InputStream inputStream = urls == null || urls.length == 0 ? null : Utils.readInputFromURL(urls[0]);
        Type listType = new TypeToken<List<Integer>>() {
        }.getType();
        //noinspection unchecked
        final List<Double> newsIdsDouble = Utils.loadJSON(inputStream, List.class);
        final List<Integer> newsIds = new ArrayList<>();
        for(Double s : newsIdsDouble) newsIds.add(s.intValue());

        appState.setTopStories(newsIds);

        for (final int storyId : newsIds.subList(0,2)) {
            Log.i(TAG, "Getting story " + storyId);
            String storyUrl = String.format(HackerNews.GET_STORY_URL, storyId);
            retrieveNewsStory(storyUrl);
        }

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return newsIds;
    }

    private void retrieveNewsStory(String storyUrl) {
        InputStream stream = Utils.readInputFromURL(storyUrl);
        if (stream != null) {
            News news = Utils.loadJSON(stream, News.class);
            Log.i(TAG, "Adding news to list: " + news.getTitle());
            this.getAppState().addNews(news);
        }

    }

    @Override
    protected void executeOnRetrieved(final List newsObjects, final AppState appState) {
        Log.i(TAG, "News list: " + appState.getShowNewsList());
        appState.getNewsList().setAdapter(new NewsAdapter(appState.getContext(), appState.getShowNewsList()));
    }

    @Override
    protected Class<List> getRetrievedClass() {
        return List.class;
    }

}