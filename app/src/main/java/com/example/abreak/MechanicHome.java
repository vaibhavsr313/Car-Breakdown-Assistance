package com.example.abreak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MechanicHome extends AppCompatActivity {

    TextView usernameinmechanic;
    Button logoutinmech;
    CardView location,contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_home);
        usernameinmechanic = findViewById(R.id.usernameinmechanic);
        logoutinmech = findViewById(R.id.logoutinmech);
        location = findViewById(R.id.location);
        contacts = findViewById(R.id.contacts);

        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),contactus.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
            }
        });

        String name = getIntent().getStringExtra("name1");
        String phone = getIntent().getStringExtra("phone1");

        usernameinmechanic.setText("Hello "+name+"!");

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),maptoaddlocation.class);
                intent.putExtra("name2", name);
                intent.putExtra("phone2", phone);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
            }
        });

        logoutinmech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),logsignin.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
            }
        });


    }
}


