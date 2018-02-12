package fr.area42.mygavolt.Interventions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import fr.area42.mygavolt.API.Helper.InterventionHelper;
import fr.area42.mygavolt.Adapter.InterventionAdapter;
import fr.area42.mygavolt.R;

/**
 * Created by allardk on 30/01/2018.
 */

public class ListActivity extends AppCompatActivity {

    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interventions_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        requestQueue = Volley.newRequestQueue(this);

        recyclerView = findViewById(R.id.recyclerViewContainer);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new InterventionAdapter(ListActivity.this, InterventionHelper.sendRequest(requestQueue));

        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }
}
