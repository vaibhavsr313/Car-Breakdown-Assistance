package com.example.abreak;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class queriespageinuser extends AppCompatActivity {

    ImageButton backincontactusinqueries;
    ImageView callusincontinqueries,mailusincontinqueries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_queriespageinuser);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        backincontactusinqueries = findViewById(R.id.backincontactusinqueries);
        callusincontinqueries = findViewById(R.id.callusincontinqueries);
        mailusincontinqueries = findViewById(R.id.mailusincontinqueries);

        callusincontinqueries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:9969658121"));
                startActivity(intent);
            }
        });

        String to_email = "vaibhavsr3130@gmail.com";
        String subject = "";
        String content = "";

        mailusincontinqueries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{to_email});
                intent.putExtra(Intent.EXTRA_SUBJECT,subject );
                intent.putExtra(Intent.EXTRA_TEXT, content);
                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent,"Choose email client :"));
            }
        });

        backincontactusinqueries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Homepage.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
            }
        });

    }
}