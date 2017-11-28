package branch.hackernews.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import branch.hackernews.JSONObject.Comment;
import branch.hackernews.R;
import branch.hackernews.RetrieveFromAPI.RetrieveCommentsTask;
import branch.hackernews.adapter.CommentsAdapter;
import branch.hackernews.api.HackerRankAPIClient;
import branch.hackernews.api.HackerRankAPIInterface;


public class ViewComments extends AppCompatActivity {
    private final String TAG = ViewComments.class.getName();

    ExpandableListView expandableListView;
    private CommentsAdapter commentsAdapter;

    private List<Integer> commentIds;
    private List<Comment> comments;
    private Map<Integer, String> commentResponses;
    private String title;

    private HackerRankAPIInterface apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comments);

        apiService = HackerRankAPIClient.getClient();

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        setTitle(title);
        commentIds = intent.getIntegerArrayListExtra("comments");

        expandableListView = findViewById(R.id.comments_list);
        setListAdapterView();
        getCommentsFromHackerNews();

        expandableListView.setOnChildClickListener(onChildClickListener());
    }

    private OnChildClickListener onChildClickListener() {
        final Context context = this;
        return new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView,
                                        View view,
                                        int groupPosition,
                                        int childPosition,
                                        long id) {
                final Comment comment = comments.get(groupPosition);

                Log.i(TAG, "Showing comments for " + comment.getId());

                Intent intent = new Intent(context, ViewComments.class);
                intent.putIntegerArrayListExtra("comments",
                        (ArrayList<Integer>) comment.getKids());
                intent.putExtra("title", title);
                startActivity(intent);
                return true;
            }
        };
    }

    private void setListAdapterView() {
        comments = new ArrayList<>();
        commentResponses = new HashMap<>();
        commentsAdapter = new CommentsAdapter(this, comments, commentResponses);
        expandableListView.setAdapter(commentsAdapter);
    }

    public void getCommentsFromHackerNews() {
        new RetrieveCommentsTask(this, apiService, commentIds).execute();
    }

    public void loadComments(Map<Integer, Comment> commentsMap) {
        int numComments = commentsMap.size();
        Log.i(TAG, String.format("Loading comments to view, %s retrieved",
                numComments));
        for (Entry<Integer, Comment> commentEntry : commentsMap.entrySet()) {
            Comment comment = commentEntry.getValue();
            comments.add(comment);
            if (comment.getKids() != null && comment.getKids().size() > 0) {
                commentResponses.put(comment.getId(), "View Replies");
            }
        }
        Log.i(TAG, "Comments retrieved, displaying " + comments.size());
        commentsAdapter.notifyDataSetChanged();
    }
}
