package dev.qbikkx.keepsolidone.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.UUID;

import dev.qbikkx.keepsolidone.R;
import dev.qbikkx.keepsolidone.models.User;
import dev.qbikkx.keepsolidone.models.UsersDataBase;
import dev.qbikkx.keepsolidone.utils.EmailUtils;

/**
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class UserFragment extends Fragment {
    private final static String EXTRA_USER_ID = "dev.qbikkx.keepsolidone.fragments.UserFragment.userId";

    private User mUser;

    private TextView mUserNameTextView;
    private TextView mEmailTextView;
    private ImageView mIsOnlineImageView;
    private ImageView mCategoryImageView;
    private TextView mCategoryTextView;

    public static UserFragment newInstance(UUID id) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_USER_ID, new ParcelUuid(id));
        UserFragment fragment = new UserFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);
        initUserFromArguments();
        if (isUserValid()) {
            initUI(rootView);
            updateUI();
        }
        return rootView;
    }

    private void initUserFromArguments() {
        Bundle args = getArguments();
        if (args != null) {
            ParcelUuid parcelUuid = args.getParcelable(EXTRA_USER_ID);
            if (parcelUuid != null) {
                mUser = UsersDataBase.getInstance().getUser(parcelUuid.getUuid());
            }
        }
    }

    private boolean isUserValid() {
        return mUser != null;
    }

    private void initUI(View rootView) {
        mUserNameTextView = (TextView) rootView.findViewById(R.id.tv_sender_user_name);
        mEmailTextView = (TextView) rootView.findViewById(R.id.tv_sender_email);
        mIsOnlineImageView = (ImageView) rootView.findViewById(R.id.iv_sender_is_online);
        mCategoryImageView = (ImageView) rootView.findViewById(R.id.iv_sender_category);
        mCategoryTextView = (TextView) rootView.findViewById(R.id.tv_sender_category);

        Button confirmBtn = (Button) rootView.findViewById(R.id.btn_confirm);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        Button cancelBtn = (Button) rootView.findViewById(R.id.btn_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithResult(Activity.RESULT_CANCELED);
            }
        });
    }

    private void updateUI() {
        mUserNameTextView.setText(mUser.getUserName());
        mEmailTextView.setText(mUser.getUserAddress());
        if (mUser.isOnline()) {
            mIsOnlineImageView.setImageDrawable(getActivity().getDrawable(R.drawable.online));
        } else {
            mIsOnlineImageView.setImageDrawable(getActivity().getDrawable(R.drawable.offline));
        }
        User.Category category = mUser.getCategory();
        mCategoryImageView.setImageDrawable(getActivity().getDrawable(category.getDrawableResourceId()));
        mCategoryTextView.setText(category.getStringResourceId());
    }


    private void sendEmail() {
        CharSequence email = mEmailTextView.getText();
        if (email != null && EmailUtils.isValidEmail(email)) {
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email.toString(), null));
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.mail_subject));
            sendIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.mail_body));
            startActivity(Intent.createChooser(sendIntent, getResources().getString(R.string.send_email_msg)));
            finishWithResult(Activity.RESULT_OK);
        }
    }

    private void finishWithResult(int resultCode) {
        getActivity().setResult(resultCode);
        getActivity().finish();
    }
}
