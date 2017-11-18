package hackernews.JSONObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Story {
    private int id;
    private String title;
    private String by;
    private String url;
    private int descendants;
    private List<Integer> kids = new ArrayList<>();

    public Story() {}

    public String getTitle() {
        return title;
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
}
