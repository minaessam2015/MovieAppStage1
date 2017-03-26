package com.example.android.movieappstage1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.movieappstage1.NetworkUtils.NetworkUtils;
import com.example.android.movieappstage1.Utils.Movie;
import com.example.android.movieappstage1.Utils.StaticVariables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MoviesHome extends AppCompatActivity implements MovieHomeAdapter.ListItemClickListener{
    TextView textView;
    RecyclerView recyclerView;
    List<Movie> movies=new ArrayList<>();
    MovieHomeAdapter adapter;
    ProgressBar progressBar;
    TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_recycler_view);
        progressBar=(ProgressBar)findViewById(R.id.progress_bar);
        errorTextView=(TextView)findViewById(R.id.error_text_view);
        //initializes the recyclerview
        recyclerView=(RecyclerView)findViewById(R.id.movies_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter=new MovieHomeAdapter(this,movies,this);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID=item.getItemId();
        switch (itemID){
            case R.id.popular_search_item:

                //call make popular url
                URL url= NetworkUtils.makePopularSearchURL();
                new MakeHTTPCall().execute(url);
                break;
            case R.id.top_rated_search_item:
                URL url2=NetworkUtils.makeTopRatedURL();
                new MakeHTTPCall().execute(url2);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    /*handle clicks on the list*/
    @Override
    public void onItemClickListener(int position) {
       // Toast.makeText(this,"clicked item position"+position,Toast.LENGTH_LONG).show();
        Intent intent=new Intent(this,MovieDetails.class);
        intent.putExtra("position",position);
        startActivity(intent);
    }

    class MakeHTTPCall extends AsyncTask<URL,Void,String>{
        @Override
        protected void onPreExecute() {
            //clear all movies
            movies.clear();
            StaticVariables.movies.clear();
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.INVISIBLE);
            if(s!=null&&!s.equals("")){
                parseMoviesJSON(s);
                errorTextView.setVisibility(View.INVISIBLE);
                adapter.notifyDataSetChanged();
                StaticVariables.movies=movies;
            }
            else {
                //show error message
                recyclerView.setVisibility(View.INVISIBLE);
                errorTextView.setVisibility(View.VISIBLE);

            }

        }

        @Override
        protected String doInBackground(URL... urls) {
            String result=null;
            try {
                result=NetworkUtils.makeHTTPRequest(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
        protected void parseMoviesJSON(String s){
            try {
                JSONObject json=new JSONObject(s);
                JSONArray array=json.getJSONArray("results");
                int size=array.length();
                for(int i=0;i<size;++i){
                    JSONObject movie=array.getJSONObject(i);
                    String title=movie.getString("title");
                    String description=movie.getString("overview");
                    String posterPath=movie.getString("poster_path");
                    float vote=(float) movie.getDouble("vote_average");
                    String date=movie.getString("release_date");
                    movies.add(new Movie(title,description,vote,posterPath,date));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
