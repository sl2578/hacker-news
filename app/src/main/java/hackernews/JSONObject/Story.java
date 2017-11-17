package hackernews.JSONObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 */
public class Story {
    private int id;
    private String title;
    private String author;
    private String url;
    private String descendants;
    private JSONArray kids;

    public Story(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getInt("id");
            this.title = jsonObject.getString("title");
            this.author = jsonObject.getString("by");
            this.url = jsonObject.getString("url");
            this.descendants = jsonObject.getString("descendants");
            this.kids = jsonObject.getJSONArray("kids");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }

    public String getDescendants() {
        return descendants;
    }

    public JSONArray getKids() {
        return kids;
    }
}
