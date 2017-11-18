package branch.hackernews.pages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import branch.hackernews.RetrieveFromAPI.RetrieveUserTask;
import branch.hackernews.R;
import branch.hackernews.AppState;

public class ViewUser extends AppCompatActivity {
    public final static String GET_USER_URL = "https://hacker-news.firebaseio.com/v0/user/%s.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        final AppState appState = new AppState()
                .setContext(this);

        Intent intent = getIntent();
        String user = intent.getStringExtra("user");
        String url = String.format(GET_USER_URL, user);
        new RetrieveUserTask(appState, this).execute(url);
    }
}
