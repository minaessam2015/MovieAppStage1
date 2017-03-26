package com.example.android.movieappstage1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movieappstage1.NetworkUtils.NetworkUtils;
import com.example.android.movieappstage1.Utils.Movie;
import com.example.android.movieappstage1.Utils.StaticVariables;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class MovieDetails extends AppCompatActivity {
    ImageView movieImage;
    TextView movieTitle;
    TextView movieDate;
    TextView movieOverview;
    TextView movieRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        movieImage=(ImageView)findViewById(R.id.movie_image_onDetails);
        movieTitle=(TextView)findViewById(R.id.title_text_onDetails);
        movieRating=(TextView)findViewById(R.id.rating_text_onDetails);
        movieDate=(TextView)findViewById(R.id.date_text_onDetails);
        movieOverview=(TextView)findViewById(R.id.overview_text_onDetails);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        int position=bundle.getInt("position");
        Movie movie= StaticVariables.movies.get(position);
        URL url= NetworkUtils.makePosterpathURL(movie.getPosterPath());
        Picasso.with(this).load(url.toString()).error(R.drawable.mimo_pic).into(movieImage);
        movieTitle.setText(movie.getTitle());
        movieRating.setText(String.valueOf(movie.getVote()));
        movieDate.setText(movie.getDate());
        movieOverview.setText(movie.getDescription());

    }
}
