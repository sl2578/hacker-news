package hackernews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;

import branch.hackernews.R;
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
                return false;
            }

//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Story selectedNews = appState.getShowStoryList().get(i);
//                Intent viewIntent = new Intent(HackerNews.this, ViewNewsPage.class);
//
//                viewIntent.putExtra("title", selectedNews.getTitle());
//                viewIntent.putExtra("url", selectedNews.getUrl());
//
//                startActivity(viewIntent);
//            }
        });
    }
}