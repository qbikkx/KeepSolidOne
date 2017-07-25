package dev.qbikkx.keepsolidone.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

import dev.qbikkx.keepsolidone.R;
import dev.qbikkx.keepsolidone.adapters.UserPagerAdapter;
import dev.qbikkx.keepsolidone.models.User;
import dev.qbikkx.keepsolidone.models.UsersDataBase;

/**
 * Activity which call the external Email Sender Activity
 *
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class UserActivity extends AppCompatActivity {
    public static final String EXTRA_USER_ID = "dev.qbikkx.keepsolidone.activities.UserActivity.userId";

    private ViewPager mPager;
    private PagerAdapter mAdapter;
    private List<User> mUsersList;

    /**
     * Четко определяем интент который может стартовать эту активити
     * @param ctx - запускающая компонента
     * @param userId - id отображаемого пользователя
     */
    public static Intent newIntent(Context ctx, UUID userId) {
        Intent intent = new Intent(ctx, UserActivity.class);
        intent.putExtra(EXTRA_USER_ID, new ParcelUuid(userId));
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mPager = (ViewPager) findViewById(R.id.vp_user_pager);
        mUsersList = UsersDataBase.getInstance().getUsersList();
        mAdapter = new UserPagerAdapter(getSupportFragmentManager(), mUsersList);
        mPager.setAdapter(mAdapter);

        Intent intent = getIntent();
        if (intent != null) {
            UUID userId = ((ParcelUuid) getIntent().getParcelableExtra(EXTRA_USER_ID)).getUuid();
            for (int i = 0; i < mUsersList.size(); i++) {
                if (mUsersList.get(i).getId().equals(userId)) {
                    mPager.setCurrentItem(i);
                    break;
                }
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }
}
