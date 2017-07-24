package dev.qbikkx.keepsolidone.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import dev.qbikkx.keepsolidone.R;
import dev.qbikkx.keepsolidone.fragments.UsersListFragment;
import dev.qbikkx.keepsolidone.models.User;
import dev.qbikkx.keepsolidone.models.UsersDataBase;
import dev.qbikkx.keepsolidone.utils.EmailUtils;

/**
 * Activity which call the external Email Sender Activity
 *
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class UserActivity extends AppCompatActivity {

    private User mUser;

    private TextView mUserNameTextView;
    private TextView mEmailTextView;
    private ImageView mIsOnlineImageView;
    private ImageView mCategoryImageView;
    private TextView mCategoryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        setContentView(R.layout.activity_user);
        initUserFromIntent();
        if (isUserValid()) {
            initUI();
            updateUI();
        }
    }

    private void initUserFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            ParcelUuid parcelUuid = intent.getParcelableExtra(UsersListFragment.EXTRA_SELECTED_ID);
            if (parcelUuid != null) {
                mUser = UsersDataBase.getInstance().getUser(parcelUuid.getUuid());
            }
        }
    }

    private boolean isUserValid() {
        return mUser != null;
    }

    private void initUI() {
        mUserNameTextView = (TextView) findViewById(R.id.tv_sender_user_name);
        mEmailTextView = (TextView) findViewById(R.id.tv_sender_email);
        mIsOnlineImageView = (ImageView) findViewById(R.id.iv_sender_is_online);
        mCategoryImageView = (ImageView) findViewById(R.id.iv_sender_category);
        mCategoryTextView = (TextView) findViewById(R.id.tv_sender_category);

        Button confirmBtn = (Button) findViewById(R.id.btn_confirm);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        Button cancelBtn = (Button) findViewById(R.id.btn_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithResult(RESULT_CANCELED);
            }
        });
    }

    private void updateUI() {
        mUserNameTextView.setText(mUser.getUserName());
        mEmailTextView.setText(mUser.getUserAddress());
        if (mUser.isOnline()) {
            mIsOnlineImageView.setImageDrawable(getDrawable(R.drawable.online));
        } else {
            mIsOnlineImageView.setImageDrawable(getDrawable(R.drawable.offline));
        }
        User.Category category = mUser.getCategory();
        mCategoryImageView.setImageDrawable(getDrawable(category.getDrawableResourceId()));
        mCategoryTextView.setText(category.getStringResourceId());
    }


    private void sendEmail() {
        CharSequence email = mEmailTextView.getText();
        if (email != null && EmailUtils.isValidEmail(email)) {
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email.toString(), null));
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.mail_subject));
            sendIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.mail_body));
            startActivity(Intent.createChooser(sendIntent, getResources().getString(R.string.send_email_msg)));
            finishWithResult(RESULT_OK);
        }
    }

    private void finishWithResult(int resultCode) {
        setResult(resultCode);
        finish();
    }

    /**
     * finish with custom animation
     */
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }
}
