package branch.hackernews.adapter;

import android.content.Context;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import branch.hackernews.JSONObject.Comment;
import branch.hackernews.R;
import branch.hackernews.Utils;

public class CommentsAdapter extends BaseExpandableListAdapter {
    public final String TAG = CommentsAdapter.class.getName();

    private Context context;
    private List<Comment> comments;
    private Map<Integer, String> commentResponses;
    private LayoutInflater layoutInflater;

    public CommentsAdapter(Context context,
                           List<Comment> comments,
                           Map<Integer, String> commentResponses) {
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

        if (isExpanded) {
            convertView = layoutInflater.inflate(R.layout.comments_row_full, null);
        }
        else {
            convertView = layoutInflater.inflate(R.layout.comments_row_shortened, null);
        }

        TextView user = convertView.findViewById(R.id.user);
        TextView created_date = convertView.findViewById(R.id.date);
        TextView comment_text = convertView.findViewById(R.id.comment_text);

        user.setText(comment.getBy());
        created_date.setText(Utils.timeSince(
                DateUtils.SECOND_IN_MILLIS * comment.getTime(), System.currentTimeMillis()));
        if (comment.getText() != null) {
            comment_text.setText(Html.fromHtml(comment.getText()));
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition,
                             int childPosition,
                             boolean isLastChild,
                             View convertView,
                             ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.comments_row_info, null);
        }
        TextView viewMoreText = convertView.findViewById(R.id.comment_info);
        viewMoreText.setText(childText);

//        final AppState appState = new AppState()
//                .setView(convertView)
//                .setIdsList(Collections.singletonList(comment.getKids().get(childPosition)))
//                .setContext(this.context);

//        try {
//            new RetrieveCommentRepliesTask(appState, convertView).execute(HackerNews.GET_STORY_URL).get();
//        } catch (InterruptedException e) {
//            Log.e(TAG, "Interrupted while retrieving comment replies for " + comment.getId());
//        } catch (ExecutionException e) {
//            Log.e(TAG, "Error while retrieving comment replies for " + comment.getId(), e);
//        }

        return convertView;

    }

    @Override
    public int getGroupCount() {
        return this.comments.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.commentResponses.containsKey(this.comments.get(groupPosition).getId()) ? 1 : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.comments.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.commentResponses.get(this.comments.get(groupPosition).getId());
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
