package hackernews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;

import branch.hackernews.R;
import hackernews.JSONObject.Story;
import hackernews.RetrieveFromAPI.RetrieveTopStoriesTask;

public class HackerNews extends AppCompatActivity {
    public final String TAG = "HackerNews";

    public final static String TOP_STORY_URL = "https://hacker-news.firebaseio.com/v0/topstories.json";
    public final static String GET_STORY_URL = "https://hacker-news.firebaseio.com/v0/item/%d.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_stories);

        final AppState appState = new AppState()
                .setNewsList((ExpandableListView) findViewById(R.id.news_list))
                .setContext(this);

        new RetrieveTopStoriesTask(appState).execute(TOP_STORY_URL);

        appState.getNewsList().setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view,
                                        int groupPosition, int childPosition, long id) {
                final String selectedChild = (String) appState.getNewsList()
                        .getExpandableListAdapter().getChild(groupPosition, childPosition);
                Story selectedNews = appState.getShowStoryList().get(groupPosition);
                Intent intent = null;
                switch(selectedChild) {
                    case "View Article":
                        intent = new Intent(HackerNews.this, ViewNewsPage.class);
                        intent.putExtra("title", selectedNews.getTitle());
                        intent.putExtra("url", selectedNews.getUrl());
                    case "View User":
                        break;
                    case "View Comments":
                        break;
                }
                startActivity(intent);
                return true;
            }
        });
    }
}