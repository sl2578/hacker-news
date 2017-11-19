package branch.hackernews.RetrieveFromAPI;

import android.util.Log;
import android.widget.ExpandableListView;

import com.google.gson.JsonSyntaxException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import branch.hackernews.AppState;
import branch.hackernews.JSONObject.Comment;
import branch.hackernews.Utils;
import branch.hackernews.adapter.CommentsAdapter;

public class RetrieveCommentsTask  extends RetrieveFromAPITask<AppState> {

    public RetrieveCommentsTask(AppState appState) {
        super(appState);
    }

    @Override
    protected AppState doInBackground(String... urls) {
        List<Integer> commentIds = this.getAppState().getIdsList();
        for (int commentId : commentIds) {
            String url = String.format(urls[0], commentId);
            retrieveComment(url);
        }
        return getAppState();
    }

    private void retrieveComment(String url) {
        String input = Utils.fetchResource(TAG, url);

        if (input == null) {
            Log.w(TAG, "Input stream from API endpoint is empty: " + url);
            return;
        }

        try {
            Comment comment = Utils.loadJSON(input, Comment.class);
            this.getAppState().addComment(comment);
        } catch (JsonSyntaxException e) {
            Log.e(TAG, "Unable to parse input as JSON" + input, e);
        }
    }

    @Override
    protected void onPostExecute(AppState appState) {
        Log.i(TAG, "Comments retrieved, displaying " + appState.getShowCommentsList().size());
        Map<Integer, String> commentResponses = new HashMap<>();

        for (Comment comment : appState.getShowCommentsList()) {
            if (comment.getKids() != null && comment.getKids().size() > 0) {
                commentResponses.put(comment.getId(), "View Replies");
            }
        }
        ((ExpandableListView) appState.getView()).setAdapter(
                new CommentsAdapter(appState.getContext(), appState.getShowCommentsList(), commentResponses));
    }

    @Override
    protected Class<AppState> getRetrievedClass() {
        return null;
    }
}
