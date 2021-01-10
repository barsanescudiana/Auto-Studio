package com.example.autostudio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    User user;

    FirebaseDatabase usersDatabase;
    DatabaseReference usersReference;
    private GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle userBundle = getIntent().getExtras();
        String userEmailIntent = (String) userBundle.getSerializable("USER_EMAIL");

        usersDatabase = FirebaseDatabase.getInstance();
        usersReference = usersDatabase.getReference("autostudio").child("users");

        final ImageView picture = findViewById(R.id.image);
        final TextView name = findViewById(R.id.display_name);
        final TextView email = findViewById(R.id.display_email);

        Query query = usersReference.orderByChild("userEmail").equalTo(userEmailIntent);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Glide.with(getApplicationContext()).load(ds.child("userPhoto").getValue(String.class))
                                .centerCrop().circleCrop().into(picture);
                        name.setText(ds.child("userDisplayName").getValue(String.class));
                        email.setText(ds.child("userEmail").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        if(user != null) {
//            Glide.with(getApplicationContext()).load(user.getUserPhoto())
//                    .centerCrop().circleCrop().into(picture);
//            name.setText(user.getUserDisplayName());
//            email.setText(user.getUserEmail());
//        }

        Button settings = (Button) findViewById(R.id.btn_settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });

        Button dashboard = (Button) findViewById(R.id.btn_main);
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dashboardIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(dashboardIntent);
            }
        });

        Button logoutBtn = (Button) findViewById(R.id.logout_button);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.signOut();
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginIntent);
            }
        });

//    private void signOut() {
//        gsc.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(loginIntent);
//            }
//        });
//    }
    }
}