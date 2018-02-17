package fr.area42.mygavolt.Interventions;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import fr.area42.mygavolt.R;

/**
 * Created by allardk on 06/02/2018.
 */

public class ShowActivity extends AppCompatActivity {

    private int id = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intervention);

        Toolbar toolbarIntervention = findViewById(R.id.toolbarIntervention);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            id = extras.getInt("id");
        }

        setTitle("Intervention #" + id);
        setSupportActionBar(toolbarIntervention);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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

    @Override
    protected void onResume() {
        super.onResume();

        InterventionAsyncTask asyncTask = new InterventionAsyncTask();
        asyncTask.execute("http://api.area42.fr/interventions/" + Integer.toString(id));
    }

    class InterventionAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Chargement de l'intervention #" + id + "...", Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(String... strings) {
            return null;
        }


    }
}
