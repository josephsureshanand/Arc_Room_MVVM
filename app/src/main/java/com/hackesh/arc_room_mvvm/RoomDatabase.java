package com.hackesh.arc_room_mvvm;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {SQLEntity.class},version = 1)
public abstract class RoomDatabase extends androidx.room.RoomDatabase {

    private static RoomDatabase instance;

    public abstract RoomDao roomDao();

    public static synchronized RoomDatabase getInstance(Context context)
    {
        if (instance==null)
        {
            instance= Room.databaseBuilder(context.getApplicationContext(),RoomDatabase.class,"room_database")
            .fallbackToDestructiveMigration()
            .addCallback(roomCallback)
            .build();
        }
        return instance;

    }

    private static androidx.room.RoomDatabase.Callback roomCallback = new androidx.room.RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new AddSomeValues(instance).execute();
        }
    };

    private static class AddSomeValues extends AsyncTask<Void,Void,Void>
    {
        private RoomDao roomDao;

        public AddSomeValues(RoomDatabase database) {
            this.roomDao = database.roomDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            roomDao.insert(new SQLEntity("TENET",8,"Sci-fi"));
            roomDao.insert(new SQLEntity("In Time",7,"Sci-fi"));
            roomDao.insert(new SQLEntity("Master",6,"Action"));
            return null;
        }
    }

}
