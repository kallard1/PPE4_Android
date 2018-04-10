package fr.area42.mygavolt;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import fr.area42.mygavolt.Interventions.ListActivity;
import fr.area42.mygavolt.Singletons.SecurePreferences;

/**
 * Created by allardk on 28/01/2018.
 */

public class SplashScreenActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String versionName;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;

        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        assert packageInfo != null;
        versionName = packageInfo.versionName;

        TextView textViewVersionInfo = findViewById(R.id.textview_version_info);
        textViewVersionInfo.setText(String.format("Version: %s", versionName));

        if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
            int SPLASH_TIME_OUT = 3000;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    SharedPreferences sharedPreferences = new SecurePreferences(getApplicationContext());

                    String userID = sharedPreferences.getString("userID", null);
                    Intent intent = null;
                    if (userID == null) {
                        intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    } else {
                        intent = new Intent(SplashScreenActivity.this, ListActivity.class);
                        Toast.makeText(getApplicationContext(), "Bonjour " + sharedPreferences.getString("firstname", "") + " " + sharedPreferences.getString("lastname", ""), Toast.LENGTH_SHORT).show();
                    }
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Pas de réseau")
                    .setMessage("Aucune connectivité détecté")
                    .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            System.exit(0);
                        }
                    })
                    .show();
        }
    }
}
