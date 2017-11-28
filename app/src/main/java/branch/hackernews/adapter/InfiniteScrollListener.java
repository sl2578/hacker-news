package branch.hackernews.adapter;

import android.widget.AbsListView;

public abstract class InfiniteScrollListener implements AbsListView.OnScrollListener {
    private int currentFirstVisibleItem, currentVisibleItemCount, currentTotalItemCount;
    private int scrollThreshold = 2;
    private int currentScrollState;

    @Override
    public void onScroll(AbsListView absListView,
                         int firstVisibleItem,
                         int visibleItemCount,
                         int totalItemCount) {
        this.currentFirstVisibleItem = firstVisibleItem;
        this.currentVisibleItemCount = visibleItemCount;
        this.currentTotalItemCount = totalItemCount;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        this.currentScrollState = scrollState;
        this.isScrollCompleted();
    }

    private void isScrollCompleted() {
        if (scrollThreshold + currentFirstVisibleItem
                + currentVisibleItemCount >= currentTotalItemCount) {
            if (this.currentVisibleItemCount > 0
                    && this.currentScrollState == SCROLL_STATE_IDLE) {
                loadMore(currentFirstVisibleItem, currentTotalItemCount);
            }
        }
    }

    public abstract boolean loadMore(int page, int totalItemCount);

}