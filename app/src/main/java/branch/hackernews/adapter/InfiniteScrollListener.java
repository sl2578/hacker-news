package branch.hackernews.adapter;

import android.widget.AbsListView;

public abstract class InfiniteScrollListener implements AbsListView.OnScrollListener {
    // Min number of items below current scroll position to load more
    private int visibleThreshold = 3;
    // Current offset index of data that is loaded
    private int currentPage = 0;
    // Total number of items in dataset after last load
    private int previousLoadedItemCount = 0;
    // True if still waiting on last set of data to load
    private boolean isLoading = true;

    public InfiniteScrollListener() {}

    @Override
    public void onScroll(AbsListView absListView,
                         int firstVisibleItem,
                         int visibleItemCount,
                         int totalItemCount) {

        // If still loading and totalItemCount increased, loading must've completed
        if (isLoading && (totalItemCount > previousLoadedItemCount)) {
            isLoading = false;
            previousLoadedItemCount = totalItemCount;
            currentPage++;
        }

        // Need to load more if visibleThreshold is breached
        if (!isLoading && (totalItemCount - visibleItemCount - firstVisibleItem <= visibleThreshold)) {
            isLoading = loadMore(++currentPage, totalItemCount);
        }
    }

    public abstract boolean loadMore(int page, int totalItemCount);

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

}
