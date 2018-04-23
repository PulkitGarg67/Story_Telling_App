package cic.du.ac.in.software;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUp extends AppCompatActivity {

    EditText email,password;
    String emailaddress,passwordtext;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    Toolbar toolbar;

    public void loginus(View view) {
        Intent homeIntent = new Intent(SignUp.this,Login.class);
        finish();
        startActivity(homeIntent);
    }

    public void signin(View view) {
        emailaddress = email.getText().toString().trim();
        passwordtext = password.getText().toString().trim();
        if(emailaddress.isEmpty()) {
            email.setError("Email is Required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailaddress).matches()) {
            email.setError("Please enter a valid Email");
            email.requestFocus();
            return;
        }
        if(passwordtext.isEmpty())  {
            password.setError("Password is Requires");
            password.requestFocus();
            return;
        }
        if(passwordtext.length()<6) {
            password.setError("Minimum length of password should be 6");
            password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(emailaddress,passwordtext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.INVISIBLE);
                if(task.isSuccessful()) {
                    Toast.makeText(SignUp.this, "User Registered", Toast.LENGTH_SHORT).show();
                    Intent homeIntent = new Intent(SignUp.this,HomePage.class);
                    homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homeIntent);
                    finish();
                }
                else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)   {
                        Toast.makeText(SignUp.this, "You are Already Registered", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(SignUp.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email = (EditText) findViewById(R.id.emails);
        password = (EditText) findViewById(R.id.passwords);

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progress);

        toolbar = findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");


    }
}