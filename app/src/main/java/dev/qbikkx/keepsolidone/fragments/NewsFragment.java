package dev.qbikkx.keepsolidone.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dev.qbikkx.keepsolidone.R;

/**
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class NewsFragment extends Fragment {
    private final static String URI_ARG = "uri_arg";

    public static NewsFragment newInstance(Uri url) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(URI_ARG, url);
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        WebView webView = (WebView) rootView.findViewById(R.id.wv_news);
        webView.loadUrl(String.valueOf(getArguments().getParcelable(URI_ARG)));
        webView.setWebViewClient(new WebViewClient());
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.news_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                shareNews();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareNews() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        Uri url = getArguments().getParcelable(URI_ARG);
        if (url != null) {
            shareIntent.putExtra(Intent.EXTRA_TEXT, url.toString());
            shareIntent.setType("text/plain");
            PackageManager packageManager = getActivity().getPackageManager();
            if (shareIntent.resolveActivity(packageManager) != null) {
                startActivity(Intent.createChooser(shareIntent, getResources().getString(R.string.share_chooser)));
            }
        }
    }
}
