package com.example.abreak;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    TextView gotologinfromsignup;
    Button signup;
    FirebaseAuth mAuth;
    EditText emailinsignup,passwordinsignup;
    ProgressBar progressBar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        gotologinfromsignup = findViewById(R.id.gotologinfromsignup);
        signup = findViewById(R.id.signup);
        mAuth = FirebaseAuth.getInstance();
        emailinsignup = findViewById(R.id.emailinsignup);
        passwordinsignup = findViewById(R.id.passwordinsignup);
        progressBar = findViewById(R.id.progressbar);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                progressBar.setVisibility(View.VISIBLE);
                String email,password;
                email = String.valueOf(emailinsignup.getText());
                password = String.valueOf(passwordinsignup.getText());

                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(SignUp.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(SignUp.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "Authentication Success.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),Profile.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                                    finish();

                                } else {
                                    Toast.makeText(SignUp.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });

        gotologinfromsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(SignUp.this,Login.class); //don't have an account button
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
            }
        });
    }
}