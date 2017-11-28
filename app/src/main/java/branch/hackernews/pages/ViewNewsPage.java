package branch.hackernews.pages;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import branch.hackernews.HackerNewsMainActivity;
import branch.hackernews.R;

public class ViewNewsPage extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news_page);

        String title = this.getIntent().getExtras().getString(HackerNewsMainActivity.TITLE_FIELD);
        String url = this.getIntent().getExtras().getString(HackerNewsMainActivity.URL_FIELD);

        setTitle(title);

        webView = findViewById(R.id.view_news_page);
        webView.loadUrl(url);
    }
}
