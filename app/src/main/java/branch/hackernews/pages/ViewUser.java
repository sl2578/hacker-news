package branch.hackernews.pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.TextView;

import branch.hackernews.HackerNewsMainActivity;
import branch.hackernews.JSONObject.User;
import branch.hackernews.R;
import branch.hackernews.RetrieveFromAPI.RetrieveUserTask;
import branch.hackernews.Utils;
import branch.hackernews.api.HackerNewsAPIClient;
import branch.hackernews.api.HackerNewsAPIInterface;

public class ViewUser extends AppCompatActivity {
    private final String TAG = ViewUser.class.getName();

    private HackerNewsAPIInterface apiService;
    private TextView user_name;
    private TextView created_date;
    private TextView karma_points;
    private TextView about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        user_name = findViewById(R.id.user);
        created_date = findViewById(R.id.created_date);
        karma_points = findViewById(R.id.karma_points);
        about = findViewById(R.id.about);

        apiService = HackerNewsAPIClient.getClient();

        Intent intent = getIntent();
        final String user = intent.getStringExtra(HackerNewsMainActivity.USER_FIELD);

        Log.i(TAG, "Retrieving user data for " + user);

        new RetrieveUserTask(this, apiService).execute(user);
    }

    public void setTextView(User user) {
        user_name.setText(user.getId());
        created_date.setText(Utils.timeSince(
                DateUtils.SECOND_IN_MILLIS * user.getCreated(), System.currentTimeMillis()));
        karma_points.setText(String.valueOf(user.getKarma()));
        if (user.getAbout() != null) {
            about.setText(Html.fromHtml(user.getAbout()));
        }
    }
}
