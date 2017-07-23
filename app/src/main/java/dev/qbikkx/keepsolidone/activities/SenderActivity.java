package dev.qbikkx.keepsolidone.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import dev.qbikkx.keepsolidone.R;
import dev.qbikkx.keepsolidone.fragments.DataFragment;
import dev.qbikkx.keepsolidone.utils.EmailUtils;

/**
 * Activity which call the external Email Sender Activity
 *
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class SenderActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_left_animation, R.anim.slide_out_right);
        setContentView(R.layout.activity_sender);
        textView = (TextView) findViewById(R.id.tv_received_text);
        Intent intent = getIntent();
        if (intent != null) {
            String receivedText = intent.getStringExtra(DataFragment.EXTRA_INPUT_TEXT);
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
                    startActivity(Intent.createChooser(sendIntent, getResources().getString(R.string.send_email_msg)));
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

        Button cancelBtn = (Button) findViewById(R.id.btn_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResultCancel();
                finish();
            }
        });
    }

    /**
     * BackStack отрабатывает как кнопка cancelBtn
     */
    @Override
    public void onBackPressed() {
        setResultCancel();
        super.onBackPressed();
    }

    private void setResultCancel() {
        Intent intent = new Intent();
        intent.putExtra(DataFragment.EXTRA_INPUT_TEXT, textView.getText().toString());
        setResult(RESULT_CANCELED, intent);
    }

    /**
     * finish with custom animation
     */
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left_animation, R.anim.slide_out_right);
    }
}
