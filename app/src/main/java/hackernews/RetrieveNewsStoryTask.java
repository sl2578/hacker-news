package hackernews;

import android.util.Log;

import hackernews.JSONObject.News;

public class RetrieveNewsStoryTask extends RetrieveFromAPITask<News> {
    public RetrieveNewsStoryTask(AppState appState) {
        super(appState);
    }

    @Override
    protected void executeOnRetrieved(final News news, final AppState appState) {
        Log.i(TAG, "Adding news to list: " + news.getTitle());
        appState.addNews(news);
    }

    @Override
    protected Class<News> getRetrievedClass() {
        return News.class;
    }
}