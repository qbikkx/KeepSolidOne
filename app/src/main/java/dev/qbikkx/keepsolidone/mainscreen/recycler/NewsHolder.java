package dev.qbikkx.keepsolidone.mainscreen.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Date;

import dev.qbikkx.keepsolidone.R;
import dev.qbikkx.keepsolidone.models.News;

/**
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */

public class NewsHolder extends RecyclerView.ViewHolder {

    private TextView mTitleTextView;
    private TextView mDateTextView;
    private ImageView mImageView;

    public NewsHolder(LayoutInflater inflater, ViewGroup parent, final OnNewsItemClickListener listener) {
        super(inflater.inflate(R.layout.news_list_item, parent, false));
        mTitleTextView = (TextView) itemView.findViewById(R.id.tv_news_item_title);
        mDateTextView = (TextView) itemView.findViewById(R.id.tv_news_item_date);
        mImageView = (ImageView) itemView.findViewById(R.id.iv_news_item_image);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v, getAdapterPosition());
            }
        });
    }

    public void bind(News news) {
        mTitleTextView.setText(news.getTitle());
        Date date = news.getPublishedAt();
        if (date != null) {
            mDateTextView.setText(date.toString());
        }
        Picasso.with(itemView.getContext())
                .load(news.getUrlToImage())
                //optimize image size in cache
                .fit()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(mImageView);
    }
}
