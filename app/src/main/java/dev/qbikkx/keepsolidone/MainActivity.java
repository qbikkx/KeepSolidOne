package dev.qbikkx.keepsolidone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_INPUT_TEXT = "input_text";

    private final static int REQUEST_CODE_SEND_BTN_ACTION = 1;

    private final static boolean BTN_STATE_NOT_ENABLED = false;
    private final static boolean BTN_STATE_ENABLED = true;

    private EditText inputEditText;
    private Button sendBtn;
    private Button clearBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEditText = (EditText) findViewById(R.id.et_input);
        sendBtn = (Button) findViewById(R.id.btn_send);
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
                if (s == null || s.length() == 0) {
                    setButtonsState(BTN_STATE_NOT_ENABLED);
                } else if (!isButtonsEnabled()) {
                    setButtonsState(BTN_STATE_ENABLED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent senderIntent = new Intent(MainActivity.this, ReceiverActivity.class);
                senderIntent.putExtra(EXTRA_INPUT_TEXT, inputEditText.getText().toString());
                startActivityForResult(senderIntent, REQUEST_CODE_SEND_BTN_ACTION);
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
            }
        }
    }

    private boolean isButtonsEnabled() {
        return sendBtn.isEnabled();
    }

    private void setButtonsState(boolean isEnabled) {
        sendBtn.setEnabled(isEnabled);
        clearBtn.setEnabled(isEnabled);
    }

    private void resultOk() {
        inputEditText.setText("");
        Toast.makeText(this, R.string.text_confirmed, Toast.LENGTH_LONG).show();
    }

    private void resultCanceled() {
        Toast.makeText(this, R.string.text_canceled, Toast.LENGTH_LONG).show();
    }
}
