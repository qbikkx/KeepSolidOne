package dev.qbikkx.keepsolidone.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import dev.qbikkx.keepsolidone.R;
import dev.qbikkx.keepsolidone.fragments.CancelFragment;
import dev.qbikkx.keepsolidone.fragments.DataFragment;
import dev.qbikkx.keepsolidone.fragments.OkFragment;
import dev.qbikkx.keepsolidone.utils.Constants;

/**
 * MainActivity. Реализует интерфейс
 * @see ScreenSwitcher
 * Управляет переключениями фрагментов.
 *
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class MainActivity extends AppCompatActivity implements ScreenSwitcher {
    private final static String TAG = "MainActivity";

    private String currentFragmentTag = null;

    /**
     * Хранит значение поля с email
     * Поле оказалось необходимо, т.к. при переходе с SenderActivity
     * Стартует CancelFragment которому ничего не надо знать о имейле
     * Следовательно Активити перед стартом фрагмента перехватывает имейл и запоминает
     * Похоже на костыль. Нужен будет код ревью)
     */
    private Bundle dataParams = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            startFragment(DataFragment.TAG);
        } else {
            currentFragmentTag = savedInstanceState.getString("currentFragment");
            dataParams = savedInstanceState.getBundle("dataParams");
            startFragment(currentFragmentTag, dataParams);
        }
    }

    /**
     * в case RESULT_CANCELED не нравится, что приходится запоминать текст.
     * Всё теже проблемы с полем dataParams
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Constants.REQUEST_SEND_BTN_ACTION == requestCode) {
            switch (resultCode) {
                case RESULT_OK:
                    startFragment(OkFragment.TAG, null);
                    break;
                case RESULT_CANCELED:
                    if (data != null) {
                        String text = data.getStringExtra(DataFragment.EXTRA_INPUT_TEXT);
                        if (text != null) {
                            dataParams = new Bundle();
                            dataParams.putString(DataFragment.EXTRA_INPUT_TEXT, text);
                        }
                    }
                    startFragment(CancelFragment.TAG);
                    break;
            }
        }
    }

    private void startFragment(String tag) {
        startFragment(tag, null);
    }

    /**
     * Хотелось реализовать единый механизм для старта компонент (интерфейс ScreenSwitcher),
     * но появилась проблема с полем dataParams (При увеличении кол-ва данных для хранения
     * проблема будет разрастаться)
     * @param tag - Тэг компоненты, которую запускаем.
     * @param params - Bundle с параметрами которые передаем в компоненту.
     */
    private void startFragment(String tag, Bundle params) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (tag) {
                case DataFragment.TAG:
                    fragment = DataFragment.newInstance(params);
                    break;
                case OkFragment.TAG:
                    fragment = OkFragment.newInstance();
                    break;
                case CancelFragment.TAG:
                    fragment = CancelFragment.newInstance();
                    break;
                default:
                    Log.e(TAG, "unknown_start_fragment_error");
                    return;
            }
            currentFragmentTag = tag;
            transaction.setCustomAnimations(R.anim.slide_left_animation, R.anim.slide_out_right);
            transaction.replace(R.id.fl_fragment_container, fragment, tag);
            transaction.commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("currentFragment", currentFragmentTag);
        if (dataParams != null) {
            outState.putBundle("dataParams", dataParams);
        }
        super.onSaveInstanceState(outState);
    }

    /**
     *
     * @param tag - Компонента, которая вызвала смену экрана
     * @param params - Параметры для передачи в следующую компоненту
     */
    @Override
    public void switchScreenByTag(String tag, Bundle params) {
        switch(tag) {
            case DataFragment.TAG:
                startSenderActivity(this, SenderActivity.class, params);
                break;
            case OkFragment.TAG:
                startFragment(DataFragment.TAG, params);
                break;
            case CancelFragment.TAG:
                params = dataParams;
                startFragment(DataFragment.TAG, params);
                break;
        }
    }

    @Override
    public void switchScreenByTag(String tag) {
        switchScreenByTag(tag, null);
    }

    /**
     * Стартуем вторую активити
     * @see SenderActivity
     */
    private void startSenderActivity(Activity from, Class to, Bundle params) {
        Intent senderIntent = new Intent(from, to);
        if (to == SenderActivity.class) {
            String extra = params.getString(DataFragment.EXTRA_INPUT_TEXT);
            if(extra != null && !extra.isEmpty()) {
                senderIntent.putExtra(DataFragment.EXTRA_INPUT_TEXT, extra);
                deleteCurrentFragment();
                startActivityForResult(senderIntent, Constants.REQUEST_SEND_BTN_ACTION);
            }
        }
    }

    /**
     * При переходе со второй активити в OnResult сначала показывается DataFragment
     * и только потом открывается нужный фрагмент.
     * Костыль, надо придумать решение красивее
     */
    private void deleteCurrentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(currentFragmentTag);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }

}
