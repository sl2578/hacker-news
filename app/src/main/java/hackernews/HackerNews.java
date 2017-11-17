package hackernews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import hackernews.JSONObject.News;
import branch.hackernews.R;

public class HackerNews extends AppCompatActivity {
    public final String TAG = "HackerNews";

    public final static String TOP_STORY_URL = "https://hacker-news.firebaseio.com/v0/topstories.json";
    public final static String GET_STORY_URL = "https://hacker-news.firebaseio.com/v0/item/%d.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_stories);

        final AppState appState = new AppState()
                .setNewsList((ListView) findViewById(R.id.news_list))
                .setContext(this);

        new RetrieveTopStoriesTask(appState).execute(TOP_STORY_URL);

        appState.getNewsList().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News selectedNews = appState.getShowNewsList().get(i);
                Intent viewIntent = new Intent(HackerNews.this, ViewNewsPage.class);

                viewIntent.putExtra("title", selectedNews.getTitle());
                viewIntent.putExtra("url", selectedNews.getUrl());

                startActivity(viewIntent);
            }
        });
    }
}