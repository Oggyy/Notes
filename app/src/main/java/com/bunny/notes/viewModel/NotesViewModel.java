package com.bunny.notes.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.bunny.notes.database.Notes;
import com.bunny.notes.database.NotesRepository;
import java.util.List;


public class NotesViewModel extends AndroidViewModel {
    private NotesRepository notesRepository;
    private LiveData<List<Notes>> allNotes;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        notesRepository = new NotesRepository(application);
        allNotes = notesRepository.getAllNotes();
    }

    public void insert(Notes notes){
        notesRepository.insert(notes);

    }

    public void update(Notes notes){
        notesRepository.update(notes);
    }

    public void delete(Notes notes){
        notesRepository.delete(notes);
    }

    public void deleteAllNotes(){
        notesRepository.deleteAllNotes();
    }


    public LiveData<List<Notes>> getAllNotes(){
        return allNotes;
    }
}
