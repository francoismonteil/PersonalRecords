package modele;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EspaceBdd extends SQliteConnexion {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);

    public EspaceBdd(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public Cursor getUserSpacesCursor(int idUtilisateur){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT idEspaces _id,* FROM Espaces WHERE Utilisateurs_idUtilisateurs = "+idUtilisateur, null);
    }

    public Cursor getUserSpacesDateCursor(int idUtilisateur, Date date){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DISTINCT e.idEspaces _id, e.*, count(v.idValeurs) as nbrSaisie ";
        query+="FROM Espaces e ";
        query+="JOIN Espaces_has_Indicateurs ei ON e.idEspaces = ei.Espaces_idEspaces ";
        query+="LEFT JOIN Valeurs v ON (ei.idEspaces_has_Indicateurs = v.idEspaces_has_Indicateurs AND v.date = '"+dateFormat.format(date)+"') ";
        query+="WHERE e.Utilisateurs_idUtilisateurs = "+idUtilisateur+" ";
        query+="GROUP BY e.idEspaces";
        return db.rawQuery(query, null);
    }

    public int addSpace(String label, String description, int idUtilisateur){
        int idEspace = -1;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("description", description);
        values.put("label", label);
        values.put("Utilisateurs_idUtilisateurs", idUtilisateur);
        idEspace = (int) db.insert("Espaces", null, values);

        return idEspace;
    }

    public int addSpacesHasIndicators(int idEspace, int idIndicateur){
        int idEspaceHasIndicateur = -1;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Espaces_idEspaces", idEspace);
        values.put("Indicateurs_idIndicateurs", idIndicateur);
        idEspaceHasIndicateur = (int) db.insert("Espaces_has_Indicateurs", null, values);

        return idEspaceHasIndicateur;
    }

    public Espace getEspaceById(int idEspace){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT e.*, ei.idEspaces_has_Indicateurs, i.idIndicateurs, i.label labelIndicateur, i.type\n" +
                "FROM Espaces e\n" +
                "JOIN Espaces_has_Indicateurs ei ON ei.Espaces_idEspaces = e.idEspaces\n" +
                "JOIN Indicateurs i ON i.idIndicateurs = ei.Indicateurs_idIndicateurs\n" +
                "WHERE idEspaces = "+idEspace;

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToNext();
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("idEspaces"));
        String label = cursor.getString(cursor.getColumnIndexOrThrow("label"));
        String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
        int idEspace_has_Indicateur = cursor.getInt(cursor.getColumnIndexOrThrow("idEspaces_has_Indicateurs"));
        Espace espace = new Espace(id, description, label, idEspace_has_Indicateur);

        do{
            int idIndicateur = cursor.getInt(cursor.getColumnIndexOrThrow("idIndicateurs"));
            String labelIndicateur = cursor.getString(cursor.getColumnIndexOrThrow("labelIndicateur"));
            String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
            Indicateur indicateur = new Indicateur(idIndicateur, type, labelIndicateur);

            espace.addIndicateur(indicateur);
        }while (cursor.moveToNext());
        cursor.close();

        return espace;
    }

    public int addValeur(int idEspace_has_indicateur, boolean value, Date date) {
        int idValeur = -1;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idEspaces_has_indicateurs", idEspace_has_indicateur);
        values.put("valueBoolean", value);
        values.put("date", dateFormat.format(date));
        idValeur = (int) db.insert("Valeurs", null, values);

        return idValeur;
    }

    public int addValeur(int idEspace_has_indicateur, Float value, Date date) {
        int idValeur = -1;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idEspaces_has_indicateurs", idEspace_has_indicateur);
        values.put("valueFloat", value);
        values.put("date", dateFormat.format(date));
        idValeur = (int) db.insert("Valeurs", null, values);

        return idValeur;
    }

    public int addValeur(int idEspace_has_indicateur, int value, Date date) {
        int idValeur = -1;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idEspaces_has_indicateurs", idEspace_has_indicateur);
        values.put("valueInt", value);
        values.put("date", dateFormat.format(date));
        idValeur = (int) db.insert("Valeurs", null, values);

        return idValeur;
    }

    public int addValeur(int idEspace_has_indicateur, String value, Date date) {
        int idValeur = -1;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idEspaces_has_indicateurs", idEspace_has_indicateur);
        values.put("valueString", value);
        values.put("date", dateFormat.format(date));
        idValeur = (int) db.insert("Valeurs", null, values);

        return idValeur;
    }
}
