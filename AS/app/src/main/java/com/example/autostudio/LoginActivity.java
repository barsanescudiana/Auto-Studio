package com.example.autostudio;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 2000;

    private final int RC_SIGN_IN = 0;
    private SignInButton signInButton;
    private GoogleSignInClient gsc;
    TextView welcomeLastSignedIn;

    ImageView userPic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this, gso);
        //pentru dev, sa te deconecteze
        //gsc.signOut();

        signInButton = findViewById(R.id.sign_in_btn);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        welcomeLastSignedIn = findViewById(R.id.welcomeLastSignedIn);
        userPic = findViewById(R.id.userPic);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = gsc.getSignInIntent();
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            Log.w("Google SignIn", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            updateUI(account);
            signInButton.setVisibility(View.INVISIBLE);
            Glide.with(getApplicationContext()).load(account.getPhotoUrl())
                    .centerCrop().circleCrop().into(userPic);
            userPic.setVisibility(View.VISIBLE);
            welcomeLastSignedIn.setText("Welcome back," + "\n" + account.getDisplayName());
            welcomeLastSignedIn.setVisibility(View.VISIBLE);
        }
    }

    private void updateUI(final GoogleSignInAccount account) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (account != null) {
                    User user = new User(account.getId(), account.getDisplayName(),
                            account.getEmail(), account.getPhotoUrl().toString());


                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("User", user);
                    startActivity(intent);
                }
            }
        }, SPLASH_TIME_OUT);
    }
}