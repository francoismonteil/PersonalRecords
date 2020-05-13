package com.example.personalrecords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;

import modele.SaveSharedPreference;
import modele.Utilisateur;
import modele.UtilisateurBdd;

public class InformationsActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText_prenom;
    private EditText editText_nom;
    private EditText editText_motdepasse;
    private UtilisateurBdd utilisateurBdd = new UtilisateurBdd(this, "PersonalRecords.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informations);

        Utilisateur utilisateur = utilisateurBdd.getUser(SaveSharedPreference.getLoggedStatus(getApplicationContext()));

        editText_prenom = findViewById(R.id.editText_informations_prenom);
        editText_prenom.setText(utilisateur.getPrenom());
        editText_nom = findViewById(R.id.editText_informations_nom);
        editText_nom.setText(utilisateur.getNom());
        editText_motdepasse = findViewById(R.id.editText_informations_motdepasse);

        Button button_enregistrer = findViewById(R.id.button_informations_enregistrer);
        button_enregistrer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String motdepasse = editText_motdepasse.getText().toString();
        if(!motdepasse.equals("")){
            try {
                if(!utilisateurBdd.modifyUser(SaveSharedPreference.getLoggedStatus(getApplicationContext()), editText_prenom.getText().toString(), editText_nom.getText().toString(), editText_motdepasse.getText().toString()))
                    this.alerter("Impossible de modifier les informations");
                else
                    this.alerter("Informations enregistrées");
            } catch (NoSuchAlgorithmException e) {
                this.alerter("Impossible de modifier les informations");
                e.printStackTrace();
            }
        }else{
            if(!utilisateurBdd.modifyUser(SaveSharedPreference.getLoggedStatus(getApplicationContext()), editText_prenom.getText().toString(), editText_nom.getText().toString()))
                this.alerter("Impossible de modifier les informations");
            else
                this.alerter("Informations enregistrées");
        }
    }

    private void alerter(String s){
        Toast alertToast = Toast.makeText(this, s, Toast.LENGTH_LONG);
        alertToast.show();
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
}
