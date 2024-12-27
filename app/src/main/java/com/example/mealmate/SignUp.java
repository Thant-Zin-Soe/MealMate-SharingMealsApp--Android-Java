
package com.example.mealmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    EditText mail;
    EditText password;
    Button enter;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mail = findViewById(R.id.editTextReset);
        password = findViewById(R.id.editTextSignupPassword);
        enter = findViewById(R.id.buttonReset);

        Toolbar toolbar = findViewById(R.id.toolbarSignup);

        // Set up the toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        }

        // Handle back arrow click
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous activity
                Intent intent = new Intent(SignUp.this, AppStartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish(); // Finish this activity
            }
        });

//        enter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String userEmail = mail.getText().toString();
//                String userPassword = password.getText().toString();
//                signUpFirebase(userEmail, userPassword);
//            }
//        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = mail.getText().toString().trim();
                String userPassword = password.getText().toString().trim();

                // Validation for empty fields
                if (userEmail.isEmpty()) {
                    Toast.makeText(SignUp.this, "Please enter an email address.", Toast.LENGTH_SHORT).show();
                    return; // Exit the method
                }

                if (userPassword.isEmpty()) {
                    Toast.makeText(SignUp.this, "Please enter a password.", Toast.LENGTH_SHORT).show();
                    return; // Exit the method
                }

                // Validation for minimum password length
                if (userPassword.length() < 6) {
                    Toast.makeText(SignUp.this, "Password must be at least 6 characters.", Toast.LENGTH_SHORT).show();
                    return; // Exit the method
                }

                // Call the signup method after validation
                signUpFirebase(userEmail, userPassword);
            }
        });

    }


    @Override
    public void onBackPressed() {
        // Navigate back to StartAppActivity when the back button is pressed
        super.onBackPressed();
        Intent intent = new Intent(SignUp.this, AppStartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish(); // Finish this activity to remove it from the back stack
    }



    public void signUpFirebase(String userEmail, String userPassword) {
        auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Toast.makeText(SignUp.this, "Your account has been created.", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SignUp.this, AppStartActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            // Sign in failed
                            Toast.makeText(SignUp.this, "There is a problem creating an account.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
