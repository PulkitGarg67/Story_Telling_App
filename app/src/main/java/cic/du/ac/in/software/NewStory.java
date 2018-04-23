package cic.du.ac.in.software;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewStory extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference storyref,story,user;
    EditText editText,title;
    Spinner spinner;

    String storyId,genre;

    void submit(View view){
        story = storyref.child("Story").push();
        storyId = story.getKey();
        story.child("Content").setValue(editText.getText().toString() + "\n", new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Toast.makeText(NewStory.this, databaseReference.toString() , Toast.LENGTH_SHORT).show();
            }
        });
        story.child("Title").setValue(title.getText().toString());
        story.child("Semaphore").setValue("Can");
        story.child("Genre").setValue(genre);


        user = storyref.child("Users").child(mAuth.getCurrentUser().getUid());
        user.child(story.getKey()).child("Title").setValue(title.getText().toString());
        finish();
        startActivity(new Intent(this,HomePage.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_story);

        editText = findViewById(R.id.contents);
        title = findViewById(R.id.Title);
        spinner = findViewById(R.id.genre);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storyref = database.getReference();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                genre = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                genre = "Horror";
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()== null)    {
            finish();
            startActivity(new Intent(this,Login.class));
        }
    }
}
