package fr.area42.mygavolt.Customers;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import fr.area42.mygavolt.Adapter.CategoryAdapter;
import fr.area42.mygavolt.Models.Category;
import fr.area42.mygavolt.R;
import fr.area42.mygavolt.Singletons.Http;

public class FilterActivity extends AppCompatActivity {

    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        setContentView(R.layout.activity_filter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setTitle("Filtre");
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerViewContainer);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    protected void onResume() {
        super.onResume();

        FilterActivity.CategoryAsyncTask asyncTask = new FilterActivity.CategoryAsyncTask();

        asyncTask.execute("http://api.area42.fr/categories");
    }

    class CategoryAsyncTask extends AsyncTask<String, Void, List<Category>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Chargement des categories...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected List<Category> doInBackground(String... strings) {
            List<Category> category;

            try {
                String jsonString = Http.run(strings[0]);

                Gson gson = new Gson();

                Type collectionType = new TypeToken<List<Category>>() {
                }.getType();
                category = gson.fromJson(jsonString, collectionType);

            } catch (IOException e) {
                Log.e("ERROR:", e.getStackTrace().toString());

                return null;
            }

            return category;
        }

        @Override
        protected void onPostExecute(List<Category> categories) {
            if (categories != null) {
                Toast.makeText(getApplicationContext(), "Chargement des cagtgories terminé", Toast.LENGTH_SHORT).show();
                recyclerViewAdapter = new CategoryAdapter(FilterActivity.this, categories);

                recyclerView.setAdapter(recyclerViewAdapter);
            }
        }
    }
}