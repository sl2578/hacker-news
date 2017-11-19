package branch.hackernews.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import branch.hackernews.AppState;
import branch.hackernews.HackerNews;
import branch.hackernews.JSONObject.Comment;
import branch.hackernews.R;
import branch.hackernews.RetrieveFromAPI.RetrieveCommentRepliesTask;
import branch.hackernews.RetrieveFromAPI.RetrieveCommentsTask;
import branch.hackernews.Utils;

public class CommentsAdapter extends BaseExpandableListAdapter {
    public final String TAG = CommentsAdapter.class.getName();

    private Context context;
    private List<Comment> comments;
    private Map<Integer, List<Integer>> commentResponses;
    private LayoutInflater layoutInflater;

    public CommentsAdapter(Context context,
                           List<Comment> comments,
                           Map<Integer, List<Integer>> commentResponses) {
        this.context = context;
        this.comments = comments;
        this.commentResponses = commentResponses;
        this.layoutInflater = (LayoutInflater)
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getGroupView(int groupPosition,
                             boolean isExpanded,
                             View convertView,
                             ViewGroup parent) {
        Comment comment = (Comment) getGroup(groupPosition);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.comments_row, null);
        }

        TextView user = convertView.findViewById(R.id.user);
        TextView created_date = convertView.findViewById(R.id.date);
        TextView comment_text = convertView.findViewById(R.id.comment_text);

        user.setText(comment.getBy());
        created_date.setText(Utils.unixToTime(comment.getTime()));
        //TODO: html escaping
        comment_text.setText(comment.getText());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition,
                             int childPosition,
                             boolean isLastChild,
                             View convertView,
                             ViewGroup parent) {
        Comment comment = (Comment) getGroup(groupPosition);

        final AppState appState = new AppState()
                .setView(convertView)
                .setIdsList(Collections.singletonList(comment.getKids().get(childPosition)))
                .setContext(this.context);

        try {
            new RetrieveCommentRepliesTask(appState, convertView).execute(HackerNews.GET_STORY_URL).get();
        } catch (InterruptedException e) {
            Log.e(TAG, "Interrupted while retrieving comment replies for " + comment.getId());
        } catch (ExecutionException e) {
            Log.e(TAG, "Error while retrieving comment replies for " + comment.getId(), e);
        }

        return convertView;

    }

    @Override
    public int getGroupCount() {
        return this.comments.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.commentResponses.get(this.comments.get(groupPosition).getId()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.comments.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.commentResponses.get(this.comments.get(groupPosition).getId()).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
