package com.hackesh.arc_room_mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AppViewModel extends AndroidViewModel {
    private RoomRepository roomRepository;
    private LiveData<List<SQLEntity>> listLiveData;

    public AppViewModel(@NonNull Application application) {
        super(application);
        roomRepository=new RoomRepository(application);
        listLiveData=roomRepository.getGetallMovies();
    }

    public void insert(SQLEntity sqlEntity)
    {
        roomRepository.insert(sqlEntity);
    }
    public void update(SQLEntity sqlEntity)
    {
        roomRepository.update(sqlEntity);
    }
    public void delete(SQLEntity sqlEntity)
    {
        roomRepository.delete(sqlEntity);
    }

    public void deleteAll()
    {
        roomRepository.deleteAll();
    }

    public LiveData<List<SQLEntity>> getListLiveData() {
        return listLiveData;
    }
}
