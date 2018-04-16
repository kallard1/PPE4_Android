package fr.area42.mygavolt.Interventions;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fr.area42.mygavolt.Models.Intervention;
import fr.area42.mygavolt.R;
import fr.area42.mygavolt.ReportActivity;

/**
 * Created by allardk on 06/02/2018.
 */

public class ShowActivity extends AppCompatActivity {

    private int id = 0;
    private String date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intervention);

        Toolbar toolbarIntervention = findViewById(R.id.toolbarIntervention);

        FloatingActionButton fab = findViewById(R.id.fab);

        final Bundle extras = getIntent().getExtras();

        TextView dateView = findViewById(R.id.interventionDate);
        TextView customerName = findViewById(R.id.interventionCustomer);
        TextView motiveIntervention = findViewById(R.id.interventionMotive);
        TextView dial = findViewById(R.id.dial);
        TextView mobile = findViewById(R.id.dialMobile);
        final TextView address = findViewById(R.id.address);

        if (extras != null) {
            id = extras.getInt("id");
            dateView.setText(extras.getString("date"));
            customerName.setText(extras.getString("customerName"));
            motiveIntervention.setText(extras.getString("motiveIntervention"));
            dial.setText(extras.getString("customerPhone"));
            mobile.setText(extras.getString("customerMobile"));
            address.setText(extras.getString("customerAddress"));
        }

        setTitle("Intervention #" + id);
        setSupportActionBar(toolbarIntervention);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ReportActivity.class);

                intent.putExtra("id", id);
                if (extras != null) {
                    intent.putExtra("report", extras.getString("report"));
                }

                startActivity(intent);
            }
        });

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q=" + address.getText()));

                startActivity(intent);
            }
        });
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
