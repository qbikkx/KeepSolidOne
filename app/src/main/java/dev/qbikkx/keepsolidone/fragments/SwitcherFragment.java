package dev.qbikkx.keepsolidone.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;

import dev.qbikkx.keepsolidone.activities.ScreenSwitcher;

/**
 * Базовый класс для фрагментов в проекте. Подключает слушателя реализующего интерфейс
 * @see ScreenSwitcher
 *
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public abstract class SwitcherFragment extends Fragment {
    protected ScreenSwitcher mScreenSwitcher;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mScreenSwitcher = (ScreenSwitcher) context;
        } catch (ClassCastException ex) {
            throw new ClassCastException(context.toString() + "must be a string");
        }
    }
}
