package branch.hackernews;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(RobolectricTestRunner.class)
public class TestUtils {
    @Test
    public void testConvertDoubleToIntegerList() {
        List<Double> input = new ArrayList<>();
        input.add(1d);
        input.add(2d);
        List<Integer> result = Utils.convertDoubleToIntegerList(input);
        Assert.assertTrue(result.get(0) instanceof Integer);
    }

    @Test
    public void testTimeSince() {
        long current = TimeUnit.SECONDS.toMillis(1511130483);

        long past = current - TimeUnit.SECONDS.toMillis(5);
        Assert.assertEquals("5 seconds ago", Utils.timeSince(past, current));

        past = current - TimeUnit.MINUTES.toMillis(14);
        Assert.assertEquals("14 minutes ago", Utils.timeSince(past, current));

        past = current - TimeUnit.DAYS.toMillis(2);
        Assert.assertEquals("2 days ago", Utils.timeSince(past, current));

        // Beyond a week, simply state the day
        past = current - TimeUnit.DAYS.toMillis(7);
        Assert.assertEquals("Nov 12, 2017", Utils.timeSince(past, current));
    }
}
