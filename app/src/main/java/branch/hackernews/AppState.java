package branch.hackernews;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import branch.hackernews.JSONObject.Story;

public class AppState {


    public void setTopStories(List<Integer> topStories) {
        this.topStories = topStories;
    }

    protected List<Integer> topStories = new ArrayList<>();
    protected List<Story> showStoryList = Collections.synchronizedList(new ArrayList<Story>());
    protected View view;
    protected Context context;

    public void addNews(Story story) {
        showStoryList.add(story);
    }

    public List<Story> getShowStoryList() {
        return showStoryList;
    }

    public AppState setView(final View view) {
        this.view = view;
        return this;
    }

    public View getView() {
        return view;
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
