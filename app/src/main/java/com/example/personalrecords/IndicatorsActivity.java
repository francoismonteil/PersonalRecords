package com.example.personalrecords;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import modele.IndicateurBdd;
import modele.SaveSharedPreference;

public class IndicatorsActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView listView;
    private IndicateurBdd indicateurBdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicators);

        listView = findViewById(R.id.listView_indicator_list);

        indicateurBdd = new IndicateurBdd(this,"PersonalRecords.db", null, 1);
        Cursor cursor = indicateurBdd.getUserIndicatorCursor(SaveSharedPreference.getLoggedStatus(getApplicationContext()));

        IndicatorCursorAdapter listAdapter = new IndicatorCursorAdapter(this, cursor);
        listView.setAdapter(listAdapter);

        Button button_ajout = findViewById(R.id.button_indicateur_ajout);
        button_ajout.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu homeMenu) {
        getMenuInflater().inflate(R.menu.home_menu, homeMenu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_informations:
                Intent toInformation = new Intent(this, InformationsActivity.class);
                startActivity(toInformation);
                break;
            case R.id.action_indicateurs:
                Intent toIndicatorApplication = new Intent(this, IndicatorsActivity.class);
                startActivity(toIndicatorApplication);
                break;
            case R.id.action_accueil:
                Intent toAccueil = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(toAccueil);
            case R.id.action_deconnecter:
                SaveSharedPreference.disconnect(getApplicationContext());
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                break;
        }
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_indicateur_ajout:
                Intent toIndicatorAdd = new Intent(getApplicationContext(), IndicatorsAddActivity.class);
                this.finish();
                startActivity(toIndicatorAdd);
            break;
        }
    }
}
