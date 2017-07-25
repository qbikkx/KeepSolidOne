package dev.qbikkx.keepsolidone.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dev.qbikkx.keepsolidone.models.User;
import dev.qbikkx.keepsolidone.recycler.OnUserItemClickListener;
import dev.qbikkx.keepsolidone.recycler.UserHolder;

/**
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */

public class UsersListAdapter extends RecyclerView.Adapter<UserHolder> {

    private List<User> mUsersList = new ArrayList<>();

    private OnUserItemClickListener mOnUserItemClickListener;

    public UsersListAdapter(List<User> userList, OnUserItemClickListener listener) {
        mUsersList = userList;
        mOnUserItemClickListener = listener;
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new UserHolder(inflater, parent, mOnUserItemClickListener);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        User user = mUsersList.get(position);
        holder.bind(user);
    }

    public User getUser(int position) {
        return  mUsersList.get(position);
    }

    @Override
    public int getItemCount() {
        return mUsersList.size();
    }
}