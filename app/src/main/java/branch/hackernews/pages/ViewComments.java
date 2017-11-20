package branch.hackernews.pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import branch.hackernews.AppState;
import branch.hackernews.HackerNews;
import branch.hackernews.JSONObject.Comment;
import branch.hackernews.R;
import branch.hackernews.RetrieveFromAPI.RetrieveCommentsTask;


public class ViewComments extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comments);

        Intent intent = getIntent();
        List<Integer> comments = intent.getIntegerArrayListExtra("comments");
        String title = intent.getStringExtra("title");

        setTitle(title);

        final AppState appState = new AppState()
                .setView(findViewById(R.id.comments_list))
                .setIdsList(comments)
                .setContext(this);

        new RetrieveCommentsTask(appState).execute(HackerNews.GET_STORY_URL);

        ((ExpandableListView) appState.getView()).setOnChildClickListener(
                new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view,
                                        int groupPosition, int childPosition, long id) {
                Comment comment = appState.getShowCommentsList().get(groupPosition);

                Intent intent = new Intent(appState.getContext(), ViewComments.class);
                intent.putIntegerArrayListExtra("comments", (ArrayList<Integer>) comment.getKids());
                intent.putExtra("title", comment.getText());
                startActivity(intent);
                return true;
            }
        });
    }
}
