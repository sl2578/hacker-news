package branch.hackernews;

import com.google.gson.JsonSyntaxException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import branch.hackernews.JSONObject.Story;

@RunWith(RobolectricTestRunner.class)
public class TestUtils {
    @Test
    public void testLoadJSONEmpty() {
        // Test empty Story
        String input = "";
        Story storyResult = Utils.loadJSON(input, Story.class);
        Assert.assertEquals(storyResult, null);
    }

    @Test
    public void testLoadJSONPartial() {
        // Test partial Story with no kids
        String input = "{\"id\": 1, \"title\": \"title\", \"by\": \"user\", \"url\": \"testURL\", \"descendants\": 0}";
        Story storyResult = Utils.loadJSON(input, Story.class);
        Assert.assertEquals(1, storyResult.getId());
        Assert.assertEquals("title", storyResult.getTitle());
        Assert.assertEquals("user", storyResult.getUser());
        Assert.assertEquals("testURL", storyResult.getUrl());
        Assert.assertEquals(0, storyResult.getNumDescendants());
        Assert.assertEquals(0, storyResult.getKids().size());
    }

    @Test
    public void testLoadJSONFull() {
        // Test full Story
        String input = "{\"id\": 1, \"title\": \"title\", \"by\": \"user\", \"url\": \"testURL\", \"descendants\": 2, \"kids\": [2, 3]}";
        Story storyResult = Utils.loadJSON(input, Story.class);
        Assert.assertEquals(1, storyResult.getId());
        Assert.assertEquals("title", storyResult.getTitle());
        Assert.assertEquals("user", storyResult.getUser());
        Assert.assertEquals("testURL", storyResult.getUrl());
        Assert.assertEquals(2, storyResult.getNumDescendants());
        Assert.assertEquals(Arrays.asList(2, 3), storyResult.getKids());
    }

    @Test(expected = JsonSyntaxException.class)
    public void testLoadJSONIncorrect() {
        // Test incorrect input
        String input = "incorrect input";
        Story storyResult = Utils.loadJSON(input, Story.class);
        Assert.assertEquals(null, storyResult);
    }

    @Test
    public void testLoadJSONList() {
        // Test JSON array to List
        String input = "[1, 2, 3]";
        List result = Utils.loadJSON(input, List.class);
        Assert.assertTrue(result.get(0) instanceof Double);
        Assert.assertEquals(1d, result.get(0));
    }

    @Test
    public void testConvertDoubleToIntegerList() {
        List<Double> input = new ArrayList<>();
        input.add(1d);
        input.add(2d);
        List<Integer> result = Utils.convertDoubleToIntegerList(input);
        Assert.assertTrue(result.get(0) instanceof Integer);
    }
}
