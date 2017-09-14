package dev.qbikkx.keepsolidone.detailscreen;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import dev.qbikkx.keepsolidone.R;

/**
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class NewsFragment extends Fragment {
    private final static String URI_ARG = "uri_arg";

    private FrameLayout mProgressLine;

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
        mProgressLine = (FrameLayout) rootView.findViewById(R.id.fl_progress);

        WebView webView = (WebView) rootView.findViewById(R.id.wv_news);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressLine.setVisibility(ProgressBar.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressLine.setVisibility(ProgressBar.GONE);
            }
        });
        webView.loadUrl(String.valueOf(getArguments().getParcelable(URI_ARG)));
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
