package com.bunny.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTitle, editDescription;
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_DESCRIPTION = "extra_desc";
    public static final String EXTRA_ID = "note_id";
    String source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTitle = findViewById(R.id.et_title);
        editDescription = findViewById(R.id.et_description);


        if (getIntent().hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            editTitle.setText(getIntent().getStringExtra(AddNoteActivity.EXTRA_TITLE));
            editDescription.setText(getIntent().getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION));
        }else {
            setTitle("Add Note");

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_btn_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:
                saveNotes();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveNotes() {
        String title = editTitle.getText().toString().trim();
        String description = editDescription.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty()){
            Toast.makeText(this,"Please add title and description",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = getIntent();
        intent.putExtra(EXTRA_TITLE,title);
        intent.putExtra(EXTRA_DESCRIPTION,description);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            intent.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK,intent);
        finish();
    }


}
