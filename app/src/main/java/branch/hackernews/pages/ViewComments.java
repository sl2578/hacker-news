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

import branch.hackernews.HackerNewsMainActivity;
import branch.hackernews.JSONObject.Comment;
import branch.hackernews.R;
import branch.hackernews.RetrieveFromAPI.RetrieveCommentsTask;
import branch.hackernews.adapter.CommentsAdapter;
import branch.hackernews.api.HackerNewsAPIClient;
import branch.hackernews.api.HackerNewsAPIInterface;

/**
 * Activity containing {@link ExpandableListView} of {@link Comment} objects.
 * Called by either {@link HackerNewsMainActivity} or itself to load comments associated
 * with the Hacker News API items (i.e. Story, Comments)
 */
public class ViewComments extends AppCompatActivity {
    private final String TAG = ViewComments.class.getName();

    private ExpandableListView expandableListView;
    private CommentsAdapter commentsAdapter;

    private List<Integer> commentIds;
    private List<Comment> comments;
    private Map<Integer, String> commentResponses;
    private String title;

    private HackerNewsAPIInterface apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comments);

        apiService = HackerNewsAPIClient.getClient();

        // Get intent as sent from either HackerNewsMainActivity or ViewComments
        Intent intent = getIntent();
        title = intent.getStringExtra(HackerNewsMainActivity.TITLE_FIELD);
        setTitle("Comments for " + title);
        commentIds = intent.getIntegerArrayListExtra(HackerNewsMainActivity.COMMENTS_FIELD);

        expandableListView = findViewById(R.id.comments_list);
        setListAdapterView();

        Log.i(TAG, String.format("Retrieving %s comments for %s",
                commentIds.size(), title));
        getCommentsFromHackerNews();

        expandableListView.setOnChildClickListener(onChildClickListener());
    }

    /**
     * Clicking on "View Replies" for comment object will load
     * a new comments page with the comment's children
     * @return true if child click is successful
     */
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
                intent.putIntegerArrayListExtra(HackerNewsMainActivity.COMMENTS_FIELD,
                        (ArrayList<Integer>) comment.getKids());
                intent.putExtra(HackerNewsMainActivity.TITLE_FIELD, title);
                startActivity(intent);
                return true;
            }
        };
    }

    /**
     * Initialize comments and commentResponses and create an initial adapter
     */
    private void setListAdapterView() {
        comments = new ArrayList<>();
        commentResponses = new HashMap<>();
        commentsAdapter = new CommentsAdapter(this, comments, commentResponses);
        expandableListView.setAdapter(commentsAdapter);
    }

    /**
     * Call AsyncTask to retrieve comments by list of ids
     */
    public void getCommentsFromHackerNews() {
        new RetrieveCommentsTask(this, apiService, commentIds).execute();
    }

    /**
     * Called by {@link RetrieveCommentsTask} to load the comments object and
     * a string that indicates whether a comment has a reply into the
     * {@link CommentsAdapter}
     * @param commentsMap map containing comment id as key and comments object
     */
    public void loadComments(Map<Integer, Comment> commentsMap) {
        final int numComments = commentsMap.size();
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
