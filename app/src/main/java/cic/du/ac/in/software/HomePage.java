package cic.du.ac.in.software;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.SnackbarContentLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {
    private FirebaseAuth mAuth;

    public void comedy(View view)  {
        Intent homeIntent = new Intent(HomePage.this,selectStory.class);
        homeIntent.putExtra("Genre","Comedy");
        startActivity(homeIntent);
    }
    public void horror(View view)  {
        Intent homeIntent = new Intent(HomePage.this,selectStory.class);
        homeIntent.putExtra("Genre","Horror");
        startActivity(homeIntent);
    }
    public void drama(View view)  {
        Intent homeIntent = new Intent(HomePage.this,selectStory.class);
        homeIntent.putExtra("Genre","Drama");
        startActivity(homeIntent);

    }
    public void romance(View view)  {
        Intent homeIntent = new Intent(HomePage.this,selectStory.class);
        homeIntent.putExtra("Genre","Romance");
        startActivity(homeIntent);

    }
    public void adventure(View view)  {
        Intent homeIntent = new Intent(HomePage.this,selectStory.class);
        homeIntent.putExtra("Genre","Adventure");
        startActivity(homeIntent);

    }
    public void story(View view)  {
        Log.i("output","executed");
        Intent newstory = new Intent(HomePage.this,NewStory.class);
        startActivity(newstory);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbarh);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Narratore");

        View view = findViewById(R.id.activityHome);

        if(mAuth.getCurrentUser().isEmailVerified())
        {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(HomePage.this, "Verification Email Sent. Please click the mentioned link to verify", Toast.LENGTH_SHORT).show();
                }
            });
            mAuth.signOut();
            Snackbar.make(view, "Please Verify your Email. Redirected to Login!", Snackbar.LENGTH_LONG).show();
            finish();
            startActivity(new Intent(this,Login.class));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()== null)    {
            finish();
            startActivity(new Intent(this,Login.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       int id = item.getItemId();
       if(id == R.id.signout){
           mAuth.signOut();
           finish();
           startActivity(new Intent(this,Login.class));
       }
       if(id == R.id.myStories){
           startActivity(new Intent(this,mystories.class));
       }
       return super.onOptionsItemSelected(item);
    }
}
