package branch.hackernews.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringEscapeUtils.unescapeHtml4;

/**
 * Represents a story item in HackerNews API. The id is unique to the story and is the only
 * required property.
 */
public class Story {
    private int id;
    private String title;
    private String by;
    private String url;
    private int descendants;
    private List<Integer> kids = new ArrayList<>();

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
}
