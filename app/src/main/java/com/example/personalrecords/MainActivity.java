package com.example.personalrecords;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import modele.EspaceBdd;
import modele.SaveSharedPreference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText_date;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
    private ListView listView_espaces;
    private EspaceBdd espaceBdd;
    private Date dateVue;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int idUtilisateur = SaveSharedPreference.getLoggedStatus(getApplicationContext());
        if(idUtilisateur == -1){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        Button btn_calandar = findViewById(R.id.button_accueil_calandar);
        btn_calandar.setOnClickListener(this);

        dateVue = new Date();

        editText_date = findViewById(R.id.editText_date_ajd);
        editText_date.setText(dateFormat.format(dateVue));

        espaceBdd = new EspaceBdd(this,"PersonalRecords.db", null, 1);
        listView_espaces = findViewById(R.id.listView_main_spaces);

        updateSpacesListView(dateVue);

        listView_espaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toFill((int) id, dateVue);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_accueil_calandar:
                setDate();
                break;
        }
    }

    private void setDate(){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        dateVue.setYear(mYear);
        dateVue.setMonth(mMonth+1);
        dateVue.setDate(mDay);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        try {
                            editText_date.setText(dateFormat.format(Objects.requireNonNull(dateFormat.parse(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year))));
                            updateSpacesListView(dateVue);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void toFill(int idEspace, Date dateEntree){
        Intent toFill = new Intent(this, FillActivity.class);
        toFill.putExtra("idEspace", idEspace);
        toFill.putExtra("dateEntry", dateEntree.getTime());
        startActivity(toFill);
    }

    private void updateSpacesListView(Date date){
        Cursor cursor = espaceBdd.getUserSpacesDateCursor(SaveSharedPreference.getLoggedStatus(getApplicationContext()), date);
        MainCursorAdapter spaceCursorAdapter = new MainCursorAdapter(this, cursor);

        listView_espaces.setAdapter(spaceCursorAdapter);
        listView_espaces.setClickable(true);
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
               this.recreate();
            break;
            case R.id.action_espaces:
                Intent toSpaceApplication = new Intent(this, SpacesActivity.class);
                startActivity(toSpaceApplication);
            break;
            case R.id.action_deconnecter:
                SaveSharedPreference.disconnect(getApplicationContext());
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void alerter(String s){
        Toast alertToast = Toast.makeText(this, s, Toast.LENGTH_LONG);
        alertToast.show();
    }
}

