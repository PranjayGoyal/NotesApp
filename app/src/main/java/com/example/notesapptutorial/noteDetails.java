package com.example.notesapptutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

public class noteDetails extends AppCompatActivity {
    private TextView mtitleOfNoteDetails, mContentOfNoteDetails;
    FloatingActionButton mgotoeditnote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        mtitleOfNoteDetails = findViewById(R.id.TitleOfNoteDetails);
        mContentOfNoteDetails = findViewById(R.id.ContentOfNoteDetails);
        mgotoeditnote = findViewById(R.id.goToEditNote);

        Toolbar toolbar = findViewById(R.id.toolBarOfNoteDetails);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent data = getIntent();
        mgotoeditnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),editNoteAcitivity.class);
                intent.putExtra("title",data.getStringExtra("title"));
                intent.putExtra("content",data.getStringExtra("content"));
                intent.putExtra("noteId",data.getStringExtra("docId"));
                view.getContext().startActivity(intent);
            }
        });
        mContentOfNoteDetails.setText(data.getStringExtra("content"));
        mtitleOfNoteDetails.setText(data.getStringExtra("title"));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();

        }
        return super.onOptionsItemSelected(item);
    }
}