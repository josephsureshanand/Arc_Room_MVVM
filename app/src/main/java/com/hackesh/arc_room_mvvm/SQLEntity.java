package com.hackesh.arc_room_mvvm;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "entity_table")
public class SQLEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "movie_name")
    private String movie;

    private int rating;

    private String genere;

    public SQLEntity(String movie, int rating, String genere) {
        this.movie = movie;
        this.rating = rating;
        this.genere = genere;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getMovie() {
        return movie;
    }

    public int getRating() {
        return rating;
    }

    public String getGenere() {
        return genere;
    }
}
