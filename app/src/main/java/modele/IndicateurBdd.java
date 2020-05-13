package modele;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


public class IndicateurBdd extends SQliteConnexion {
    public IndicateurBdd(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public int addIndicator(String type, String label, int idUtilisateur){
        int idIndicateur = -1;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("type", type);
        values.put("label", label);
        values.put("Utilisateurs_idUtilisateurs", idUtilisateur);
        idIndicateur = (int) db.insert("Indicateurs", null, values);

        return idIndicateur;
    }

    public ArrayList<Indicateur> getUserIndicator(int idUtilisateur){
        SQLiteDatabase db = this.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                "idIndicateurs",
                "type",
                "label"
        };

        // Filter results WHERE "title" = 'My Title'
            String selection = "Utilisateurs_idUtilisateurs=?";
            String[] selectionArgs = { String.valueOf(idUtilisateur) };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = "label ASC";

        Cursor cursor = db.query(
                "Indicateurs",   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        ArrayList<Indicateur> indicateurs = new ArrayList<Indicateur>();
        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("idIndicateurs"));
            String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
            String label = cursor.getString(cursor.getColumnIndexOrThrow("label"));
            Indicateur indicateur = new Indicateur(id, type, label);

            indicateurs.add(indicateur);
        }
        cursor.close();

        return indicateurs;
    }

    public Cursor getUserIndicatorCursor(int idUtilisateur){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT idIndicateurs _id,* FROM Indicateurs WHERE Utilisateurs_idUtilisateurs = "+idUtilisateur, null);
    }

    public Cursor getSelectedIndicatorCursor(ArrayList<Integer> indicateurs){
        SQLiteDatabase db = this.getReadableDatabase();
        StringBuilder Query = new StringBuilder("SELECT idIndicateurs _id, * FROM Indicateurs WHERE ");
        for (int i = 0; i < indicateurs.size(); i++){
            Query.append("idIndicateurs = ").append(indicateurs.get(i));
            if(i < indicateurs.size()-1)
                Query.append(" OR ");
        }
        return db.rawQuery(Query.toString(), null);
    }
}
