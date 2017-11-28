//package branch.hackernews.RetrieveFromAPI;
//
//import android.text.Html;
//import android.text.format.DateUtils;
//import android.view.View;
//import android.widget.TextView;
//
//import java.util.List;
//
//import branch.hackernews.AppState;
//import branch.hackernews.JSONObject.Comment;
//import branch.hackernews.R;
//import branch.hackernews.Utils;
//import branch.hackernews.api.HackerRankAPIInterface;
//import branch.hackernews.pages.ViewComments;
//
//public class RetrieveCommentRepliesTask extends RetrieveCommentsTask {
//    View parentView;
//
//    public RetrieveCommentRepliesTask(ViewComments viewCommentsActivity, HackerRankAPIInterface apiService, List<Integer> commentIds) {
//        super(viewCommentsActivity, apiService, commentIds);
//    }
//
//
//    @Override
//    protected void onPostExecute(AppState appState) {
//        Comment comment = appState.getShowCommentsList().get(0);
//
//        TextView user = parentView.findViewById(R.id.user);
//        TextView created_date = parentView.findViewById(R.id.created_date);
//        TextView comment_text = parentView.findViewById(R.id.comment_text);
//
//        user.setText(comment.getBy());
//        created_date.setText(Utils.timeSince(
//                DateUtils.SECOND_IN_MILLIS * comment.getTime(), System.currentTimeMillis()));
//        comment_text.setText(Html.fromHtml(comment.getText()));
//    }
//}
