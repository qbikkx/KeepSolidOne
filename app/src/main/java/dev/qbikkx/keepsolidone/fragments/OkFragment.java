package dev.qbikkx.keepsolidone.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import dev.qbikkx.keepsolidone.R;

/**
 * Отображает сообщение для RESULT_OK у активити
 *
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class OkFragment extends SwitcherFragment {
    public final static String TAG = "OkFragment";

    public static OkFragment newInstance() {
        return new  OkFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_ok, container, false);
        ImageView img = (ImageView) rootView.findViewById(R.id.iv_ok);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScreenSwitcher.switchScreenByTag(TAG);
            }
        });
        return  rootView;
    }
}
