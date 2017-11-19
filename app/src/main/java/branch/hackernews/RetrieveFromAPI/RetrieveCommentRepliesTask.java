package branch.hackernews.RetrieveFromAPI;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import branch.hackernews.AppState;
import branch.hackernews.JSONObject.Comment;
import branch.hackernews.R;
import branch.hackernews.Utils;

public class RetrieveCommentRepliesTask extends RetrieveCommentsTask {
    View parentView;

    public RetrieveCommentRepliesTask(AppState appState, View parentView) {
        super(appState);
        this.parentView = parentView;
    }

    @Override
    protected void onPostExecute(AppState appState) {
        Comment comment = appState.getShowCommentsList().get(0);

        TextView user = parentView.findViewById(R.id.user);
        TextView created_date = parentView.findViewById(R.id.created_date);
        TextView comment_text = parentView.findViewById(R.id.comment_text);

        user.setText(comment.getBy());
        created_date.setText(Utils.timeSince(comment.getTime(), System.currentTimeMillis()/1000));
        comment_text.setText(Html.fromHtml(comment.getText()));
    }
}
