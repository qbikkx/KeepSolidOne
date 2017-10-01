package dev.qbikkx.keepsolidone.mainscreen;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dev.qbikkx.keepsolidone.di.ActivityScoped;
import dev.qbikkx.keepsolidone.di.FragmentScoped;

/**
 * Created by Sviat on 28.09.2017.
 */
@Module
public abstract class NewsListModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract NewsListFragment newsListFragment();

    @ActivityScoped
    @Binds
    abstract NewsListContract.Presenter newsListPresenter(NewsListPresenter presenter);
}
