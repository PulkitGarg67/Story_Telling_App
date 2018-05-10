package cic.du.ac.in.software;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditScreen extends AppCompatActivity {
    String existing, fresh,title;
    TextView textView;
    EditText editText;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mref;
    FirebaseAuth mauth;
    String key;

    public void Updatenew(View view)    {

        fresh = editText.getText().toString().trim();
        if(fresh.isEmpty()) {
            editText.setError("Text is Empty");
            editText.requestFocus();
            return;
        }
        mref.child("Content").setValue(existing+fresh+"\n");
        mref.child("Contribution").child(mauth.getCurrentUser().getUid()).setValue("Done");
        Toast.makeText(this, "Story Updated", Toast.LENGTH_SHORT).show();

        Intent editit = new Intent(this,HomePage.class);
        finish();
        startActivity(editit);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_screen);

        Intent intent = getIntent();
        key = intent.getStringExtra("Storyid");

        firebaseDatabase = FirebaseDatabase.getInstance();
        mauth = FirebaseAuth.getInstance();
        mref = firebaseDatabase.getReference().child("Story").child(key);

         textView = findViewById(R.id.existing);
         editText = findViewById(R.id.fresh);

        Toolbar toolbar = findViewById(R.id.toolbaredit);
        setSupportActionBar(toolbar);

        mref.child("Content").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                existing = dataSnapshot.getValue(String.class);
                textView.setText(existing);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mref.child("Title").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                title = dataSnapshot.getValue(String.class);
                getSupportActionBar().setTitle(title);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
