package com.example.personalrecords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;

import modele.SQliteConnexion;
import modele.SaveSharedPreference;
import modele.UtilisateurBdd;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editText_identifiant;
    EditText editText_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Check if UserResponse is Already Logged In
        if(SaveSharedPreference.getLoggedStatus(getApplicationContext()) != -1) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.editText_identifiant = findViewById(R.id.edt_login);
        this.editText_password = findViewById(R.id.edt_password);

        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

        Button btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);

        Button btnSupprBdd = findViewById(R.id.button_login_supprBdd);
        btnSupprBdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                UtilisateurBdd utilisateurBdd = new UtilisateurBdd(getApplicationContext(), "PersonalRecords.db", null, 1);
                try {
                    int idUtilisateur = utilisateurBdd.checkUser(this.editText_identifiant.getText().toString(), this.editText_password.getText().toString());
                    if(idUtilisateur != -1){
                        SaveSharedPreference.setLoggedIn(getApplicationContext(), true, idUtilisateur);
                        Intent toAccueil = new Intent(this, MainActivity.class);
                        startActivity(toAccueil);
                    }else
                        alerter("Identifiant ou mot de passe incorrect.");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_register:
                Intent toRegister = new Intent(this, RegisterActivity.class);
                startActivity(toRegister);
                break;
            case R.id.button_login_supprBdd:
                SQliteConnexion bdd = new SQliteConnexion(this, "PersonalRecords.db", null, 1);
            break;
        }
    }

    private void alerter(String s){
        Toast alertToast = Toast.makeText(this, s, Toast.LENGTH_LONG);
        alertToast.show();
    }

}
