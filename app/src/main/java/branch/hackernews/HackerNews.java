package branch.hackernews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;

import branch.hackernews.JSONObject.Story;
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

        ((ExpandableListView) appState.getView()).setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view,
                                        int groupPosition, int childPosition, long id) {
                final String selectedChild = (String) ((ExpandableListView) appState.getView())
                        .getExpandableListAdapter().getChild(groupPosition, childPosition);
                Story selectedNews = appState.getShowStoryList().get(groupPosition);
                Intent intent = null;
                switch(selectedChild) {
                    case "View Article":
                        intent = new Intent(appState.getContext(), ViewNewsPage.class);
                        intent.putExtra("title", selectedNews.getTitle());
                        intent.putExtra("url", selectedNews.getUrl());
                        break;
                    case "View User":
                        intent = new Intent(appState.getContext(), ViewUser.class);
                        intent.putExtra("user", selectedNews.getUser());
                        break;
                    case "View Comments":
                        // TODO
                        intent = new Intent(appState.getContext(), ViewComments.class);
                        break;
                }
                startActivity(intent);
                return true;
            }
        });
    }
}