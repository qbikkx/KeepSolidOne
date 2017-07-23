package dev.qbikkx.keepsolidone.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import dev.qbikkx.keepsolidone.R;
/**
 * Отображает сообщение для RESULT_CANCEL у активити
 *
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class CancelFragment extends SwitcherFragment {
    public final static String TAG = "CancelFragment";

    public static CancelFragment newInstance() {
        return new CancelFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cancel, container, false);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.iv_cancel);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScreenSwitcher.switchScreenByTag(TAG);
            }
        });
        return rootView;
    }
}
