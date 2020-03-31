package com.example.personalrecords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import modele.Space;

public class SpacesActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSpaceAdd;
    private ListView listViewSpaces;

    private Space[] spaces;

    private String[] spaceList = new String[]{
            "Cigarette", "Sport", "Nourriture"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spaces);

        btnSpaceAdd = findViewById(R.id.btn_addSpace);
        btnSpaceAdd.setOnClickListener(this);

        listViewSpaces = findViewById(R.id.listView_spaces);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(SpacesActivity.this,
                android.R.layout.simple_list_item_1, spaceList);
        listViewSpaces.setAdapter(adapter);

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
            case R.id.action_indicateurs:
                Intent toIndicatorApplication = new Intent(this, IndicatorsActivity.class);
                startActivity(toIndicatorApplication);
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addSpace:
                Intent toSpaceAddApplication = new Intent(this, SpaceAddActivity.class);
                startActivity(toSpaceAddApplication);
                break;
        }
    }
}
