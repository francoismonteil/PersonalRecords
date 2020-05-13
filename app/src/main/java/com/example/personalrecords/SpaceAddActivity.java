package com.example.personalrecords;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import modele.EspaceBdd;
import modele.IndicateurBdd;
import modele.SaveSharedPreference;

public class SpaceAddActivity extends AppCompatActivity implements View.OnClickListener {

    private Button ajout_indicateur;
    private Button ajout;
    private EditText editTexte_titre;
    private EditText editTexte_description;
    private IndicateurBdd indicateurBdd;
    private EspaceBdd espaceBdd;
    private ArrayList<Integer> selectedIdicators = new ArrayList<Integer>();
    private ListView listView_selected_spaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_add);

        espaceBdd = new EspaceBdd(this,"PersonalRecords.db", null, 1);

        ajout_indicateur = findViewById(R.id.button_espace_add_indicateur);
        ajout_indicateur.setOnClickListener(this);
        ajout = findViewById(R.id.button_espace_ajouter);
        ajout.setOnClickListener(this);
        editTexte_titre = findViewById(R.id.editText_espace_titre);
        editTexte_description = findViewById(R.id.editText_space_add_description);

        listView_selected_spaces = findViewById(R.id.listview_espace_indicateurs);
        listView_selected_spaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(selectedIdicators.size() == 1)
                    selectedIdicators = new ArrayList<Integer>();
                else
                    selectedIdicators.remove(position);
                updateList();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_espace_add_indicateur:
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.setTitle("Title...");

                indicateurBdd = new IndicateurBdd(this,"PersonalRecords.db", null, 1);
                Cursor cursor = indicateurBdd.getUserIndicatorCursor(SaveSharedPreference.getLoggedStatus(getApplicationContext()));

                IndicatorCursorAdapter listAdapter = new IndicatorCursorAdapter(this, cursor);

                final ListView listView = (ListView) dialog.findViewById(R.id.List);
                listView.setAdapter(listAdapter);

                listView.setClickable(true);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        view.setClickable(true);
                        selectedIdicators.add((int) id);
                        listView.getChildAt(position).setEnabled(false);
                        listView.getChildAt(position).setBackgroundColor(Color.GRAY);
                        updateList();
                    }
                });

                dialog.show();
            break;
            case R.id.button_espace_ajouter:
                String titre = editTexte_titre.getText().toString();
                if(!titre.equals("")){
                    String description = editTexte_description.getText().toString();
                    if(selectedIdicators.size() > 0){
                        int idEspace = espaceBdd.addSpace(titre, description, SaveSharedPreference.getLoggedStatus(getApplicationContext()));
                        if(idEspace != -1){
                            for(int i: selectedIdicators){
                                int idEspaceHasIndicateur = espaceBdd.addSpacesHasIndicators(idEspace, i);
                                if(idEspaceHasIndicateur == -1){
                                    alerter("Un prolème est survenue.");
                                    break;
                                }
                            }
                            alerter("Nouvel espace créé.");
                            Intent toSpaceApplication = new Intent(this, SpacesActivity.class);
                            startActivity(toSpaceApplication);
                            this.finish();
                        }else
                            alerter("Un prolème est survenue.");
                    }else
                        alerter("Choisissez au moins 1 indicateur.");
                }else
                    alerter("Il faut un label à votre espace.");
            break;
        }
    }

    private void updateList(){
        if(selectedIdicators.size() >= 1) {
            Cursor selectedIndicatorCursor = indicateurBdd.getSelectedIndicatorCursor(selectedIdicators);
            IndicatorCursorAdapter listAdapter = new IndicatorCursorAdapter(this, selectedIndicatorCursor);
            listView_selected_spaces.setAdapter(listAdapter);
        }else{
            listView_selected_spaces.setAdapter(null);
        }
    }

    private void alerter(String s){
        Toast alertToast = Toast.makeText(this, s, Toast.LENGTH_LONG);
        alertToast.show();
    }
}
