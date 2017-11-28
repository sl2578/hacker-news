package branch.hackernews;

import android.text.format.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    /**
     * Convert {@link List<Double>} into {@link List<Integer>}
     * @param list List of doubles
     * @return List of integers
     */
    public static List<Integer> convertDoubleToIntegerList(List<Double> list) {
        List<Integer> newList = new ArrayList<>();
        for(Double s : list) {
            newList.add(s.intValue());
        }
        return newList;
    }

    /**
     * Get how much time passed since the input unix timestamp
     * @param past Initial time to calculate the time "since"
     * @param current Time to calculate to
     * @return String containing how "long ago" the input timestamp was
     */
    public static String timeSince(long past, long current) {
        return DateUtils.getRelativeTimeSpanString(past, current, 0).toString();
    }
}