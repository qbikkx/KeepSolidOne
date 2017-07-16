package dev.qbikkx.keepsolidone;

import android.text.TextUtils;

/**
 * Class helps to process emails
 *
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class EmailUtils {

    public static boolean isValidEmail(CharSequence address) {
        return !TextUtils.isEmpty(address) && android.util.Patterns.EMAIL_ADDRESS.matcher(address).matches();
    }
}
