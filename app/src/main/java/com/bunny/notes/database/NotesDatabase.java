package com.bunny.notes.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Notes.class},version = 1)
public abstract class NotesDatabase extends RoomDatabase {

    private static NotesDatabase noteDataBaseInstance;

    public abstract NotesDAO notesDAO();

    public static synchronized NotesDatabase getInstance(Context context){

        if (noteDataBaseInstance == null){
            noteDataBaseInstance = Room.databaseBuilder(context,NotesDatabase.class,"notes_database")
                    .addCallback(roomCallback)
            .fallbackToDestructiveMigration().build();
        }

        return noteDataBaseInstance;

    }

    //populate view with some dummy data
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(noteDataBaseInstance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private NotesDAO notesDAO;

        public PopulateDbAsyncTask(NotesDatabase notesDatabase) {
            notesDAO = notesDatabase.notesDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            notesDAO.insert(new Notes("Title 1","this is test description"));
            notesDAO.insert(new Notes("Title 2","this is test description"));
            notesDAO.insert(new Notes("Title 3","this is test description"));

            return null;
        }
    }
}
