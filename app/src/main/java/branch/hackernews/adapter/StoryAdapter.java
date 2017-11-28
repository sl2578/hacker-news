package branch.hackernews.adapter;

import android.content.Context;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import branch.hackernews.HackerNewsMainActivity;
import branch.hackernews.JSONObject.Story;
import branch.hackernews.R;
import branch.hackernews.Utils;

/**
 * Adapter class for {@link HackerNewsMainActivity} to display the {@link Story} objects
 * in an {@link ExpandableListView}
 */
public class StoryAdapter extends BaseExpandableListAdapter {
    private final Context context;
    private final LayoutInflater layoutInflater;
    private final List<Story> storyRow;
    private final Map<Integer, List<String>> storiesInfo;


    public StoryAdapter(Context context,
                        List<Story> storyRow,
                        Map<Integer, List<String>> storiesInfo) {
        this.context = context;
        this.storyRow = storyRow;
        this.storiesInfo = storiesInfo;
        this.layoutInflater = (LayoutInflater)
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private static class GroupViewHolder {
        private TextView title;
        private TextView score;
        private TextView user;
        private TextView date;
        private TextView descendants;
    }

    private static class ChildViewHolder {
        private TextView viewReplies;
    }

    @Override
    public View getGroupView(int groupPosition,
                             boolean isExpanded,
                             View convertView,
                             ViewGroup parent) {
        final Story story = (Story) getGroup(groupPosition);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.news_row, null);
            GroupViewHolder viewHolder = new GroupViewHolder();
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.score = convertView.findViewById(R.id.score);
            viewHolder.user = convertView.findViewById(R.id.user);
            viewHolder.date = convertView.findViewById(R.id.date);
            viewHolder.descendants = convertView.findViewById(R.id.descendants);
            convertView.setTag(viewHolder);
        }

        GroupViewHolder holder = (GroupViewHolder) convertView.getTag();

        holder.title.setText(Html.fromHtml(story.getTitle()));
        holder.score.setText(String.valueOf(story.getScore()));
        holder.user.setText(story.getUser());
        holder.date.setText(Utils.timeSince(
                DateUtils.SECOND_IN_MILLIS * story.getTime(), System.currentTimeMillis()));
        holder.descendants.setText(String.valueOf(story.getNumDescendants()));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition,
                             int childPosition,
                             boolean isLastChild,
                             View convertView,
                             ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.news_row_info, null);
            ChildViewHolder viewHolder = new ChildViewHolder();
            viewHolder.viewReplies = convertView.findViewById(R.id.news_info);
            convertView.setTag(viewHolder);
        }

        ChildViewHolder holder = (ChildViewHolder) convertView.getTag();
        holder.viewReplies.setText(childText);

        return convertView;
    }

    @Override
    public int getGroupCount() {
        return this.storyRow.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.storiesInfo.get(this.storyRow.get(groupPosition).getId()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.storyRow.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.storiesInfo.get(this.storyRow.get(groupPosition).getId()).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}