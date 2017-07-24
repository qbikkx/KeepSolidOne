package dev.qbikkx.keepsolidone.dialogs;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import dev.qbikkx.keepsolidone.R;

/**
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class CancelDialog extends DialogFragment {
    public final static String TAG = "CancelDialog";
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View rootView = getActivity().getLayoutInflater()
                .inflate(R.layout.fragment_cancel, new LinearLayoutCompat(getActivity()), false);
        final Dialog builder = new Dialog(getActivity());
        ImageView img = (ImageView) rootView.findViewById(R.id.iv_cancel);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(rootView);
        return builder;
    }
}
