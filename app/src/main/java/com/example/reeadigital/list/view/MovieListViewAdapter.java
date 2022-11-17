package com.example.reeadigital.list.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.reeadigital.R;
import com.example.reeadigital.list.model.Movie;
import java.util.List;

public class MovieListViewAdapter extends BaseAdapter {
    List<Movie> movieList;
    LayoutInflater layoutInflater;
    MovieListViewAdapter(Context context,
                         List<Movie> movieList) {
        this.movieList = movieList;
        this.layoutInflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        if (movieList == null) {
            return 0;
        } else {
            return movieList.size();
        }
    }

    @Override
    public Object getItem(int i) {
        return movieList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return movieList.get(i).getMovieId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.layoutInflater.inflate(R.layout.movie_listview_single_row,
                    viewGroup,
                    false);
        }

        TextView tvUserName = (TextView) view.findViewById(R.id.tv_movie_title);
        TextView tvUserEmail = (TextView) view.findViewById(R.id.tv_movie_details);
        ImageView ivUserImage = (ImageView) view.findViewById(R.id.iv_movie_thumbnail);
        tvUserName.setText(movieList.get(i).getMovieTitle());
        tvUserEmail.setText(movieList.get(i).getMovieDetails());
        /**method to download images using picasso */
        //loadUserImage(userInfoList.get(i).getImageUri(), ivUserImage);
        return view;
    }

    /*private void loadUserImage(String imageUri, ImageView userImageView) {
        Picasso.get()
                .load(imageUri)
                .resize(350,350)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(userImageView);
    }*/

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }
}
