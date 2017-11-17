package hackernews;

import android.content.Context;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hackernews.JSONObject.News;

public class AppState {


    public void setTopStories(List<Integer> topStories) {
        this.topStories = topStories;
    }

    protected List<Integer> topStories = new ArrayList<>();
    protected List<News> showNewsList = Collections.synchronizedList(new ArrayList<News>());
    protected ListView newsList;
    protected Context context;

    public void addNews(News news) {
        showNewsList.add(news);
    }

    public List<News> getShowNewsList() {
        return showNewsList;
    }

    public AppState setNewsList(final ListView newsList) {
        this.newsList = newsList;
        return this;
    }

    public ListView getNewsList() {
        return newsList;
    }

    public Context getContext() {
        return context;
    }

    public AppState setContext(Context context) {
        this.context = context;
        return this;
    }

    public List<Integer> getTopStories() {
        return topStories;
    }
}
