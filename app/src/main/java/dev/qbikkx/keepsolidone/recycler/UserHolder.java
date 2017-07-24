package dev.qbikkx.keepsolidone.recycler;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import dev.qbikkx.keepsolidone.R;
import dev.qbikkx.keepsolidone.models.User;

/**
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */

public class UserHolder extends RecyclerView.ViewHolder {
    User mUser;

    private TextView mUserNameTextView;
    private ImageView mIsOnlineImageView;
    private ImageView mCategoryImageView;

    public UserHolder(LayoutInflater inflater, ViewGroup parent, final OnUserItemClickListener listener) {
        super(inflater.inflate(R.layout.user_list_item, parent, false));
        mUserNameTextView = (TextView) itemView.findViewById(R.id.tv_user_name);
        mIsOnlineImageView = (ImageView) itemView.findViewById(R.id.iv_is_online);
        mCategoryImageView = (ImageView) itemView.findViewById(R.id.iv_category);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v, getAdapterPosition());
            }
        });
    }

    public void bind(User user) {
        mUser = user;
        mUserNameTextView.setText(user.getUserName());
        Context ctx = itemView.getContext();
        if (user.isOnline()) {
            mIsOnlineImageView.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.online));
        } else {
            mIsOnlineImageView.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.offline));
        }
        User.Category category = user.getCategory();
        mCategoryImageView.setImageDrawable(ContextCompat.getDrawable(ctx, category.getDrawableResourceId()));
    }
}
