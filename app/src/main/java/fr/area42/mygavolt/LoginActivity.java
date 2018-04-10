package fr.area42.mygavolt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import fr.area42.mygavolt.Models.Employee;
import fr.area42.mygavolt.Singletons.Http;
import fr.area42.mygavolt.Singletons.SecurePreferences;

/**
 * Created by allardk on 29/01/2018.
 */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        final Button loginButton = findViewById(R.id.button_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView userId = findViewById(R.id.userId);
                Log.d("DEBUG", userId.getText().toString());

                if (!userId.getText().toString().isEmpty()) {
                    LoginAsyncTask loginAsyncTask = new LoginAsyncTask();
                    loginAsyncTask.execute("http://api.area42.fr/employees?userId=" + userId.getText().toString());


                } else {
                    Toast.makeText(getApplicationContext(), "Veuillez saisir un UserID", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class LoginAsyncTask extends AsyncTask<String, Void, List<Employee>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Recherche en cours...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected List<Employee> doInBackground(String... strings) {

            List<Employee> employee;

            try {
                String jsonString = Http.run(strings[0]);

                Gson gson = new Gson();

                Type collectionType = new TypeToken<List<Employee>>() {
                }.getType();
                employee = gson.fromJson(jsonString, collectionType);
            } catch (IOException e) {
                Log.e("ERROR:", e.getStackTrace().toString());

                return null;
            }

            return employee;
        }

        @Override
        protected void onPostExecute(List<Employee> employees) {
            Boolean commit = false;
            if (employees.size() == 1) {
                SharedPreferences sharedPreferences = new SecurePreferences(getApplicationContext());
                for (Employee employee : employees) {
                    if (employee.isActive) {
                        commit = sharedPreferences.edit()
                                .putInt("id", employee.id)
                                .putString("firstname", employee.firstname)
                                .putString("lastname", employee.lastname)
                                .putString("userID", employee.userId)
                                .commit();
                    }
                }
                if (commit) {
                    Toast.makeText(getApplicationContext(), "Bonjour " + sharedPreferences.getString("firstname", null) + " " + sharedPreferences.getString("lastname", null), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Une erreur est survenue ou le compte n'est pas actif.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "L'utilisateur n'existe pas", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
