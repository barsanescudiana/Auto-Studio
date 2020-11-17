package com.example.autostudio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class AddCarActivity1 extends AppCompatActivity {

    ImageView userPic;
    Button addCarNav;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car1);

        Intent intent = getIntent();
        final User user = (User) intent.getSerializableExtra("User");
        userPic = (ImageView) findViewById(R.id.userPic);

        if (user != null) {
            Glide.with(getApplicationContext()).load(user.getUserPhoto())
                    .centerCrop().circleCrop().into(userPic);
            Log.d("User", user.toString());
        }

        addCarNav = (Button) findViewById(R.id.nav_add);
        addCarNav.setVisibility(View.GONE);
        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setTitle(R.string.new_car);

        //vezi cu radio button urile

    }
}