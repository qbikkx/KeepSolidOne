package dev.qbikkx.keepsolidone.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import dev.qbikkx.keepsolidone.R;
import dev.qbikkx.keepsolidone.utils.EmailUtils;

/**
 * DataFragment
 *
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class DataFragment extends SwitcherFragment {
    public final static String TAG = "DataFragment";

    public final static String EXTRA_INPUT_TEXT = "input_text";

    private final static boolean BTN_STATE_DISABLED = false;
    private final static boolean BTN_STATE_ENABLED = true;

    private EditText inputEditText;
    private Button sendBtn;
    private Button clearBtn;
    private Switch isSendEnabledSwitch;


    public static DataFragment newInstance(Bundle params) {
        DataFragment fragment = new DataFragment();
        fragment.setArguments(params);
        return fragment;
    }

    private String getText() {
        Bundle arguments = getArguments();
        return arguments != null ? arguments.getString(EXTRA_INPUT_TEXT) : null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_data, container, false);
        initUI(rootView);
        updateUI();
        return rootView;
    }

    private void initUI(View rootView) {
        inputEditText = (EditText) rootView.findViewById(R.id.et_input);
        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            /**
             * Disabling buttons when input field is empty
             * ensures that a non-empty string will be passed next
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isInputNullOrEmpty()) {
                    setBothButtonsState(BTN_STATE_DISABLED);
                } else if (!isClearBtnEnabled()) {
                    setClearBtnState(BTN_STATE_ENABLED);
                    if (isSendSwitchChecked()) {
                        setSendBtnState(BTN_STATE_ENABLED);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        isSendEnabledSwitch = (Switch) rootView.findViewById(R.id.sw_is_send_enabled);
        isSendEnabledSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && !isInputNullOrEmpty()) {
                    setSendBtnState(BTN_STATE_ENABLED);
                } else {
                    setSendBtnState(BTN_STATE_DISABLED);
                }
            }
        });


        sendBtn = (Button) rootView.findViewById(R.id.btn_send);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EmailUtils.isValidEmail(inputEditText.getText())) {
                    Bundle bundle = new Bundle();
                    bundle.putString(EXTRA_INPUT_TEXT, inputEditText.getText().toString());
                    mScreenSwitcher.switchScreenByTag(TAG, bundle);
                } else {
                    showToast(R.string.incorrect_email_msg);
                }
            }
        });

        clearBtn = (Button) rootView.findViewById(R.id.btn_clear);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEditText.setText("");
            }
        });
    }

    /**
     * Заполнение всех виджетов после их создания
     */
    private void updateUI() {
        inputEditText.setText(getText());
    }

    private boolean isSendSwitchChecked() {
        return isSendEnabledSwitch.isChecked();
    }

    private boolean isInputNullOrEmpty() {
        CharSequence charSequence = inputEditText.getText();
        return charSequence == null || charSequence.length() == 0;
    }

    private boolean isClearBtnEnabled() {
        return clearBtn.isEnabled();
    }

    private void setSendBtnState(boolean isEnabled) {
        sendBtn.setEnabled(isEnabled);
    }

    private void setClearBtnState(boolean isEnabled) {
        clearBtn.setEnabled(isEnabled);
    }

    private void setBothButtonsState(boolean isEnabled) {
        setSendBtnState(isEnabled);
        setClearBtnState(isEnabled);
    }

    private void showToast(int msgId) {
        Toast.makeText(getActivity(), msgId, Toast.LENGTH_LONG).show();
    }
}
