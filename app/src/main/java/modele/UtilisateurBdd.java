package modele;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static modele.hashCalculate.getSHA;
import static modele.hashCalculate.toHexString;


public class UtilisateurBdd extends SQliteConnexion {
    public UtilisateurBdd(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public Utilisateur getUser(int idUtilisateur){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                "identifiant",
                "prenom",
                "nom"
        };

       String selection = "idUtilisateurs=?";
       String[] selectionArgs = { String.valueOf(idUtilisateur) };

        Cursor cursor = db.query(
                "Utilisateurs",   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        cursor.moveToNext();
        String identifiant = cursor.getString(cursor.getColumnIndexOrThrow("identifiant"));
        String prenom = cursor.getString(cursor.getColumnIndexOrThrow("prenom"));
        String nom = cursor.getString(cursor.getColumnIndexOrThrow("nom"));

        Utilisateur user = new Utilisateur(idUtilisateur, identifiant, prenom, nom);

        cursor.close();

        return user;
    }

    public ArrayList<Utilisateur> allUsers(){
        SQLiteDatabase db = this.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                "idUtilisateurs",
                "identifiant",
                "prenom",
                "nom"
        };

        // Filter results WHERE "title" = 'My Title'
        //    String selection = "identifiant=?";
        //    String[] selectionArgs = { "francois" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = "identifiant ASC";

        Cursor cursor = db.query(
                "Utilisateurs",   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        ArrayList<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("idUtilisateurs"));
            String identifiant = cursor.getString(cursor.getColumnIndexOrThrow("identifiant"));
            String prenom = cursor.getString(cursor.getColumnIndexOrThrow("prenom"));
            String nom = cursor.getString(cursor.getColumnIndexOrThrow("nom"));

            utilisateurs.add(new Utilisateur(id, identifiant, prenom, nom));
        }
        cursor.close();

        return utilisateurs;
    }

    public int addUser(String identifiant, String prenom, String nom, String motdepasse) throws NoSuchAlgorithmException {
        int idUtilisateur = -1;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("identifiant", identifiant);
        values.put("prenom", prenom);
        values.put("nom", nom);
        motdepasse = toHexString(getSHA(motdepasse));
        values.put("motdepasse", motdepasse);
        idUtilisateur = (int) db.insert("Utilisateurs", null, values);

        return idUtilisateur;
    }

    public boolean modifyUser(int idUtilisateur, String prenom, String nom, String motdepasse) throws NoSuchAlgorithmException {
        boolean retour = false;
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("prenom", prenom);
            values.put("nom", nom);
            motdepasse = toHexString(getSHA(motdepasse));
            values.put("motdepasse", motdepasse);

        String[] args = { String.valueOf(idUtilisateur) };
        if(db.update("Utilisateurs", values, "idUtilisateurs", args) > 0)
            retour = true;

        return retour;
    }

    public boolean modifyUser(int idUtilisateur, String prenom, String nom){
        boolean retour = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("prenom", prenom);
        values.put("nom", nom);

        String[] args = { String.valueOf(idUtilisateur) };
        if(db.update("Utilisateurs", values, "idUtilisateurs="+idUtilisateur, null) > 0)
            retour = true;

        return retour;
    }

    public int checkUser(String identifiant, String motdepasse) throws NoSuchAlgorithmException {
        int retour = -1;

        SQLiteDatabase db = this.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                "count(*) as nbr",
                "idUtilisateurs"
        };

        // Filter results WHERE "title" = 'My Title'
            String selection = "identifiant=? AND motdepasse=?";
            String[] selectionArgs = { identifiant,  toHexString(getSHA(motdepasse))};

        Cursor cursor = db.query(
                "Utilisateurs",   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        cursor.moveToNext();
        if(cursor.getInt(cursor.getColumnIndexOrThrow("nbr")) == 1)
            retour = cursor.getInt(cursor.getColumnIndexOrThrow("idUtilisateurs"));

        cursor.close();
        return retour;
    }
}
