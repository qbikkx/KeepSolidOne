package dev.qbikkx.keepsolidone.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import dev.qbikkx.keepsolidone.R;
import dev.qbikkx.keepsolidone.fragments.NewsListFragment;

/**
 * MainActivity.
 *
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startFragment();
    }

    private void startFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fl_fragment_container);
        if (fragment == null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            fragment = NewsListFragment.newInstance();
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
            transaction.add(R.id.fl_fragment_container, fragment);
            transaction.commit();
        }
    }
}
