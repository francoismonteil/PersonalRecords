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

import modele.EspaceBdd;
import modele.SaveSharedPreference;

public class SpacesActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSpaceAdd;
    private ListView listViewSpaces;
    private EspaceBdd espaceBdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spaces);

        btnSpaceAdd = findViewById(R.id.btn_addSpace);
        btnSpaceAdd.setOnClickListener(this);

        listViewSpaces = findViewById(R.id.listView_spaces);

        espaceBdd = new EspaceBdd(this,"PersonalRecords.db", null, 1);
        Cursor cursor = espaceBdd.getUserSpacesCursor(SaveSharedPreference.getLoggedStatus(getApplicationContext()));

        SpaceCursorAdapter listAdapter = new SpaceCursorAdapter(this, cursor);
        listViewSpaces.setAdapter(listAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        switch (v.getId()) {
            case R.id.btn_addSpace:
                Intent toSpaceAddApplication = new Intent(this, SpaceAddActivity.class);
                startActivity(toSpaceAddApplication);
                this.finish();
                break;
        }
    }
}
