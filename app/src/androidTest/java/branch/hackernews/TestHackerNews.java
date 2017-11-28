package branch.hackernews;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TestHackerNews {
    @Rule
    public ActivityTestRule<HackerNews> hackerNewsActivityRule = new ActivityTestRule<>(
            HackerNews.class);

    @Test
    public void testInitialLoadedStories() {
        onView(withId(R.id.news_list))
                .check(matches(withText("Lalala")));
    }
}
