package dev.qbikkx.keepsolidone.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import dev.qbikkx.keepsolidone.fragments.UserFragment;
import dev.qbikkx.keepsolidone.models.User;

/**
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class UserPagerAdapter extends FragmentStatePagerAdapter {
    private List<User> mUsersList;

    public UserPagerAdapter(FragmentManager fm, List<User> userList) {
        super(fm);
        mUsersList = userList;
    }

    @Override
    public Fragment getItem(int position) {
        User user = mUsersList.get(position);
        return UserFragment.newInstance(user.getId());
    }

    @Override
    public int getCount() {
        return mUsersList.size();
    }
}
