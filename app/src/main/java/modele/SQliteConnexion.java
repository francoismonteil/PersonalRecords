package modele;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQliteConnexion extends SQLiteOpenHelper{

    public SQliteConnexion(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQLCreateUtilisateurs = "CREATE TABLE Utilisateurs (\n" +
                "  idUtilisateurs INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "  identifiant VARCHAR(20) UNIQUE NOT NULL,\n" +
                "  prenom VARCHAR(45) NOT NULL,\n" +
                "  nom VARCHAR(45) NOT NULL,\n" +
                "  motdepasse TEXT NOT NULL);\n";
        String SQLCreateIndicateurs = "CREATE TABLE `Indicateurs` (\n" +
                "  `idIndicateurs` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "  `type` VARCHAR(45) NOT NULL,\n" +
                "  `label` VARCHAR(45) NOT NULL,\n" +
                "  `Utilisateurs_idUtilisateurs` INTEGER NOT NULL,\n" +
                "  CONSTRAINT `fk_Indicateurs_Utilisateurs`\n" +
                "    FOREIGN KEY (`Utilisateurs_idUtilisateurs`)\n" +
                "    REFERENCES `Utilisateurs` (`idUtilisateurs`)\n" +
                "    ON DELETE NO ACTION\n" +
                "    ON UPDATE NO ACTION);";

        String SQLCreateEspaces = "CREATE TABLE `Espaces` (\n" +
                "  `idEspaces` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "  `label` VARCHAR(45) NOT NULL,\n" +
                "  `description` VARCHAR(255),\n" +
                "  `Utilisateurs_idUtilisateurs` INTEGER NOT NULL,\n" +
                "  CONSTRAINT `fk_Espaces_Utilisateurs1`\n" +
                "    FOREIGN KEY (`Utilisateurs_idUtilisateurs`)\n" +
                "    REFERENCES `Utilisateurs` (`idUtilisateurs`)\n" +
                "    ON DELETE NO ACTION\n" +
                "    ON UPDATE NO ACTION);";

        String SQLCreateEspacesHasIndicateurs = "CREATE TABLE `Espaces_has_Indicateurs` (\n" +
                "  `Espaces_idEspaces` INTEGER NOT NULL,\n" +
                "  `Indicateurs_idIndicateurs` INTEGER NOT NULL,\n" +
                "  `valueBoolean` TINYINT,\n" +
                "  `valueString` TEXT,\n" +
                "  `valueFloat` FLOAT,\n" +
                "  `valueInt` INTEGER,\n" +
                "  `valuePhoto` TEXT,\n" +
                "  `date` DATETIME NOT NULL,\n" +
                "  PRIMARY KEY (`Espaces_idEspaces`, `Indicateurs_idIndicateurs`),\n" +
                "  CONSTRAINT `fk_Espaces_has_Indicateurs_Espaces1`\n" +
                "    FOREIGN KEY (`Espaces_idEspaces`)\n" +
                "    REFERENCES `Espaces` (`idEspaces`)\n" +
                "    ON DELETE NO ACTION\n" +
                "    ON UPDATE NO ACTION,\n" +
                "  CONSTRAINT `fk_Espaces_has_Indicateurs_Indicateurs1`\n" +
                "    FOREIGN KEY (`Indicateurs_idIndicateurs`)\n" +
                "    REFERENCES `Indicateurs` (`idIndicateurs`)\n" +
                "    ON DELETE NO ACTION\n" +
                "    ON UPDATE NO ACTION);";


        db.execSQL(SQLCreateUtilisateurs);
        db.execSQL(SQLCreateIndicateurs);
        db.execSQL(SQLCreateEspaces);
        db.execSQL(SQLCreateEspacesHasIndicateurs);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Utilisateurs");
        db.execSQL("DROP TABLE IF EXISTS Indicateurs");
        db.execSQL("DROP TABLE IF EXISTS Espaces");
        db.execSQL("DROP TABLE IF EXISTS EspacesHasIndicateurs");
    }
}
