package com.example.ahmad.popularmovies_final;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahmad.popularmovies_final.data.MoviesContract;
import com.squareup.picasso.Picasso;

/**
 * Created by Ahmad on 8/23/2015.
 */
public class ReviewsAdapter extends CursorAdapter{

    int author_col_index;
    int content_col_index;

    public ReviewsAdapter(Context context, Cursor c) {
        super(context, c, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view  = LayoutInflater.from(context).inflate(R.layout.review_item, parent, false);
        ReviewViewHolder viewHolder = new ReviewViewHolder(view);
        view.setTag(viewHolder);

        //Get the Column index for the author, and the content.
        author_col_index = cursor.getColumnIndex(MoviesContract.ReviewsEntry.REV_COL_AUTHOR);
        content_col_index = cursor.getColumnIndex(MoviesContract.ReviewsEntry.REV_COL_CONTENT);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ReviewViewHolder viewHolder = (ReviewViewHolder) view.getTag();


        String author = cursor.getString(author_col_index);
        String content = cursor.getString(content_col_index);

        //bind the data (Strings, and pics) to video.
        viewHolder.textView_author.setText(author);
        viewHolder.textView_content.setText(content);
        Picasso.with(context).load(R.drawable.reviewer_icon).into(viewHolder.reviewer_icon);

    }

    class ReviewViewHolder {
        public final TextView textView_author;
        public final TextView textView_content;
        public final ImageView reviewer_icon;
        ReviewViewHolder(View view){

            textView_author = (TextView) view.findViewById(R.id.textView_review_author);
            textView_content = (TextView) view.findViewById(R.id.textView_review_content);
            reviewer_icon = (ImageView) view.findViewById(R.id.reviewer_icon);
        }
    }
}
