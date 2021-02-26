package com.hackesh.arc_room_mvvm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.RecycleViewHolder> {

    OnItemClickListener listener;
    List<SQLEntity> listLiveData = new ArrayList<>();

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new RecycleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {
        SQLEntity sqlEntity = listLiveData.get(position);
        holder.text_movie.setText(sqlEntity.getMovie());
        holder.text_rating.setText(String.valueOf(sqlEntity.getRating()));
        holder.text_genre.setText(sqlEntity.getGenere());

    }

    @Override
    public int getItemCount() {
        return listLiveData.size();
    }

    public void setMovies(List<SQLEntity> sqlEntities) {
        this.listLiveData = sqlEntities;
        notifyDataSetChanged();
    }

    public SQLEntity getEntry(int position) {
        return listLiveData.get(position);
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder {
        TextView text_movie;
        TextView text_rating;
        TextView text_genre;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            text_movie = itemView.findViewById(R.id.text_movie);
            text_genre = itemView.findViewById(R.id.text_genre);
            text_rating = itemView.findViewById(R.id.text_rating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(listLiveData.get(position));
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(SQLEntity sqlEntity);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }

}
