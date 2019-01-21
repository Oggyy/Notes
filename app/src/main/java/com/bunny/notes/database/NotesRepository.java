package com.bunny.notes.database;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import java.util.List;

public class NotesRepository {

    private NotesDAO notesDAO;
    private LiveData<List<Notes>> allNotes;

    public NotesRepository(Application application){

        NotesDatabase notesDatabase = NotesDatabase.getInstance(application);
        notesDAO = notesDatabase.notesDAO();
        allNotes = notesDAO.getAllNotes();
    }

    public void insert(Notes notes){new insertAsyncTask(notesDAO).execute(notes);}

    public void update(Notes notes){new updateAsyncTask(notesDAO).execute(notes);}

    public void delete(Notes notes){new deleteAsyncTask(notesDAO).execute(notes);}

    public void deleteAllNotes(){new deleteAllNotesAsyncTask(notesDAO).execute( );}

    public LiveData<List<Notes>> getAllNotes(){
        return allNotes;
    }

    private static class insertAsyncTask extends AsyncTask<Notes,Void,Void>{
        private NotesDAO notesDAO;

        private insertAsyncTask(NotesDAO notesDAO) {
            this.notesDAO = notesDAO;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            notesDAO.insert(notes[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Notes,Void,Void>{
        private NotesDAO notesDAO;

        private updateAsyncTask(NotesDAO notesDAO) {
            this.notesDAO = notesDAO;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            notesDAO.update(notes[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Notes,Void,Void>{
        private NotesDAO notesDAO;

        private deleteAsyncTask(NotesDAO notesDAO) {
            this.notesDAO = notesDAO;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            notesDAO.delete(notes[0]);
            return null;
        }
    }

    private static class deleteAllNotesAsyncTask extends AsyncTask<Void,Void,Void>{
        private NotesDAO notesDAO;

        private deleteAllNotesAsyncTask(NotesDAO notesDAO) {
            this.notesDAO = notesDAO;
        }

        @Override
        protected Void doInBackground(Void... Void) {
            notesDAO.deleteAllNotes();
            return null;
        }
    }


}
