package com.example.ahmad.popularmovies_final;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ahmad.popularmovies_final.Data.MoviesContract;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by Ahmad on 8/7/2015.
 */
public class MovieCardAdapter extends CursorAdapter {


    public MovieCardAdapter(Context context, Cursor c) {
        super(context, c, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_card, parent, false);
        ViewHolderCustom viewHolder = new ViewHolderCustom(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolderCustom viewholder = (ViewHolderCustom) view.getTag();
        int col_index = cursor.getColumnIndex(MoviesContract.MoviesEntry.MOV_COL_POSTER);
        String poster_path = cursor.getString(col_index);
        String imagepath = "http://image.tmdb.org/t/p/w342" + poster_path;

        //Using Picasso to fetch the pics and populate it.
        Picasso.with(context).load(imagepath).error(R.drawable.spinner).noFade().into(viewholder.movie_poster);

//        Glide is good in fetching movies posters from the cash, but it is not reliable enough!.
//        Glide.with(context).load(imagepath).error(R.drawable.spinner).into(viewholder.movie_poster);
    }

    class ViewHolderCustom {
        public final ImageView movie_poster ;
        ViewHolderCustom(View view){
            movie_poster = (ImageView) view.findViewById(R.id.movie_image);
        }
    }
}
