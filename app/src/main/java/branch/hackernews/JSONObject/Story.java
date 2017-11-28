package branch.hackernews.JSONObject;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringEscapeUtils.unescapeHtml4;

/**
 * Represents a story item in HackerNews API. The id is unique to the story and is the only
 * required property.
 */
public class Story {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("by")
    private String by;
    @SerializedName("url")
    private String url;
    @SerializedName("score")
    private int score;
    @SerializedName("time")
    private int time;
    @SerializedName("descendants")
    private int descendants;
    @SerializedName("kids")
    private List<Integer> kids;

    public Story(int id, String title, String by, String url, int score, int time, int descendants,
          List<Integer> kids) {
        this.id = id;
        this.title = title;
        this.by = by;
        this.url = url;
        this.score = score;
        this.time = time;
        this.descendants = descendants;
        this.kids = kids;
    }

    public Story(int id, String title, String by, String url, int score, int time, int descendants) {
        this.id = id;
        this.title = title;
        this.by = by;
        this.url = url;
        this.score = score;
        this.time = time;
        this.descendants = descendants;
        this.kids = new ArrayList<>();
    }


    public String getTitle() {
        return unescapeHtml4(title);
    }

    public int getId() {
        return id;
    }

    public String getUser() {
        return by;
    }

    public String getUrl() {
        return url;
    }

    public int getNumDescendants() {
        return descendants;
    }

    public List<Integer> getKids() {
        return kids;
    }

    public int getScore() {
        return score;
    }

    public int getTime() {
        return time;
    }

}
