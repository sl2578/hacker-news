package hackernews;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import branch.hackernews.R;

public class ViewNewsPage extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news_page);

        String title = this.getIntent().getExtras().getString("title");
        String url = this.getIntent().getExtras().getString("url");

        setTitle(title);

        webView = findViewById(R.id.view_news_page);
        webView.loadUrl(url);
    }
}
