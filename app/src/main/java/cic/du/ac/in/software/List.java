package cic.du.ac.in.software;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class List extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    String key;
    public void edit(View view) {
//        database.getReference().child("Stroy").child(key).child("Contribution").child(mAuth.getCurrentUser().getUid()).Data

        Intent editit = new Intent(this,EditScreen.class);
        editit.putExtra("Storyid",key);
        startActivity(editit);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

         mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        String title = intent.getStringExtra("Title");

        Toolbar toolbar = findViewById(R.id.toolbarm);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        ListView textView  = findViewById(R.id.textcontent);
        database = FirebaseDatabase.getInstance();
        DatabaseReference mref = database.getReference("Story");
        mref.orderByChild("Title").equalTo(title).addValueEventListener(new ValueEventListener(){
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(List.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    key=datas.getKey();
                }
            }});
        Query select = mref.orderByChild("Title").equalTo(title).limitToLast(1);
        final FirebaseListAdapter<datafetch> listAdapter = new FirebaseListAdapter<datafetch>(this,datafetch.class,android.R.layout.simple_list_item_1,select) {
            @Override
            protected void populateView(View v, datafetch model, int position) {
                ((TextView)v.findViewById(android.R.id.text1)).setText(model.getContent());
            }
        };
        textView.setAdapter(listAdapter);


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
