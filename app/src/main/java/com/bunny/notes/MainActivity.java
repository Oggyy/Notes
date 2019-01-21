package com.bunny.notes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.bunny.notes.database.Notes;
import com.bunny.notes.viewModel.NotesViewModel;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NotesAdapter.onAdapterItemClickListener{
    private NotesViewModel notesViewModel;
    private RecyclerView recyclerView;
    public static final int ADD_STATIC_NOTE = 1;
    public static final int EDIT_STATIC_NOTE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NotesAdapter notesAdapter = new NotesAdapter();
        recyclerView.setAdapter(notesAdapter);

        //Observe data
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        notesViewModel.getAllNotes().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(@Nullable List<Notes> notes) {
                notesAdapter.setNotes(notes);
            }
        });

        FloatingActionButton fabAddNoteBtn = findViewById(R.id.add_notes);

        fabAddNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = (new Intent(MainActivity.this,AddNoteActivity.class));
                startActivityForResult(intent,ADD_STATIC_NOTE);
            }
        });



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT
                |ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                notesViewModel.delete(notesAdapter.getNote(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

        notesAdapter.setOnAdapterItemClickListener(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_STATIC_NOTE && resultCode == RESULT_OK && data != null){
            String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
            Notes notes = new Notes(title,description);
            notesViewModel.insert(notes);
            Toast.makeText(this,"Note Saved",Toast.LENGTH_SHORT).show();
        }else if(requestCode == EDIT_STATIC_NOTE && resultCode == RESULT_OK && data != null)  {
            int id = data.getIntExtra(AddNoteActivity.EXTRA_ID, -1);
            String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
            Notes notes = new Notes(title,description);
            notes.setId(id);
            notesViewModel.update(notes);
            Toast.makeText(this,"Note Updated",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClicked(Notes notes) {
        Intent intent = new Intent(this,AddNoteActivity.class);
        intent.putExtra(AddNoteActivity.EXTRA_ID, notes.getId());
        intent.putExtra(AddNoteActivity.EXTRA_TITLE,notes.getNoteName());
        intent.putExtra(AddNoteActivity.EXTRA_DESCRIPTION,notes.getNoteBody());
        startActivityForResult(intent,EDIT_STATIC_NOTE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_note){
            notesViewModel.deleteAllNotes();
            Toast.makeText(this,"Notes Deleted Successfully",Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);

    }
}
