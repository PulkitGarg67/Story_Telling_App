package cic.du.ac.in.software;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class selectStory extends AppCompatActivity {

    ListView listView;
    FirebaseDatabase database;
    DatabaseReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_story);

        listView = findViewById(R.id.listView);

        Intent intent = getIntent();
        String genre = intent.getStringExtra("Genre");

        Toolbar toolbar = findViewById(R.id.toolbarl);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(genre);

        database = FirebaseDatabase.getInstance();
        mref = database.getReference("Story");
        Query select = mref.orderByChild("Genre").equalTo(genre);
        final FirebaseListAdapter<datafetch> listAdapter = new FirebaseListAdapter<datafetch>(this,datafetch.class,android.R.layout.simple_list_item_1,select) {
            @Override
            protected void populateView(View v, datafetch model, int position) {
                ((TextView)v.findViewById(android.R.id.text1)).setText(model.getTitle());
            }
        };
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(selectStory.this,List.class);
                intent.putExtra("Title",listAdapter.getItem(i).getTitle());
                startActivity(intent);
            }
        });
    }
}
