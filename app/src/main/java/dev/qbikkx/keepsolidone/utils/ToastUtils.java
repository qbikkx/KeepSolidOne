package dev.qbikkx.keepsolidone.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Class helps to process toasts
 *
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class ToastUtils {

    public static void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }

    public static void showToast(Context ctx, int resId) {
        Toast.makeText(ctx, resId, Toast.LENGTH_LONG).show();
    }
}
