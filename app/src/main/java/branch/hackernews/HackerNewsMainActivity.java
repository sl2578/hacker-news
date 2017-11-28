package branch.hackernews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import branch.hackernews.JSONObject.Story;
import branch.hackernews.RetrieveFromAPI.RetrieveNewsStoryTask;
import branch.hackernews.RetrieveFromAPI.RetrieveTopStoriesTask;
import branch.hackernews.adapter.InfiniteScrollListener;
import branch.hackernews.adapter.StoryAdapter;
import branch.hackernews.api.HackerNewsAPIClient;
import branch.hackernews.api.HackerNewsAPIInterface;
import branch.hackernews.pages.ViewComments;
import branch.hackernews.pages.ViewNewsPage;
import branch.hackernews.pages.ViewUser;

public class HackerNewsMainActivity extends AppCompatActivity {
    private final String TAG = HackerNewsMainActivity.class.getName();

    public final static String TITLE_FIELD = "title";
    public final static String URL_FIELD = "url";
    public final static String USER_FIELD = "user";
    public final static String COMMENTS_FIELD = "comments";

    private ExpandableListView expandableListView;
    private StoryAdapter storyAdapter;

    // Complete list of top story ids from HackerNewsMainActivity
    private List<Integer> topStoryIds = new ArrayList<>();
    // List of stories to display
    private List<Story> topStories;
    // Expanded story list view text
    private Map<Integer, List<String>> storyInfo;
    // Keep track of last retrieved story index
    private int offset = 0;
    // Three options for view more
    private List<String> storyInfoTextWithComments;
    private List<String> storyInfoTextWithOutComments;

    private HackerNewsAPIInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_stories);

        // Initialize newsInfoText list
        Log.i(TAG, "Initializing news info text");
        initializeNewsInfoText();

        Log.i(TAG, "Retrieving top stories from Hacker News");
        apiService = HackerNewsAPIClient.getClient();
        topStoryIds = getTopStoriesFromHackerNews();

        Log.i(TAG, String.format("Completed retrieval of top story ids %s, " +
                        "loading story data", topStoryIds.size()));
        expandableListView = findViewById(R.id.news_list);
        setListAdapterView();
        getStoryFromHackerNews();
        expandableListView.setOnScrollListener(onScrollListener());
        expandableListView.setOnChildClickListener(onChildClickListener());
    }

    private OnChildClickListener onChildClickListener() {
        final Context context = this;
        return new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView,
                                        View view,
                                        int groupPosition,
                                        int childPosition,
                                        long id) {
                final String selectedChild = (String) expandableListView
                        .getExpandableListAdapter()
                        .getChild(groupPosition, childPosition);
                final Story selectedNews = topStories.get(groupPosition);
                Intent intent = null;
                switch(selectedChild) {
                    case "View Article":
                        Log.i(TAG, "Opening story in browser: "
                                + selectedNews.getTitle());
                        intent = new Intent(context, ViewNewsPage.class);
                        intent.putExtra(TITLE_FIELD, selectedNews.getTitle());
                        intent.putExtra(URL_FIELD, selectedNews.getUrl());
                        break;
                    case "View User":
                        Log.i(TAG, "View user info: "
                                + selectedNews.getUser());
                        intent = new Intent(context, ViewUser.class);
                        intent.putExtra(USER_FIELD, selectedNews.getUser());
                        break;
                    case "View Comments":
                        Log.i(TAG, "View comments for "
                                + selectedNews.getTitle());
                        intent = new Intent(context, ViewComments.class);
                        intent.putIntegerArrayListExtra(COMMENTS_FIELD,
                                (ArrayList<Integer>) selectedNews.getKids());
                        intent.putExtra(TITLE_FIELD, selectedNews.getTitle());
                        break;
                }
                startActivity(intent);
                return true;
            }
        };
    }

    private OnScrollListener onScrollListener() {
        return new InfiniteScrollListener() {
            @Override
            public boolean loadMore(int currentFirstVisibleItem, int totalItemCount) {
                Log.i(TAG, "Loading more stories, current story count: " + totalItemCount);
                getStoryFromHackerNews();
                return true;
            }
        };
    }

    private void setListAdapterView() {
        topStories = new ArrayList<>();
        storyInfo = new HashMap<>();
        storyAdapter = new StoryAdapter(this, topStories, storyInfo);
        expandableListView.setAdapter(storyAdapter);
    }

    public List<Integer> getTopStoriesFromHackerNews() {
        try {
            return new RetrieveTopStoriesTask(apiService).execute().get();
        } catch (InterruptedException e) {
            Log.w(TAG, "Interrupted while retrieving top stories", e);
        } catch (ExecutionException e) {
            Log.e(TAG, "Error while retrieving top stories", e);
        }
        return Collections.emptyList();
    }

    public void getStoryFromHackerNews() {
        new RetrieveNewsStoryTask(this, topStoryIds, offset, apiService)
                .execute();
    }

    private void initializeNewsInfoText() {
        storyInfoTextWithOutComments = new ArrayList<>();
        storyInfoTextWithOutComments.add("View Article");
        storyInfoTextWithOutComments.add("View User");

        storyInfoTextWithComments = new ArrayList<>(storyInfoTextWithOutComments);
        storyInfoTextWithComments.add("View Comments");
    }

    /**
     * Called by {@link RetrieveNewsStoryTask} to load the {@link Story} object and it's child options.
     * Only show "View Comments" when a story has comments
     *
     * @param stories map containing story id as key and story object
     */
    public void loadStories(Map<Integer, Story> stories) {
        final int numStories = stories.size();
        Log.d(TAG, String.format("Loading stories to view, %s retrieved",
                numStories));
        offset += numStories;
        for (Map.Entry<Integer, Story> storyEntry : stories.entrySet()) {
            Story story = storyEntry.getValue();
            topStories.add(story);
            storyInfo.put(storyEntry.getKey(),
                    story.getNumDescendants() > 0 ?
                            storyInfoTextWithComments : storyInfoTextWithOutComments);
        }

        Log.i(TAG, "Top news stories retrieved, displaying " + topStories.size());
        storyAdapter.notifyDataSetChanged();

    }
}