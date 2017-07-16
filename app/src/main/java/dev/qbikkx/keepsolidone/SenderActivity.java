package dev.qbikkx.keepsolidone;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Activity which call the external Email Sender Activity
 *
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class SenderActivity extends AppCompatActivity {
    /**
     * Result code for previous activity in case if
     * external EmailActivity returns RESULT_CANCELED
     */
    public final static int RESULT_CODE_CANCELED_EMAIL = 404;

    private final static int REQUEST_CODE_EMAIL_ACTIVITY = 2;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender);
        textView = (TextView) findViewById(R.id.tv_received_text);
        Intent intent = getIntent();
        if (intent != null) {
            String receivedText = intent.getStringExtra(MainActivity.EXTRA_INPUT_TEXT);
            if (receivedText != null) {
                textView.setText(receivedText);
            }
        }

        Button confirmBtn = (Button) findViewById(R.id.btn_confirm);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence email = textView.getText();
                if (email != null && EmailUtils.isValidEmail(email)) {
                    Intent sendIntent = new Intent(Intent.ACTION_SENDTO,
                            Uri.fromParts("mailto", email.toString(), null));
                    sendIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.mail_subject));
                    sendIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.mail_body));
                    startActivityForResult(Intent.createChooser(sendIntent, getResources().getString(R.string.send_email_msg))
                            , REQUEST_CODE_EMAIL_ACTIVITY);
                }
            }
        });

        Button cancelBtn = (Button) findViewById(R.id.btn_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    /**
     * Waiting for external Email Activity Result
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_EMAIL_ACTIVITY == requestCode) {
            switch (resultCode) {
                case RESULT_OK: {
                    setResult(RESULT_OK);
                    finish();
                    break;
                }
                case RESULT_CANCELED: {
                    setResult(RESULT_CODE_CANCELED_EMAIL);
                    finish();
                    break;
                }
            }
        }
    }
}
