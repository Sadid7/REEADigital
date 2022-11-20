package com.example.reeadigital.movies.viewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.reeadigital.R;
import com.example.reeadigital.movies.model.Movie;
import java.util.List;
/**Simple adapter for movie list view*/
public class MovieListViewAdapter extends BaseAdapter {
    List<Movie> movieList;
    LayoutInflater layoutInflater;

    public MovieListViewAdapter(Context context,
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

        TextView tvMovieTitle = (TextView) view.findViewById(R.id.tv_movie_title);
        TextView tvMovieDetails = (TextView) view.findViewById(R.id.tv_movie_details);
        tvMovieTitle.setText(movieList.get(i).getMovieTitle());
        tvMovieDetails.setText(movieList.get(i).getMovieDetails());
        /**method to download images using picasso is called here */
        //ImageView movieThumbnail = (ImageView) view.findViewById(R.id.iv_movie_thumbnail);
        //loadMovieThumbnai(movieList.get(i).getThumbnailUrl(), movieThumbnail);
        return view;
    }
    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    /**method to download images using picasso is called here */
/*    private void loadMovieThumbnai(String imageUri, ImageView movieThumbnail) {
        Picasso.get()
                .load(imageUri)
                .resize(350,350)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(movieThumbnail);
    }*/
}
