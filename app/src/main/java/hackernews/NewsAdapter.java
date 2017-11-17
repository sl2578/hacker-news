package hackernews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hackernews.JSONObject.News;
import branch.hackernews.R;

public class NewsAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<News> dataSource;

    public NewsAdapter(Context context, List<News> items) {
        this.context = context;
        this.dataSource = items;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.dataSource.size();
    }

    @Override
    public Object getItem(int i) {
        return this.dataSource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = layoutInflater.inflate(R.layout.row, viewGroup, false);
        TextView title = rowView.findViewById(R.id.title);
        TextView text = rowView.findViewById(R.id.text);
        title.setText(dataSource.get(i).getTitle());
        text.setText(dataSource.get(i).getAuthor());
        return rowView;
    }
}
