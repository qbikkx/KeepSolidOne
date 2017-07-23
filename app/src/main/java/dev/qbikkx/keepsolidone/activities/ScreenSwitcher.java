package dev.qbikkx.keepsolidone.activities;

import android.os.Bundle;

/**
 * Интерфейс предназначен для смены экранов. Как фрагментов так и активити.
 *
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */

public interface ScreenSwitcher {

    void switchScreenByTag(String  tag, Bundle params);

    void switchScreenByTag(String  tag);
}
