package dev.qbikkx.keepsolidone;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import dev.qbikkx.keepsolidone.di.AppComponent;
import dev.qbikkx.keepsolidone.di.DaggerAppComponent;
import dev.qbikkx.keepsolidone.network.NewsAPIService;
import dev.qbikkx.keepsolidone.storage.DatabaseAPI;
import dev.qbikkx.keepsolidone.storage.database.NewsStorage;

/**
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class NewsApplication extends DaggerApplication {

    private static DatabaseAPI sDatabaseAPI;

    @Override
    public void onCreate() {
        super.onCreate();
        sDatabaseAPI = NewsStorage.getInstance(this);
    }

    public static DatabaseAPI getDatabaseAPI() {
        return sDatabaseAPI;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }
}
