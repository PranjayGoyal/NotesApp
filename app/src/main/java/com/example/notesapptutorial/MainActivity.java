package com.example.notesapptutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    // back button not working.

    private EditText mloginmail, mloginpassword;
    private RelativeLayout mlogin,mgotosignup;
    private TextView mgotoforgetpass;
    private FirebaseAuth firebaseAuth;
    ProgressBar progressBarOfMainAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();  // here we are taking the current user details

        mloginmail = findViewById(R.id.loginEmail);
        mloginpassword = findViewById(R.id.loginPassword);
        mlogin = findViewById(R.id.login);
        mgotosignup = findViewById(R.id.gotoSignUp);
        mgotoforgetpass = findViewById(R.id.gotoForgetPassword);
        progressBarOfMainAct = findViewById(R.id.progressBarOfMainAct);


        if(firebaseUser != null){   // this code is because if the user has once logged in he or she need not to login again and again
            finish();
            startActivity(new Intent(MainActivity.this,notesActvity.class));
        }


        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = mloginmail.getText().toString().trim();
                String password = mloginpassword.getText().toString().trim();

                if(mail.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter the full details", Toast.LENGTH_SHORT).show();
                }
                else{
                    progressBarOfMainAct.setVisibility(View.VISIBLE);

                    firebaseAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                //we have to check whether the user has verified the email or not.
                                checkMailVerification();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "you have still not verified your email, Account doesn't exist. Sorry", Toast.LENGTH_LONG).show();
                                progressBarOfMainAct.setVisibility(View.INVISIBLE);
                            }
                        }
                    });

                }
            }
        });

        mgotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SignUp.class));
            }
        });

        mgotoforgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, forgotPassword.class);
                startActivity(intent);
            }
        });
    }
    private void checkMailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser.isEmailVerified() == true){
            Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(MainActivity.this, notesActvity.class));
        }
        else{
            progressBarOfMainAct.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "First Verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}