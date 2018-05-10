package cic.du.ac.in.software;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class Login extends AppCompatActivity {

    EditText email,password;
    String emailaddress,passwordtext;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    Toolbar toolbar;

    public  void login(View view)   {
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
        mAuth.signInWithEmailAndPassword(emailaddress,passwordtext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.INVISIBLE);
                if(task.isSuccessful()) {
                    if(!(mAuth.getCurrentUser().isEmailVerified())){
                        mAuth.getCurrentUser().sendEmailVerification();
                        View v = findViewById(R.id.loginparent);
                        Snackbar.make(v, "Please Verify your Email. Redirected to Login!", Snackbar.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent homeIntent = new Intent(Login.this,HomePage.class);
                    homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homeIntent);
                    finish();
                }   else {
                    if(task.getException() instanceof FirebaseAuthInvalidUserException) {
                        Toast.makeText(Login.this, "User Not Registered", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    public void signup(View view) {
        Intent homeIntent = new Intent(Login.this,SignUp.class);
        finish();
        startActivity(homeIntent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.emaill);
        password = (EditText) findViewById(R.id.passwordl);

        progressBar = findViewById(R.id.progressl);

        mAuth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.toolbarl);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Login");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!= null)    {
            finish();
            startActivity(new Intent(this,HomePage.class));
        }
    }
}
