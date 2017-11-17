package hackernews.JSONObject;

import org.json.JSONException;
import org.json.JSONObject;

public class News {
    private String title;
    private int id;
    private String author;
    private String url;

    public News(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getInt("id");
            this.title = jsonObject.getString("title");
            this.author = jsonObject.getString("by");
            this.url = jsonObject.getString("url");
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


}
