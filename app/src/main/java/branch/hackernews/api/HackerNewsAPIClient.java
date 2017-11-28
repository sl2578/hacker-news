package branch.hackernews.api;

import android.os.AsyncTask;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HackerNewsAPIClient {
    private static HackerNewsAPIInterface hackerNewsAPIInterface = null;

    public static HackerNewsAPIInterface getClient() {
        if (hackerNewsAPIInterface ==null) {
            final OkHttpClient client = new OkHttpClient();
            final Retrofit retrofit = new Retrofit.Builder()
                    .callbackExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                    .baseUrl(HackerNewsAPIInterface.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            hackerNewsAPIInterface = retrofit.create(HackerNewsAPIInterface.class);
        }
        return hackerNewsAPIInterface;
    }
}