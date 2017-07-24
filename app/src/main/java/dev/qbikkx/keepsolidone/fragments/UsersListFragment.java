package dev.qbikkx.keepsolidone.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.UUID;

import dev.qbikkx.keepsolidone.R;
import dev.qbikkx.keepsolidone.activities.UserActivity;
import dev.qbikkx.keepsolidone.dialogs.CancelDialog;
import dev.qbikkx.keepsolidone.dialogs.OkDialog;
import dev.qbikkx.keepsolidone.models.User;
import dev.qbikkx.keepsolidone.models.UsersDataBase;
import dev.qbikkx.keepsolidone.recycler.OnUserItemClickListener;
import dev.qbikkx.keepsolidone.recycler.UsersListAdapter;
import dev.qbikkx.keepsolidone.utils.Constants;

/**
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class UsersListFragment extends AppCompatDialogFragment {
    public final static String TAG = "UsersListFragment";

    public final static String EXTRA_SELECTED_ID = "dev.qbikkx.keepsolidone.fragments.UsersListFragmet.selected_id";
    private RecyclerView recyclerView;
    private UsersListAdapter adapter;

    public static UsersListFragment newInstance() {
        return new UsersListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.users_list_fragment, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_users_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new UsersListAdapter(UsersDataBase.getInstance().getUsersList(), new OnUserItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                User user = adapter.getUser(position);
                startSenderActivity(user.getId());
            }
        });
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    private void startSenderActivity(UUID userId) {
        Intent intent = new Intent(getActivity(), UserActivity.class);
        ParcelUuid parcelUuid = new ParcelUuid(userId);
        intent.putExtra(EXTRA_SELECTED_ID, parcelUuid);
        startActivityForResult(intent, Constants.REQUEST_SEND_BTN_ACTION);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Constants.REQUEST_SEND_BTN_ACTION == requestCode) {
            DialogFragment dialog;
            switch (resultCode) {
                case Activity.RESULT_OK:
                    dialog = new OkDialog();
                    dialog.show(getFragmentManager(), OkDialog.TAG);
                    break;
                case Activity.RESULT_CANCELED:
                    dialog = new CancelDialog();
                    dialog.show(getFragmentManager(), CancelDialog.TAG);
                    break;
            }
        }
    }
}