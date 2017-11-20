package branch.hackernews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import branch.hackernews.JSONObject.Story;
import branch.hackernews.RetrieveFromAPI.RetrieveNewsStoryTask;
import branch.hackernews.adapter.InfiniteScrollListener;
import branch.hackernews.pages.ViewComments;
import branch.hackernews.pages.ViewUser;
import branch.hackernews.RetrieveFromAPI.RetrieveTopStoriesTask;
import branch.hackernews.pages.ViewNewsPage;

public class HackerNews extends AppCompatActivity {
    public final String TAG = HackerNews.class.getName();

    public final static String TOP_STORY_URL = "https://hacker-news.firebaseio.com/v0/topstories.json";
    public final static String GET_STORY_URL = "https://hacker-news.firebaseio.com/v0/item/%d.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_stories);
        final AppState appState = new AppState()
                .setView(findViewById(R.id.news_list))
                .setContext(this);

        new RetrieveTopStoriesTask(appState).execute(TOP_STORY_URL);

        ((ExpandableListView) appState.getView()).setOnScrollListener(new InfiniteScrollListener() {
            @Override
            public boolean loadMore(int page, int totalItemCount) {
                Log.i(TAG, "Loading more stories, current story count: " + totalItemCount);
                new RetrieveNewsStoryTask(appState).execute(GET_STORY_URL);
                return true;
            }
        });

        ((ExpandableListView) appState.getView()).setOnChildClickListener(
                new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view,
                                        int groupPosition, int childPosition, long id) {
                final String selectedChild = (String) expandableListView
                        .getExpandableListAdapter().getChild(groupPosition, childPosition);
                Story selectedNews = appState.getShowStoryList().get(groupPosition);
                Intent intent = null;
                switch(selectedChild) {
                    case "View Article":
                        Log.i(TAG, "Opening story in browser: " + selectedNews.getTitle());
                        intent = new Intent(appState.getContext(), ViewNewsPage.class);
                        // TODO: replace hardcoded name with variable
                        intent.putExtra("title", selectedNews.getTitle());
                        intent.putExtra("url", selectedNews.getUrl());
                        break;
                    case "View User":
                        Log.i(TAG, "View user info: " + selectedNews.getUser());
                        intent = new Intent(appState.getContext(), ViewUser.class);
                        intent.putExtra("user", selectedNews.getUser());
                        break;
                    case "View Comments":
                        Log.i(TAG, "View comments for " + selectedNews.getTitle());
                        intent = new Intent(appState.getContext(), ViewComments.class);
                        intent.putIntegerArrayListExtra("comments", (ArrayList<Integer>) selectedNews.getKids());
                        intent.putExtra("title", selectedNews.getTitle());
                        break;
                }
                startActivity(intent);
                return true;
            }
        });
    }
}