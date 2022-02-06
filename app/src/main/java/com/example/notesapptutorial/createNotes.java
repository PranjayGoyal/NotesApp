package com.example.notesapptutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class createNotes extends AppCompatActivity {

    EditText mCreateTitle , mCreateContent;
    FloatingActionButton saveButton;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore; // to access the database
    ProgressBar progressBarOfCreateNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notes);

        saveButton = findViewById(R.id.saveNote);
        mCreateContent = findViewById(R.id.createContent);
        mCreateTitle = findViewById(R.id.createTitleOfNote);
        progressBarOfCreateNote = findViewById(R.id.progressBarOfCreateNote);


        Toolbar toolbar = findViewById(R.id.toolBarOfCreateNote);
        setSupportActionBar(toolbar); // we have set a custom toolbar that we have made
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // creates a go back button on the top
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mCreateTitle.getText().toString();
                String content = mCreateContent.getText().toString();
                if(title.isEmpty() || content.isEmpty()){
                    Toast.makeText(getApplicationContext(), "both fields are required", Toast.LENGTH_SHORT).show();
                }
                else{

                    progressBarOfCreateNote.setVisibility(View.VISIBLE);
                    // we store data in firestore using documents
                    DocumentReference documentReference = firebaseFirestore.collection("note").document(firebaseUser.getUid()).collection("myNotes").document();
                    Map<String, Object> note = new HashMap<>();
                    note.put("title",title);  // here "title" word refers to the object of title  created above.
                    note.put("content", content);


                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            Toast.makeText(getApplicationContext(), "Notes Created Successfully", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(createNotes.this,notesActvity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBarOfCreateNote.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "Failed To Create Notes", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}