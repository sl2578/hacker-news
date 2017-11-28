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

import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import branch.hackernews.JSONObject.Story;
import branch.hackernews.RetrieveFromAPI.RetrieveNewsStoryTask;
import branch.hackernews.RetrieveFromAPI.RetrieveTopStoriesTask;
import branch.hackernews.adapter.InfiniteScrollListener;
import branch.hackernews.adapter.NewsAdapter;
import branch.hackernews.pages.ViewComments;
import branch.hackernews.pages.ViewNewsPage;
import branch.hackernews.pages.ViewUser;

public class HackerNews extends AppCompatActivity {
    public final String TAG = HackerNews.class.getName();

    public final static String TOP_STORY_URL = "https://hacker-news.firebaseio.com/v0/topstories.json";
    public final static String GET_STORY_URL = "https://hacker-news.firebaseio.com/v0/item/%d.json";

    private ExpandableListView expandableListView;
    private NewsAdapter newsAdapter;

    // Complete list of top story ids from HackerNews
    private List<Integer> topStoryIds;
    // List of stories to display
    private List<Story> topStories;
    // Expanded story list view text
    private Map<Integer, List<String>> storyInfo;
    // Keep track of last retrieved story index
    private int offset = 0;
    // Three options for view more
    List<String> storyInfoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_stories);

        // Initialize newsInfoText list
        Log.i(TAG, "Initializing news info text");
        initializeNewsInfoText();

        Log.i(TAG, "Retrieving top stories from Hacker News");
        try {
            topStoryIds = new RetrieveTopStoriesTask(TOP_STORY_URL).execute().get();
        } catch (InterruptedException e) {
            // TODO: handle this
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Log.i(TAG, String.format(
                "Completed retrieval of top story ids %s, loading story data", topStoryIds.size()));
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
            public boolean onChildClick(ExpandableListView expandableListView, View view,
                                        int groupPosition, int childPosition, long id) {
                final String selectedChild = (String) expandableListView
                        .getExpandableListAdapter().getChild(groupPosition, childPosition);
                Story selectedNews = topStories.get(groupPosition);
                Intent intent = null;
                switch(selectedChild) {
                    case "View Article":
                        Log.i(TAG, "Opening story in browser: " + selectedNews.getTitle());
                        intent = new Intent(context, ViewNewsPage.class);
                        // TODO: replace hardcoded name with variable
                        intent.putExtra("title", selectedNews.getTitle());
                        intent.putExtra("url", selectedNews.getUrl());
                        break;
                    case "View User":
                        Log.i(TAG, "View user info: " + selectedNews.getUser());
                        intent = new Intent(context, ViewUser.class);
                        intent.putExtra("user", selectedNews.getUser());
                        break;
                    case "View Comments":
                        Log.i(TAG, "View comments for " + selectedNews.getTitle());
                        intent = new Intent(context, ViewComments.class);
                        intent.putIntegerArrayListExtra("comments", (ArrayList<Integer>) selectedNews.getKids());
                        intent.putExtra("title", selectedNews.getTitle());
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
        newsAdapter = new NewsAdapter(this, topStories, storyInfo);
        expandableListView.setAdapter(newsAdapter);
    }

    public void getStoryFromHackerNews() {
        new RetrieveNewsStoryTask(this, topStoryIds, GET_STORY_URL, offset).execute();
    }

    private void initializeNewsInfoText() {
        storyInfoText = new ArrayList<>();
        storyInfoText.add("View Article");
        storyInfoText.add("View User");
        storyInfoText.add("View Comments");
    }


    /**
     * Given a map of story id to json,
     * @param storyJsons
     */
    public void loadStoriesFromJSON(Map<Integer, String> storyJsons) {
        int numStories = storyJsons.size();
        Log.d(TAG, String.format("Parsing JSON to story objects, %s retrieved", numStories));
        offset += numStories;
        for (Map.Entry<Integer, String> storyJson : storyJsons.entrySet()) {
            String json = storyJson.getValue();
            try {
                Story story = Utils.loadJSON(json, Story.class);
                topStories.add(story);
                storyInfo.put(storyJson.getKey(), storyInfoText);
            } catch (JsonSyntaxException e) {
                Log.w(TAG, "Error while loading JSON to story object, skipping: " + json, e);
            }
        }

        Log.i(TAG, "Top news stories retrieved, displaying " + topStories.size());
        newsAdapter.notifyDataSetChanged();

    }
}