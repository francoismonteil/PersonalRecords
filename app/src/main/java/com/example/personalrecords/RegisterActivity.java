package com.example.personalrecords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;

import modele.SaveSharedPreference;
import modele.UtilisateurBdd;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText prenom;
    EditText nom;
    EditText identifiant;
    EditText motdepasse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.prenom = findViewById(R.id.editText_register_prenom);
        this.nom = findViewById(R.id.editText_register_nom);
        this.identifiant = findViewById(R.id.editText_register_identifiant);
        this.motdepasse = findViewById(R.id.editText_register_motdepasse);

        Button button_enregistrer = findViewById(R.id.button_register_enregistrer);
        button_enregistrer.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        UtilisateurBdd utilisateurBdd = new UtilisateurBdd(this, "PersonalRecords.db", null, 1);
        try {
            int idUtilisateur = utilisateurBdd.addUser(this.identifiant.getText().toString(), this.prenom.getText().toString(), this.nom.getText().toString(), this.motdepasse.getText().toString());
            SaveSharedPreference.setLoggedIn(getApplicationContext(), true, idUtilisateur);
            Intent toAccueil = new Intent(this, MainActivity.class);
            startActivity(toAccueil);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            alerter("Impossible d'enregistrer ces informations :" + e);
        }
    }

    private void alerter(String s){
        Toast alertToast = Toast.makeText(this, s, Toast.LENGTH_LONG);
        alertToast.show();
    }
}
