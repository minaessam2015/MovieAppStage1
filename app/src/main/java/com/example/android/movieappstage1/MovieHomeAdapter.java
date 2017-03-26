package com.example.android.movieappstage1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.movieappstage1.NetworkUtils.NetworkUtils;
import com.example.android.movieappstage1.Utils.Movie;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

/**
 * Created by mina essam on 26-Mar-17.
 */

public class MovieHomeAdapter extends RecyclerView.Adapter<MovieHomeAdapter.ViewHolder> {
    private Context context;
    private List<Movie> movies;
    private final ListItemClickListener itemClickListener;

    public interface ListItemClickListener{
         void onItemClickListener(int position);
    }
    public MovieHomeAdapter(Context context,List<Movie> movies,ListItemClickListener itemClickListener){
        this.context=context;
        this.movies=movies;
        this.itemClickListener=itemClickListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.movie_row_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie=movies.get(position);
       /* holder.titleTextView.setText(movie.getTitle());
        holder.descriptionTextView.setText(movie.getDescription());
        holder.voteTextView.setText(String.valueOf(movie.getVote()));*/
        URL url= NetworkUtils.makePosterpathURL(movie.getPosterPath());
        Picasso.with(context).load(url.toString()).error(R.drawable.mimo_pic).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

  /*  public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView titleTextView;
        public final TextView descriptionTextView;
        public final TextView voteTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView=(TextView)itemView.findViewById(R.id.title_text);
            descriptionTextView=(TextView)itemView.findViewById(R.id.description_text);
            voteTextView=(TextView) itemView.findViewById(R.id.vote_text_view);
        }
    }*/
  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
       public final ImageView imageView;
      public ViewHolder(View view){
          super(view);
          imageView=(ImageView)view.findViewById(R.id.movie_image);
          view.setOnClickListener(this);

      }

      @Override
      public void onClick(View view) {
          int position=getAdapterPosition();
          itemClickListener.onItemClickListener(position);
      }
  }

}
