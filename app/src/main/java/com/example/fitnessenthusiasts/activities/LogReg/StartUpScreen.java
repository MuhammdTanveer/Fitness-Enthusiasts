package com.example.fitnessenthusiasts.activities.LogReg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitnessenthusiasts.MainActivity;
import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Common.HomeScreen;
import com.example.fitnessenthusiasts.activities.Databases.Session;
import com.example.fitnessenthusiasts.activities.Databases.UserHelperClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StartUpScreen extends AppCompatActivity {

    //Variables
//    ImageView image;
//    TextView slogan;
    FirebaseAuth auth;
    FirebaseUser currentUser;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start_up_screen);

        //Hooks
//        image = findViewById(R.id.topImage);
//        slogan = findViewById(R.id.slogan_name);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));

    }

    public void login(View view){
        Intent intent = new Intent(getApplicationContext(), Login.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View,String>(findViewById(R.id.btn_login),"transition_login");
        //pairs[1] = new Pair<View,String>(slogan,"logo_text");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StartUpScreen.this,pairs);
        startActivity(intent,options.toBundle());
    }

    public void signup(View view){
        Intent intent = new Intent(getApplicationContext(), SignUp.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View,String>(findViewById(R.id.btn_signup),"transition_signup");
//        pairs[1] = new Pair<View,String>(slogan,"logo_text");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StartUpScreen.this,pairs);
        startActivity(intent,options.toBundle());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(currentUser!=null){
            database.getReference().child("Users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        UserHelperClass user = snapshot.getValue(UserHelperClass.class);
                        Session session = new Session(StartUpScreen.this);
                        session.saveSession(user);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            startActivity(new Intent(StartUpScreen.this, HomeScreen.class));
        }
    }

}