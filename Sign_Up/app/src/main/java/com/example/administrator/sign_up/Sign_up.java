package com.example.administrator.sign_up;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 3/21/2018.
 */

public class Sign_up extends Activity {
    private static final String TAG = "abc";
    private String email;
    private String password;
    private String confirmpass;
    private String username;
    private EditText email_txt;
    private EditText password_txt;
    private EditText confirmpass_txt;
    private EditText username_txt;
    private Button signup_btn;
    private FirebaseAuth auth;
    private boolean valid_flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_sign_up);
        email_txt=findViewById(R.id.email_txt);
        password_txt = findViewById(R.id.password_txt);
        confirmpass_txt = findViewById(R.id.confirmpass_txt);
        username_txt=findViewById(R.id.username_txt);
        signup_btn=findViewById(R.id.sign_up_btn);
        email=email_txt.getText().toString().trim();
        password=password_txt.getText().toString().trim();
        confirmpass=confirmpass_txt.getText().toString().trim();
        username=username_txt.getText().toString().trim();
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign_up();
            }
        });
    }
    private void sign_up() {
        email_txt=findViewById(R.id.email_txt);
        password_txt = findViewById(R.id.password_txt);
        confirmpass_txt = findViewById(R.id.confirmpass_txt);
        username_txt=findViewById(R.id.username_txt);
        signup_btn=findViewById(R.id.sign_up_btn);
        email=email_txt.getText().toString().trim();
        password=password_txt.getText().toString().trim();
        confirmpass=confirmpass_txt.getText().toString().trim();
        username=username_txt.getText().toString().trim();
        auth = FirebaseAuth.getInstance();
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }
        Log.d("1abc","qua valid"+email+password);

        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("1abc","success");
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();;
                            DatabaseReference usersRef = ref.child("users");
                            usersRef.child(user.getUid()).setValue(new Users(username, user.getUid(),"default"));
                            //        updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("1abc", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Sign up failed."+task.getException(),
                                    Toast.LENGTH_SHORT).show();
                       //     updateUI(null);
                        }

                        // [START_EXCLUDE]
               //         hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }

    private boolean validateForm() {
        valid_flag = true;
        email_txt=findViewById(R.id.email_txt);
        password_txt = findViewById(R.id.password_txt);
        confirmpass_txt = findViewById(R.id.confirmpass_txt);
        username_txt=findViewById(R.id.username_txt);
        email=email_txt.getText().toString().trim();
        password=password_txt.getText().toString().trim();
        confirmpass=confirmpass_txt.getText().toString().trim();
        username=username_txt.getText().toString().trim();
        Log.d("abc", "pass: "+password+" Confirm: "+confirmpass+" email: "+email+" username: "+username+username.length());
        String email = email_txt.getText().toString();
        if (TextUtils.isEmpty(email)) {
            email_txt.setError("Required.");
            valid_flag = false;
        } else {
            email_txt.setError(null);
        }

        if (TextUtils.isEmpty(password)||password.length()<6) {
            password_txt.setError("Too short");
            valid_flag = false;
        }
        else {
            password_txt.setError(null);
        }
        if (confirmpass.length()<6 || !confirmpass.equals(password)) {
            confirmpass_txt.setError("Not right");
            Log.d("1abc","toi day"+confirmpass+" "+password+"1");
            valid_flag = false;
        }
        else {
            confirmpass_txt.setError(null);
        }
        Log.d("1abc","1");
        Pattern p = Pattern.compile("[^a-z0-9_]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(username);
        boolean b = m.find();

        if (username.length()<8 || b ) {
            username_txt.setError("Username is too short or contains special character");
            valid_flag = false;
        }
        else {
            username_txt.setError(null);
        }
        Log.d("1abc","2");

        Log.d("1abc","3"+valid_flag);
        //Check if username is available
        /*
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("users").child("username").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    username_txt.setError("Username is not available");
                    valid_flag = false;
                } else {
                    username_txt.setError(null);
                    valid_flag=true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }); */
        return valid_flag;
    }


}
