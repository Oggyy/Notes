package com.bunny.notes.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "notes_table")
public class Notes {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String noteName;

    private String noteBody;

    public Notes(String noteName, String noteBody) {
        this.noteName = noteName;
        this.noteBody = noteBody;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteName() {
        return noteName;
    }

    public String getNoteBody() {
        return noteBody;
    }

 }
