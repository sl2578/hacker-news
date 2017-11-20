package branch.hackernews.RetrieveFromAPI;

import android.text.format.DateUtils;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import branch.hackernews.AppState;
import branch.hackernews.JSONObject.User;
import branch.hackernews.R;
import branch.hackernews.Utils;
import branch.hackernews.pages.ViewUser;

public class RetrieveUserTask extends RetrieveFromAPITask<User> {
    private WeakReference<ViewUser> viewUserWeakReference;

    public RetrieveUserTask(AppState appState, ViewUser viewUser) {
        super(appState);
        viewUserWeakReference = new WeakReference<>(viewUser);
    }

    @Override
    protected void onPostExecute(User user) {
        ViewUser viewUser = viewUserWeakReference.get();
        if (viewUser == null) {
            // TODO: Log error
        }
        TextView user_name = viewUser.findViewById(R.id.user);
        TextView created_date = viewUser.findViewById(R.id.created_date);
        TextView karma_points = viewUser.findViewById(R.id.karma_points);
        TextView about = viewUser.findViewById(R.id.about);

        user_name.setText(user.getId());
        created_date.setText(Utils.timeSince(
                DateUtils.SECOND_IN_MILLIS * user.getCreated(), System.currentTimeMillis()));
        karma_points.setText(String.valueOf(user.getKarma()));
        about.setText(user.getAbout());
    }

    @Override
    protected Class<User> getRetrievedClass() {
        return User.class;
    }
}
