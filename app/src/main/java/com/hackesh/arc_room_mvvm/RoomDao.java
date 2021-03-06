package com.hackesh.arc_room_mvvm;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RoomDao {
//he thisi s me on git
    @Insert
    void insert(SQLEntity sqlEntity);

    @Update
    void update(SQLEntity sqlEntity);

    @Delete
    void delete(SQLEntity sqlEntity);

    @Query("DELETE  FROM entity_table")
    void deleteAll();

    @Query("SELECT * FROM entity_table ORDER BY movie_name DESC")
    LiveData<List<SQLEntity>> getAllMovies();

}
