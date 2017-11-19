package branch.hackernews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import branch.hackernews.R;
import branch.hackernews.JSONObject.Story;

public class NewsAdapter extends BaseExpandableListAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Story> storyRow;
    private Map<Integer, List<String>> newsInfo;


    public NewsAdapter(Context context,
                       List<Story> storyRow,
                       Map<Integer, List<String>> newsInfo) {
        this.context = context;
        this.storyRow = storyRow;
        this.newsInfo = newsInfo;
        this.layoutInflater = (LayoutInflater)
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getGroupView(int groupPosition,
                             boolean isExpanded,
                             View convertView,
                             ViewGroup parent) {
        Story story = (Story) getGroup(groupPosition);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.news_row, null);
        }

        TextView title = convertView.findViewById(R.id.title);
        title.setText(story.getTitle());

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
        }

        TextView listText = convertView.findViewById(R.id.news_info);
        listText.setText(childText);

        return convertView;
    }

    @Override
    public int getGroupCount() {
        return this.storyRow.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.newsInfo.get(this.storyRow.get(groupPosition).getId()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.storyRow.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.newsInfo.get(this.storyRow.get(groupPosition).getId()).get(childPosition);
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

//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        View rowView = layoutInflater.inflate(R.layout.news_row, viewGroup, false);
//        TextView title = rowView.findViewById(R.id.title);
//        TextView text = rowView.findViewById(R.id.text);
//        title.setText(storyRow.get(i).getTitle());
//        text.setText(storyRow.get(i).getAuthor());
//        return rowView;
//    }