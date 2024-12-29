package com.example.abreak;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Homepage extends AppCompatActivity {

    FirebaseAuth mAuth;
    //Button logoutinhome;
    FirebaseUser user;
    TextView userinhome;
    ImageView Profileinhome;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    CardView findmechinhomepage,profileyourinhomepage,contttactusinhomepage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        mAuth = FirebaseAuth.getInstance();
        //logoutinhome = findViewById(R.id.logoutinhome);
        userinhome = findViewById(R.id.userinhome);
        Profileinhome = findViewById(R.id.Profileinhome);
        findmechinhomepage = findViewById(R.id.findmechinhomepage);
        profileyourinhomepage = findViewById(R.id.profileyourinhomepage);
        contttactusinhomepage = findViewById(R.id.contttactusinhomepage);

        findmechinhomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),usermapact.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
            }
        });

        profileyourinhomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),mepage.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
            }
        });

        contttactusinhomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),queriespageinuser.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
            }
        });


        user = mAuth.getCurrentUser();
        if(user==null)
        {
            Intent intent = new Intent(getApplicationContext(),logsignin.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            finish();
        }
//        logoutinhome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intent = new Intent(getApplicationContext(),logsignin.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
//                finish();
//            }
//        });

        String userId = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("dataName").getValue().toString();
                String image = snapshot.child("dataImage").getValue().toString();

                userinhome.setText("Hello "+name+"!");
                Picasso.get().load(image).into(Profileinhome);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Profileinhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 =new Intent(getApplicationContext(),mepage.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
            }
        });

    }
}