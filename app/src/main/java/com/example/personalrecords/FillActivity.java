package com.example.personalrecords;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import modele.Espace;
import modele.EspaceBdd;
import modele.Indicateur;

public class FillActivity extends AppCompatActivity implements View.OnClickListener {
    private  int idEspace;
    private  Date dateEntry;
    private EspaceBdd espaceBdd;
    private Espace espace;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
    private ArrayList<Object> valeurs = new ArrayList<Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill);

        idEspace = getIntent().getIntExtra("idEspace", -1);
        long time = getIntent().getLongExtra("dateEntry", 0);
        dateEntry = new Date(time);

        espaceBdd = new EspaceBdd(this,"PersonalRecords.db", null, 1);
        espace = espaceBdd.getEspaceById(idEspace);

        Button button_fill = findViewById(R.id.button_fill_register);
        button_fill.setOnClickListener(this);

        TextView textView_titre = findViewById(R.id.textView_fill_titre);
        TextView textView_description = findViewById(R.id.textView_fill_description);
        TextView textView_date = findViewById(R.id.textView_fill_date);

        textView_titre.setText(espace.getLabel());
        textView_description.setText(espace.getDescription());
        textView_date.setText(dateFormat.format(dateEntry));

        for (Indicateur indicateur:espace.getIndicateurs()) {
            LinearLayout ll = findViewById(R.id.linearLayout_fill);
            EditText editText;
            switch (indicateur.getType()){
                case "boolean":
                    CheckBox checkBox = new CheckBox(this);
                    checkBox.setText(indicateur.getLabel());
                    checkBox.setTextSize(18);
                    valeurs.add(checkBox);
                    ll.addView(checkBox);
                break;
                case "float":
                    editText = new EditText(this);
                    editText.setHint(indicateur.getLabel()+" - "+indicateur.getTypeDisplay());
                    editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
                    ll.addView(editText);
                    valeurs.add(editText);
                break;
                case "integer":
                    editText = new EditText(this);
                    editText.setHint(indicateur.getLabel()+" - "+indicateur.getTypeDisplay());
                    editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_CLASS_NUMBER);
                    ll.addView(editText);
                    valeurs.add(editText);
                break;
                case "picture":

                break;
                default:
                    editText = new EditText(this);
                    editText.setHint(indicateur.getLabel()+" - "+indicateur.getTypeDisplay());
                    ll.addView(editText);
                    valeurs.add(editText);
                break;

            }
        }
    }

    private void alerter(String s){
        Toast alertToast = Toast.makeText(this, s, Toast.LENGTH_LONG);
        alertToast.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_fill_register:
                ArrayList<Indicateur> indicateurs = espace.getIndicateurs();
                int idValeur = -1;
                for(int i=0; i<indicateurs.size();i++){
                    switch (indicateurs.get(i).getType()){
                        case "boolean":
                           CheckBox checkBoxValeurB = (CheckBox) valeurs.get(i);
                           boolean valeurB = checkBoxValeurB.isChecked();
                           idValeur = espaceBdd.addValeur(espace.getIdEspace_has_Indicateur(), valeurB, dateEntry);
                        break;
                        case "float":
                            EditText editTextValeurF = (EditText) valeurs.get(i);
                            Float valeurF = Float.parseFloat(editTextValeurF.getText().toString());
                            idValeur = espaceBdd.addValeur(espace.getIdEspace_has_Indicateur(), valeurF, dateEntry);
                        break;
                        case "integer":
                            EditText editTextValeurI = (EditText) valeurs.get(i);
                            int valeurI = Integer.parseInt(editTextValeurI.getText().toString());
                            idValeur = espaceBdd.addValeur(espace.getIdEspace_has_Indicateur(), valeurI, dateEntry);
                        break;
                        case "picture":
                        break;
                        default:
                            EditText editTextValeurS = (EditText) valeurs.get(i);
                            String valeurS = editTextValeurS.getText().toString();
                            idValeur = espaceBdd.addValeur(espace.getIdEspace_has_Indicateur(), valeurS, dateEntry);
                        break;
                    }
                }
                this.finish();
            break;
        }
    }
}
