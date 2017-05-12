package nwts.ru.autoshop.setting;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by пользователь on 13.03.2017.
 */

public class PreferenceHelper {
    private static PreferenceHelper instance;

    private Context context;

    private SharedPreferences preferences;

    private PreferenceHelper() {
    }

    public static PreferenceHelper getInstance(){
        if (instance==null){
            instance = new PreferenceHelper();
        }
        return instance;
    }

    public void init(Context context){
        this.context = context;
        preferences = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
    }

    public void putBoolean (String key, boolean value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    public void putAuthToken (String key, String value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.apply();
    }


    public String getAuthToken(){
        return preferences.getString("AuthToken",null);
    }

    public String getUserId(){
        return preferences.getString("UserId",null);
    }

    public boolean getBoolean (String key){
        return preferences.getBoolean(key,false);
    }


}
