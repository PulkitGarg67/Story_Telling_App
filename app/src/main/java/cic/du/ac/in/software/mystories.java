package cic.du.ac.in.software;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class mystories extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mystories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarmy);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Stories");
        ListView started = findViewById(R.id.startedStories);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth mauth = FirebaseAuth.getInstance();

        String uid = mauth.getCurrentUser().getUid().toString();
        DatabaseReference mref = database.getReference("Users").child(uid);
        mref.keepSynced(true);
        final FirebaseListAdapter<getMyStory> adapter = new FirebaseListAdapter<getMyStory>(this,getMyStory.class,android.R.layout.simple_list_item_1,mref) {
            @Override
            protected void populateView(View v, getMyStory model, int position) {
                ((TextView)v.findViewById(android.R.id.text1)).setText(model.getTitle());
            }
        };
        started.setAdapter(adapter);
        started.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mystories.this,List.class);
                intent.putExtra("Title",adapter.getItem(i).getTitle());
                startActivity(intent);
            }
        });


    }
}
