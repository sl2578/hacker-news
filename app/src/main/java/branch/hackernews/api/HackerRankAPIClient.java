package branch.hackernews.api;

import android.os.AsyncTask;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HackerRankAPIClient {
    private static HackerRankAPIInterface hackerRankAPIInterface = null;

    public static HackerRankAPIInterface getClient() {
        if (hackerRankAPIInterface==null) {
            final OkHttpClient client = new OkHttpClient();
            final Retrofit retrofit = new Retrofit.Builder()
                    .callbackExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                    .baseUrl(HackerRankAPIInterface.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            hackerRankAPIInterface = retrofit.create(HackerRankAPIInterface.class);
        }
        return hackerRankAPIInterface;
    }
}