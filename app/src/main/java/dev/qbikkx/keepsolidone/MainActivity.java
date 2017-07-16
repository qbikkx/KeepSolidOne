package dev.qbikkx.keepsolidone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

/**
 * MainActivity.
 *
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_INPUT_TEXT = "input_text";

    private final static int REQUEST_CODE_SEND_BTN_ACTION = 1;

    private final static boolean BTN_STATE_NOT_ENABLED = false;
    private final static boolean BTN_STATE_ENABLED = true;

    private EditText inputEditText;
    private Button sendBtn;
    private Button clearBtn;
    private CheckBox isSendEnabledCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEditText = (EditText) findViewById(R.id.et_input);
        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            /**
             * Блокировка кнопок при пустом поле гарантирует, что далее будет передаваться
             * непустая строка
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isInputNullOrEmpty()) {
                    setBothButtonsState(BTN_STATE_NOT_ENABLED);
                } else if (!isClearBtnEnabled()) {
                    setClearBtnState(BTN_STATE_ENABLED);
                    if (isSendCheckBoxChecked()) {
                        setSendBtnState(BTN_STATE_ENABLED);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        isSendEnabledCheckBox = (CheckBox) findViewById(R.id.cb_is_send_enabled);
        isSendEnabledCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && !isInputNullOrEmpty()) {
                    setSendBtnState(BTN_STATE_ENABLED);
                } else {
                    setSendBtnState(BTN_STATE_NOT_ENABLED);
                }
            }
        });


        sendBtn = (Button) findViewById(R.id.btn_send);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EmailUtils.isValidEmail(inputEditText.getText())) {
                    Intent senderIntent = new Intent(MainActivity.this, SenderActivity.class);
                    senderIntent.putExtra(EXTRA_INPUT_TEXT, inputEditText.getText().toString());
                    startActivityForResult(senderIntent, REQUEST_CODE_SEND_BTN_ACTION);
                } else {
                    showToast(R.string.incorrect_email_msg);
                }
            }
        });

        clearBtn = (Button) findViewById(R.id.btn_clear);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEditText.setText("");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_SEND_BTN_ACTION == requestCode) {
            switch (resultCode) {
                case RESULT_OK:
                    resultOk();
                    break;
                case RESULT_CANCELED:
                    resultCanceled();
                    break;
                case SenderActivity.RESULT_CODE_CANCELED_EMAIL:
                    resultEmailNotSent();
                    break;
            }
        }
    }



    private boolean isSendCheckBoxChecked() {
        return isSendEnabledCheckBox.isChecked();
    }

    private void setSendBtnState(boolean isEnabled) {
        sendBtn.setEnabled(isEnabled);
    }

    private void setClearBtnState(boolean isEnabled) {
        clearBtn.setEnabled(isEnabled);
    }

    private boolean isInputNullOrEmpty() {
        CharSequence charSequence = inputEditText.getText();
        return charSequence == null || charSequence.length() == 0;
    }

    private boolean isClearBtnEnabled() {
        return clearBtn.isEnabled();
    }

    private void setBothButtonsState(boolean isEnabled) {
        setSendBtnState(isEnabled);
        setClearBtnState(isEnabled);
    }

    private void resultOk() {
        inputEditText.setText("");
        showToast(R.string.text_confirmed);
    }

    private void resultEmailNotSent() {
        showToast(R.string.email_didnt_sent);
    }

    private void resultCanceled() {
        showToast(R.string.text_canceled);
    }

    private void showToast(int msgId) {
        Toast.makeText(this, msgId, Toast.LENGTH_LONG).show();
    }
}
