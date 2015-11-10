package com.example.ahmad.popularmovies_final;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.ahmad.popularmovies_final.POJOs.MovieData;
import com.squareup.picasso.Picasso;

import java.util.Collection;


/**
 * Created by Ahmad on 7/9/2015.
 * A class extending the ArrayAdapter for a custom Object "MovieData"
 */
public class CardAdapter extends ArrayAdapter<MovieData> {


    class ViewHolder{
        ImageView movie_poster;
    }
    ViewHolder viewholder;

    public CardAdapter(Context context, int resource) {
        super(context, resource, 0);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_card, parent, false);
            viewholder = new ViewHolder();
            viewholder.movie_poster = (ImageView) convertView.findViewById(R.id.movie_image);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
            Log.d("BEBE", "Get TAg");
        }



        MovieData md = getItem(position);

        //poster Path
        String imagepath = "http://image.tmdb.org/t/p/w342" + md.getMoviePoster();

        Picasso picasso =  Picasso.with(getContext());
        picasso.setIndicatorsEnabled(true);
        picasso.setDebugging(true);
        picasso.setLoggingEnabled(true);
        //Using Picasso to fetch the pics and populate it.
        picasso.load(imagepath).placeholder(R.drawable.spinner).noFade().into(viewholder.movie_poster);

        return convertView;
    }
}
