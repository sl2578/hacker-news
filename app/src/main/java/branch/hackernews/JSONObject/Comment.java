package branch.hackernews.JSONObject;

import java.util.List;

public class Comment {
    private String by;
    private int id;
    private List<Integer> kids;
    private int parent;
    private String text;
    private int time;

    public String getBy() {
        return by;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getKids() {
        return kids;
    }

    public int getParent() {
        return parent;
    }

    public String getText() {
        return text;
    }

    public int getTime() {
        return time;
    }

}
