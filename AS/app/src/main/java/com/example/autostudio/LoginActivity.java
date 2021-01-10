package com.example.autostudio;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.autostudio.classes.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 2000;

    private final int RC_SIGN_IN = 0;
    private SignInButton signInButton;
    private static GoogleSignInClient gsc;
    TextView welcomeLastSignedIn;

    FirebaseDatabase usersDatabase;
    DatabaseReference usersReference;

    ImageView userPic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this, gso);

        usersDatabase = FirebaseDatabase.getInstance();
        usersReference = usersDatabase.getReference("autostudio").child("users");

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
            final GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);

            Query query = usersReference.orderByChild("userEmail").equalTo(account.getEmail());
            query.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        usersReference.child(usersReference.push().getKey()).setValue(new User(account.getId(), account.getDisplayName(),
                                account.getEmail(), account.getPhotoUrl().toString()));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } catch (ApiException e) {
            Log.w("Google SignIn", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        final GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account != null) {
            updateUI(account);

            signInButton.setVisibility(View.INVISIBLE);
            Glide.with(getApplicationContext()).load(account.getPhotoUrl())
                    .centerCrop().circleCrop().into(userPic);
            userPic.setVisibility(View.VISIBLE);
            welcomeLastSignedIn.setText(getString(R.string.welcome) + "\n" + account.getDisplayName());
            welcomeLastSignedIn.setVisibility(View.VISIBLE);
        }
    }

    private void updateUI(final GoogleSignInAccount account) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (account != null) {
                    final User user = new User(account.getId(), account.getDisplayName(),
                            account.getEmail(), account.getPhotoUrl().toString());

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("User", user);
                    startActivity(intent);
                }
            }
        }, SPLASH_TIME_OUT);
    }

    public static void signOut() {
        gsc.signOut();
    }
}