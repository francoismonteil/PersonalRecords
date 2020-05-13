package com.example.personalrecords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import modele.IndicateurBdd;
import modele.SaveSharedPreference;

public class IndicatorsAddActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner type;
    private EditText label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicators_add);

        type = findViewById(R.id.spinner_indicateur_add_liste);
        label = findViewById(R.id.editText_indicator_add_nom);

        Button button_add = findViewById(R.id.button_indicator_add_ajout);
        button_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        IndicateurBdd indicateurBdd = new IndicateurBdd(this,"PersonalRecords.db", null, 1);
        String typeString;
        switch (type.getSelectedItemPosition()){
            case 0:
                typeString = "boolean";
            break;
            case 2:
                typeString = "float";
            break;
            case 3:
                typeString = "integer";
            break;
            case 4:
                typeString = "picture";
            break;
            case 5:
                typeString = "checkbox";
            break;
            default:
                typeString = "text";
            break;
        }
        int resultat = indicateurBdd.addIndicator(typeString, label.getText().toString(), SaveSharedPreference.getLoggedStatus(getApplicationContext()));
        if(resultat != -1) {
            alerter("Indicateur ajouté");
            Intent toIndicator = new Intent(this, IndicatorsActivity.class);
            startActivity(toIndicator);
            this.finish();
        }
        else
            alerter("Erreur lors de l'ajout de l'indicateur");
    }

    private void alerter(String s){
        Toast alertToast = Toast.makeText(this, s, Toast.LENGTH_LONG);
        alertToast.show();
    }
}
