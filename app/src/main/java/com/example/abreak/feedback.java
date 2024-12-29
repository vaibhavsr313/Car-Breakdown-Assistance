package com.example.abreak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class feedback extends AppCompatActivity {

    ImageView hero2star,hero3star,hero4star;
    Button feebackbutton;
    TextView reviewtext;
    RatingBar ratingbar;
    String answerValue;
    Animation charanim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_feedback);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        hero2star = findViewById(R.id.hero2star);
        hero3star = findViewById(R.id.hero3star);
        hero4star = findViewById(R.id.hero4star);
        feebackbutton = findViewById(R.id.feebackbutton);
        reviewtext = findViewById(R.id.reviewtext);
        ratingbar = findViewById(R.id.ratingbar);

        charanim = AnimationUtils.loadAnimation(this,R.anim.charanim);
        hero2star.startAnimation(charanim);
        hero3star.startAnimation(charanim);
        hero4star.startAnimation(charanim);

        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                answerValue = String.valueOf((int)(ratingBar.getRating()));
                if(answerValue.equals("1")){
                    hero2star.setVisibility(View.VISIBLE);
                    hero3star.setVisibility(View.INVISIBLE);
                    hero4star.setVisibility(View.INVISIBLE);
                    hero2star.startAnimation(charanim);
                    reviewtext.setText("Just So So");
                }
                else if(answerValue.equals("2")){
                    hero2star.setVisibility(View.VISIBLE);
                    hero3star.setVisibility(View.INVISIBLE);
                    hero4star.setVisibility(View.INVISIBLE);
                    hero2star.startAnimation(charanim);
                    reviewtext.setText("Just So So");
                }
                else if(answerValue.equals("3")){
                    hero3star.setVisibility(View.VISIBLE);
                    hero2star.setVisibility(View.INVISIBLE);
                    hero4star.setVisibility(View.INVISIBLE);
                    hero3star.startAnimation(charanim);
                    reviewtext.setText("Good Job");
                }
                else if(answerValue.equals("4")){
                    hero4star.setVisibility(View.VISIBLE);
                    hero3star.setVisibility(View.INVISIBLE);
                    hero2star.setVisibility(View.INVISIBLE);
                    hero4star.startAnimation(charanim);
                    reviewtext.setText("Good Job");
                }
                else if(answerValue.equals("5")){
                    hero4star.setVisibility(View.VISIBLE);
                    hero3star.setVisibility(View.INVISIBLE);
                    hero2star.setVisibility(View.INVISIBLE);
                    hero4star.startAnimation(charanim);
                    reviewtext.setText("Awesome");
                }
                else{
                    Toast.makeText(feedback.this, "No Point", Toast.LENGTH_SHORT).show();
                }
            }
        });

        feebackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                String userId=user.getUid();

                String text = reviewtext.getText().toString();

                FirebaseDatabase.getInstance().getReference("Feedback").child(userId)
                        .setValue(text).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(feedback.this, "Thank you for your response", Toast.LENGTH_SHORT).show();

                                    AlertDialog.Builder builder = new AlertDialog.Builder(feedback.this);
                                    View mView = getLayoutInflater().inflate(R.layout.thankyou,null);
                                    TextView gobackhome = (TextView) mView.findViewById(R.id.gobackhome);
                                    ImageView closeinfeedback = (ImageView) mView.findViewById(R.id.closeinfeedback);

                                    builder.setView(mView);
                                    AlertDialog dialog = builder.create();
                                    dialog.show();

                                    gobackhome.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(getApplicationContext(),mepage.class);
                                            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                    closeinfeedback.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(getApplicationContext(),mepage.class);
                                            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(feedback.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

    }
}