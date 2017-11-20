package branch.hackernews;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import branch.hackernews.JSONObject.Comment;
import branch.hackernews.JSONObject.Story;

public class AppState {
    protected List<Integer> idsList = new ArrayList<>();
    protected int idsListIndex;
    protected List<Story> showStoryList = Collections.synchronizedList(new ArrayList<Story>());
    protected List<Comment> showCommentsList = Collections.synchronizedList(new ArrayList<Comment>());
    protected View view;
    protected Context context;

    public AppState setIdsListIndex(int idsListIndex) {
        this.idsListIndex = idsListIndex;
        return this;
    }

    public int getIdsListIndex() {
        return idsListIndex;
    }

    public void addNews(Story story) {
        showStoryList.add(story);
    }

    public List<Story> getShowStoryList() {
        return showStoryList;
    }

    public void addComment(Comment comment) {
        showCommentsList.add(comment);
    }

    public List<Comment> getShowCommentsList() {
        return showCommentsList;
    }

    public View getView() {
        return view;
    }

    public AppState setView(final View view) {
        this.view = view;
        return this;
    }

    public Context getContext() {
        return context;
    }

    public AppState setContext(Context context) {
        this.context = context;
        return this;
    }

    public List<Integer> getIdsList() {
        return idsList;
    }

    public AppState setIdsList(List<Integer> idsList) {
        this.idsList = idsList;
        return this;
    }
}
