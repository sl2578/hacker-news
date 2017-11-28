package branch.hackernews;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import branch.hackernews.JSONObject.Comment;
import branch.hackernews.JSONObject.Story;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Tests for {@link HackerNewsMainActivity}
 */
@RunWith(AndroidJUnit4.class)
public class TestHackerNewsMainActivity {
    @Rule
    public ActivityTestRule<HackerNewsMainActivity> hackerNewsActivityRule =
            new ActivityTestRule<>(HackerNewsMainActivity.class);

    @Test
    public void testInitialView() {
        Espresso.onView(withId(R.id.news_list))
                .check(matches(isDisplayed()));

        Espresso.onView(withId(R.id.news_list))
                .check(matches(Matchers.withListSize(15)));
    }

    @Test
    public void testLoadMoreOnScroll() {
        // Check that only 15 elements exist in list
        Espresso.onView(withId(R.id.news_list))
                .check(matches(Matchers.withListSize(15)));

        // Scroll to bottom of list
        Espresso.onData(is(instanceOf(Story.class)))
                .atPosition(14)
                .perform(scrollTo());

        // Check that 15 more stories got loaded
        Espresso.onView(withId(R.id.news_list))
                .check(matches(Matchers.withListSize(30)));
    }

    @Test
    public void testClickViewComments() {
        // Check that no text view for viewing comments is displayed yet
        Espresso.onView(allOf(withId(R.id.news_info), withText("View Comments")))
                .check(doesNotExist());

        // Get the first story in the view and click on it to expand the row
        Espresso.onData(is(instanceOf(Story.class)))
                .atPosition(0)
                .inAdapterView(withId(R.id.news_list))
                .perform(click());

        // Check that a text view for viewing comments is now displayed
        Espresso.onView(allOf(withId(R.id.news_info), withText("View Comments")))
                .check(matches(isDisplayed()));

        // Click on View Comments
        Espresso.onView(allOf(withId(R.id.news_info), withText("View Comments")))
                .perform(click());

        // Check that a comment object is displayed in view
        Espresso.onData(is(instanceOf(Comment.class)))
                .atPosition(0)
                .check(matches(isDisplayed()));
    }

    @Test
    public void testClickViewUser() {
        // Check that no text view for viewing user is displayed yet
        Espresso.onView(allOf(withId(R.id.news_info), withText("View User")))
                .check(doesNotExist());

        // Get the first story in the view and click on it to expand the row
        Espresso.onData(is(instanceOf(Story.class)))
                .atPosition(0)
                .inAdapterView(withId(R.id.news_list))
                .perform(click());

        // Check that a text view for viewing user is now displayed
        Espresso.onView(allOf(withId(R.id.news_info), withText("View User")))
                .check(matches(isDisplayed()));

        // Click on View User
        Espresso.onView(allOf(withId(R.id.news_info), withText("View User")))
                .perform(click());

        // Check that the required fields for user object is displayed
        Espresso.onView(withId(R.id.user))
                .check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.created_date))
                .check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.karma_points))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testClickViewArticle() {
        // Check that no text view for viewing article is displayed yet
        Espresso.onView(allOf(withId(R.id.news_info), withText("View Article")))
                .check(doesNotExist());

        // Get the first story in the view and click on it to expand the row
        Espresso.onData(is(instanceOf(Story.class)))
                .atPosition(0)
                .inAdapterView(withId(R.id.news_list))
                .perform(click());

        // Check that a text view for viewing article is now displayed
        Espresso.onView(allOf(withId(R.id.news_info), withText("View Article")))
                .check(matches(isDisplayed()));

        // Click on View Article
        Espresso.onView(allOf(withId(R.id.news_info), withText("View Article")))
                .perform(click());

        // Check that the view_news_page view is displayed
        Espresso.onView(withId(R.id.view_news_page))
                .check(matches(isDisplayed()));
    }

}

class Matchers {
    /**
     * Matcher that checks the size of listview
     * @return true if size is equal to actual size of listview
     */
    public static Matcher<View> withListSize (final int size) {
        return new TypeSafeMatcher<View>() {
            @Override public boolean matchesSafely (final View view) {
                return ((ListView) view).getCount () == size;
            }

            @Override public void describeTo (final Description description) {
                description.appendText ("ListView should have " + size + " items");
            }
        };
    }
}
