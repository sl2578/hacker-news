package branch.hackernews.RetrieveFromAPI;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import branch.hackernews.JSONObject.User;
import branch.hackernews.api.HackerRankAPIInterface;
import branch.hackernews.pages.ViewUser;
import retrofit2.Call;

public class RetrieveUserTask extends AsyncTask<String, Void, User> {
    private final String TAG = RetrieveUserTask.class.getName();

    private final HackerRankAPIInterface apiService;
    private final Activity parentActivity;

    public RetrieveUserTask(ViewUser viewUserActivity,
                            HackerRankAPIInterface apiService) {
        this.apiService = apiService;
        this.parentActivity = viewUserActivity;
    }

    @Override
    protected User doInBackground(String... user) {
        String username = user[0];
        try {
            Log.i(TAG, "Retrieving data for " + username);
            Call<User> call = apiService.getUser(username);
            return call.execute().body();
        } catch (IOException e) {
            Log.w(TAG, "Failed to get user " + username);
            return null;
        }
    }

    @Override
    protected void onPostExecute(User user) {
        ((ViewUser) parentActivity).setTextView(user);
    }
}
