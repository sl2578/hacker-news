package branch.hackernews.pages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import branch.hackernews.AppState;
import branch.hackernews.HackerNews;
import branch.hackernews.R;
import branch.hackernews.RetrieveFromAPI.RetrieveCommentsTask;


public class ViewComments extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comments);

        Intent intent = getIntent();
        List<Integer> comments = intent.getIntegerArrayListExtra("comments");
        String title = intent.getStringExtra("title");

        setTitle(title);

        final AppState appState = new AppState()
                .setView(findViewById(R.id.comments_list))
                .setIdsList(comments)
                .setContext(this);

        new RetrieveCommentsTask(appState).execute(HackerNews.GET_STORY_URL);
    }
}
