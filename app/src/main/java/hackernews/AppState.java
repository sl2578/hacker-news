package hackernews;

import android.content.Context;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hackernews.JSONObject.Story;

public class AppState {


    public void setTopStories(List<Integer> topStories) {
        this.topStories = topStories;
    }

    protected List<Integer> topStories = new ArrayList<>();
    protected List<Story> showStoryList = Collections.synchronizedList(new ArrayList<Story>());
    protected ExpandableListView newsList;
    protected Context context;

    public void addNews(Story story) {
        showStoryList.add(story);
    }

    public List<Story> getShowStoryList() {
        return showStoryList;
    }

    public AppState setNewsList(final ExpandableListView newsList) {
        this.newsList = newsList;
        return this;
    }

    public ExpandableListView getNewsList() {
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
