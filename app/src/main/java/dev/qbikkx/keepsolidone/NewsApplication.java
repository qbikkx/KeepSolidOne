package dev.qbikkx.keepsolidone;

import android.app.Application;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dev.qbikkx.keepsolidone.network.NewsAPI;
import dev.qbikkx.keepsolidone.network.UriAdapter;
import dev.qbikkx.keepsolidone.storage.DatabaseAPI;
import dev.qbikkx.keepsolidone.storage.database.NewsStorage;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsApplication extends Application {

    private static NewsAPI sNewsAPI;
    private static DatabaseAPI sDatabaseAPI;

    @Override
    public void onCreate() {
        super.onCreate();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Uri.class, new UriAdapter())
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NewsAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        sNewsAPI = retrofit.create(NewsAPI.class);
        sDatabaseAPI = NewsStorage.getInstance(this);
    }

    public static NewsAPI getWebAPI() {
        return sNewsAPI;
    }

    public static DatabaseAPI getDatabaseAPI() {
        return sDatabaseAPI;
    }
}
