package com.example.notesapptutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.rpc.context.AttributeContext;

public class forgotPassword extends AppCompatActivity {
    private EditText mforgetPassword;
    private Button mPasswordRecoverButton;
    private TextView mgotologin;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        firebaseAuth = FirebaseAuth.getInstance();

        getSupportActionBar().hide();

        mforgetPassword = findViewById(R.id.forgotPassword);
        mPasswordRecoverButton = findViewById(R.id.PasswordRecoverButton);
        mgotologin = findViewById(R.id.gobacktologin);

        mgotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(forgotPassword.this,MainActivity.class);
                startActivity(intent);
            }
        });

        mPasswordRecoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = mforgetPassword.getText().toString().trim(); // trim function eleminates the leading or trailing spaces
                if(mail.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter Your Mail First!", Toast.LENGTH_SHORT).show();
                }
                else{
                    //we have to send password recovery mail
                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Email has been sent to you to reset your password!", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(forgotPassword.this,MainActivity.class));
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Sorry Couldn't send you the reset password email", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });

    }
}