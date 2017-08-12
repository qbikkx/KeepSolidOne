package dev.qbikkx.keepsolidone.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Class helps to process emails
 *
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class Utils {

    public static void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }

    public static void showToast(Context ctx, int resId) {
        Toast.makeText(ctx, resId, Toast.LENGTH_LONG).show();
    }
}
