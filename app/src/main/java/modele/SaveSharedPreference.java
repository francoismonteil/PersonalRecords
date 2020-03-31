package modele;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static modele.PreferencesUtility.LOGGED_ID;
import static modele.PreferencesUtility.LOGGED_IN_PREF;

public class SaveSharedPreference {

    private static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Set the Login Status
     * @param context
     * @param loggedIn
     * @param idLog
     */
    public static void setLoggedIn(Context context, boolean loggedIn, int idLog) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.putInt(LOGGED_ID, idLog);
        editor.apply();
    }

    /**
     * Get the Login Status
     * @param context
     * @return boolean: login status
     */
    public static int getLoggedStatus(Context context) {
        if(getPreferences(context).getBoolean(LOGGED_IN_PREF, false)){
            return getPreferences(context).getInt(LOGGED_ID, -1);
        }
        return -1;
    }

    public static void disconnect(Context context){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, false);
        editor.putInt(LOGGED_ID, -1);
        editor.apply();
    }
}
