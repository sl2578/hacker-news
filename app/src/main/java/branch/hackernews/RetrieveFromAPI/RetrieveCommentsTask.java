package branch.hackernews.RetrieveFromAPI;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import branch.hackernews.JSONObject.Comment;
import branch.hackernews.api.HackerNewsAPIInterface;
import branch.hackernews.pages.ViewComments;
import retrofit2.Call;

public class RetrieveCommentsTask extends AsyncTask<Void, Void, Map<Integer, Comment>> {
    private final String TAG = RetrieveCommentsTask.class.getName();

    private final Activity parentActivity;
    private final List<Integer> commentIds;
    private final HackerNewsAPIInterface apiService;


    public RetrieveCommentsTask(ViewComments viewCommentsActivity,
                                HackerNewsAPIInterface apiService,
                                List<Integer> commentIds) {
        this.parentActivity = viewCommentsActivity;
        this.apiService = apiService;
        this.commentIds = commentIds;
    }

    @Override
    protected Map<Integer, Comment> doInBackground(Void... voids) {
        Map<Integer, Comment> commentsMap = new HashMap<>();
        for (int commentId : commentIds) {
            try {
                commentsMap.put(commentId, retrieveComment(commentId));
            } catch (IOException e) {
                Log.w(TAG, "Unable to retrieve comment from HackerNewsMainActivity API: "
                        + commentId);
            }
        }
        return commentsMap;
    }

    @Override
    protected void onPostExecute(Map<Integer, Comment> commentsMap) {
        ((ViewComments) parentActivity).loadComments(commentsMap);
    }

    private Comment retrieveComment(int commentId) throws IOException {
        Call<Comment> call = apiService.getComment(commentId);
        return call.execute().body();
    }
}
