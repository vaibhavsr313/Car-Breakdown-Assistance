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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    TextView gotoSignupfromLogin,nameinmechanic;
    Button login,AdminLogin;
    FirebaseAuth mAuth;
    EditText emailinlogin,passwordinlogin,passwordadmin,adminphone;
    ProgressBar progressBar;
    TextView iamuser,iamadmin;
    TextInputLayout textpass;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailinlogin = findViewById(R.id.emailinlogin);
        passwordinlogin = findViewById(R.id.passwordinlogin);
        login = findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar);
        iamadmin = findViewById(R.id.iamadmin);
        iamuser = findViewById(R.id.iamuser);
        passwordadmin = findViewById(R.id.passwordadmin);
        adminphone = findViewById(R.id.adminphone);
        AdminLogin = findViewById(R.id.AdminLogin);
        textpass = findViewById(R.id.textpass);//TextInput
        nameinmechanic = findViewById(R.id.nameinmechanic);

        iamadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                login.setText("Admin Login");
                iamadmin.setVisibility(View.INVISIBLE);
                iamuser.setVisibility(View.VISIBLE);
                AdminLogin.setVisibility(View.VISIBLE);
                login.setVisibility(View.INVISIBLE);
                adminphone.setVisibility(View.VISIBLE);
                textpass.setVisibility(View.VISIBLE);
                emailinlogin.setVisibility(View.INVISIBLE);
                passwordinlogin.setVisibility(View.INVISIBLE);
//                parentDbName = "Admins";
            }
        });

        AdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String phone;
                String password;
                final String[] name = new String[1];
                phone = adminphone.getText().toString();
                password = passwordadmin.getText().toString();

                if (TextUtils.isEmpty(phone)){
                    Toast.makeText(Login.this, "Enter Phone No", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(Login.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                final DatabaseReference RootRef;
                RootRef = FirebaseDatabase.getInstance().getReference();
                RootRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        progressBar.setVisibility(View.GONE);
                        if (snapshot.child("Mechanic").child(phone).exists())
                        {

                            Users usersData = snapshot.child("Mechanic").child(phone).getValue(Users.class);

                            name[0] = usersData.getName();

                            if (usersData.getPhone().equals(phone))
                            {
                                if (usersData.getPassword().equals(password))
                                {
                                    Toast.makeText(Login.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this,MechanicHome.class);
                                    intent.putExtra("name1", name[0]);
                                    intent.putExtra("phone1", phone);
                                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                        }
                        else {
                            Toast.makeText(Login.this, "Wrong Phone No. or password",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });



        iamuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.setText("Login");
                iamadmin.setVisibility(View.VISIBLE);
                iamuser.setVisibility(View.INVISIBLE);
                AdminLogin.setVisibility(View.INVISIBLE);
                login.setVisibility(View.VISIBLE);
                adminphone.setVisibility(View.INVISIBLE);
                textpass.setVisibility(View.INVISIBLE);
                emailinlogin.setVisibility(View.VISIBLE);
                passwordinlogin.setVisibility(View.VISIBLE);
//                parentDbName = "Users";
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email,password;
                email = String.valueOf(emailinlogin.getText());
                password = String.valueOf(passwordinlogin.getText());

                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(Login.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(Login.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),Homepage.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                    finish();
                                }
                                else
                                {

                                    Toast.makeText(Login.this, "Wrong Email or Password",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        gotoSignupfromLogin = findViewById(R.id.gotoSignupfromLogin);
        gotoSignupfromLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(Login.this,SignUp.class); //don't have an account button
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
            }
        });
    }
}