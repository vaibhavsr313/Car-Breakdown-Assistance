package com.example.abreak;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class mepage extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    Button b1,edit;
    TextView e1,n1,a1,p1;
    ImageButton backinme,rateinme;
    ImageView pp;
    DatabaseReference databaseReference;
    SwitchCompat switchCompat;
    boolean DayMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ConstraintLayout backgroundinme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mepage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.backgroundinme), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        b1 = findViewById(R.id.logoutinme);
        backinme = findViewById(R.id.backinme);
        e1 = findViewById(R.id.emailinme);
        n1 = findViewById(R.id.Nameinme);
        p1 = findViewById(R.id.phonenoinme);
        a1 = findViewById(R.id.addressinme);
        pp = findViewById(R.id.Profileinme);
        rateinme = findViewById(R.id.rateinme);
        edit = findViewById(R.id.editinme);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        e1.setText(user.getEmail());
        switchCompat = findViewById(R.id.switchMode);
        backgroundinme = findViewById(R.id.backgroundinme);

        rateinme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 =new Intent(getApplicationContext(),feedback.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
            }
        });

        backinme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 =new Intent(getApplicationContext(),Homepage.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
            }
        });

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switchCompat.isChecked())
                {
                    backgroundinme.setBackgroundColor(Color.BLACK);
                }
                else {
                    backgroundinme.setBackgroundColor(Color.WHITE);
                }
            }
        });

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("dataName").getValue().toString();
                String address = snapshot.child("dataAddress").getValue().toString();
                String No = snapshot.child("dataNo").getValue().toString();
                String image = snapshot.child("dataImage").getValue().toString();

                n1.setText(name);
                a1.setText(address);
                p1.setText(No);
                Picasso.get().load(image).into(pp);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent =new Intent(getApplicationContext(),logsignin.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
            }
        });

    }
}