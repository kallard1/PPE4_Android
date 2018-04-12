package fr.area42.mygavolt.Interventions;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import fr.area42.mygavolt.Adapter.InterventionAdapter;
import fr.area42.mygavolt.LoginActivity;
import fr.area42.mygavolt.Models.Intervention;
import fr.area42.mygavolt.R;
import fr.area42.mygavolt.Singletons.Http;
import fr.area42.mygavolt.Singletons.SecurePreferences;

/**
 * Created by allardk on 30/01/2018.
 */

public class ListActivity extends AppCompatActivity {

    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interventions_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setTitle("Mes interventions");
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerViewContainer);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();

        InterventionAsyncTask asyncTask = new InterventionAsyncTask();
        asyncTask.execute("http://api.area42.fr/interventions");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        new SecurePreferences(getApplicationContext()).edit().clear().commit();

        Intent intent = new Intent(ListActivity.this, LoginActivity.class);
        Toast.makeText(getApplicationContext(), "Déconnexion effectuée", Toast.LENGTH_SHORT).show();

        startActivity(intent);
        finish();

        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }

    class InterventionAsyncTask extends AsyncTask<String, Void, List<Intervention>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Chargement des interventions...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected List<Intervention> doInBackground(String... strings) {
            List<Intervention> interventions;

            try {
                String jsonString = Http.run(strings[0]);

                Gson gson = new Gson();

                Type collectionType = new TypeToken<List<Intervention>>() {
                }.getType();
                interventions = gson.fromJson(jsonString, collectionType);

            } catch (IOException e) {
                Log.e("ERROR:", e.getStackTrace().toString());

                return null;
            }

            return interventions;
        }

        @Override
        protected void onPostExecute(List<Intervention> interventions) {
            if (interventions != null) {
                Toast.makeText(getApplicationContext(), "Chargement des interventions terminé", Toast.LENGTH_SHORT).show();
                recyclerViewAdapter = new InterventionAdapter(ListActivity.this, interventions);

                recyclerView.setAdapter(recyclerViewAdapter);
            }
        }
    }
}
