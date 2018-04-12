package fr.area42.mygavolt;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import fr.area42.mygavolt.Interventions.ListActivity;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by allardk on 24/03/2018.
 */

public class ReportActivity extends AppCompatActivity {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_report);

        Toolbar toolbar = findViewById(R.id.toolbar);

        final EditText editText = findViewById(R.id.editText);
        final Bundle extras = getIntent().getExtras();

        setTitle("Rapport d''intervention");
        setSupportActionBar(toolbar);

        if (extras != null) {
            editText.setText(extras.getString("report"));
        }

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton sendReportBtn = findViewById(R.id.send);
        sendReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String reportEdit = editText.getText().toString();

                SendReport asyncTask = new SendReport();

                if (extras != null) {
                    asyncTask.execute("http://api.area42.fr/interventions/" + extras.getInt("id"), reportEdit);
                }
            }
        });
    }

    class SendReport extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Envoi en cours...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String jsonObj = "{\"report\":\"" + strings[1] + "\"}";
                return post(strings[0], jsonObj);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String message) {
            Toast.makeText(getApplicationContext(), "Rapport envoyé", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(getApplicationContext(), ListActivity.class);
            startActivity(intent);
            finish();
        }

        String post(String url, String json) throws IOException {
            RequestBody body;
            body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .put(body)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }
}
