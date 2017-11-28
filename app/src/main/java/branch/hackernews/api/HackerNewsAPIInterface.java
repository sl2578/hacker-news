package branch.hackernews.api;

import java.util.List;

import branch.hackernews.JSONObject.Comment;
import branch.hackernews.JSONObject.Story;
import branch.hackernews.JSONObject.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HackerNewsAPIInterface {
    String BASE_URL = "https://hacker-news.firebaseio.com/v0/";

    @GET("topstories.json")
    Call<List<Integer>> getTopStories();

    @GET("item/{id}.json")
    Call<Story> getStory(@Path("id") int id);

    @GET("item/{id}.json")
    Call<Comment> getComment(@Path("id") int id);

    @GET("user/{id}.json")
    Call<User> getUser(@Path("id") String id);

}