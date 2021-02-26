package com.hackesh.arc_room_mvvm;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RoomRepository {

    private RoomDao roomDao;

    private LiveData<List<SQLEntity>> getallMovies;

    public RoomRepository(Application application) {
        RoomDatabase roomDatabase=RoomDatabase.getInstance(application);
        roomDao=roomDatabase.roomDao();
        getallMovies=roomDao.getAllMovies();
    }

    public void insert(SQLEntity sqlEntity)
    {
        new InsertAsyn(roomDao).execute(sqlEntity);

    }
    public void update(SQLEntity sqlEntity)
    {
        new UpdateAsyn(roomDao).execute(sqlEntity);
    }
    public void delete(SQLEntity sqlEntity)
    {
        new DeleteAsyn(roomDao).execute(sqlEntity);
    }
    public void deleteAll()
    {
        new DeleteAllAsyn(roomDao).execute();
    }

    public LiveData<List<SQLEntity>> getGetallMovies() {
        return getallMovies;
    }

    private static class InsertAsyn extends AsyncTask<SQLEntity,Void,Void>
    {
        private RoomDao roomDao;

        public InsertAsyn(RoomDao roomDao) {
            this.roomDao = roomDao;
        }

        @Override
        protected Void doInBackground(SQLEntity... sqlEntities) {
            roomDao.insert(sqlEntities[0]);
            return null;
        }
    }
    private static class UpdateAsyn extends AsyncTask<SQLEntity,Void,Void>
    {
        private RoomDao roomDao;

        public UpdateAsyn(RoomDao roomDao) {
            this.roomDao = roomDao;
        }

        @Override
        protected Void doInBackground(SQLEntity... sqlEntities) {
            roomDao.update(sqlEntities[0]);
            return null;
        }
    }
    private static class DeleteAsyn extends AsyncTask<SQLEntity,Void,Void>
    {
        private RoomDao roomDao;

        public DeleteAsyn(RoomDao roomDao) {
            this.roomDao = roomDao;
        }

        @Override
        protected Void doInBackground(SQLEntity... sqlEntities) {
            roomDao.delete(sqlEntities[0]);
            return null;
        }
    }
    private static class DeleteAllAsyn extends AsyncTask<Void,Void,Void>
    {
        private RoomDao roomDao;

        public DeleteAllAsyn(RoomDao roomDao) {
            this.roomDao = roomDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            roomDao.deleteAll();
            return null;
        }
    }

}

