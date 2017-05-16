package nwts.ru.autoshop.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import static android.R.attr.value;

/**
 * Created by пользователь on 13.03.2017.
 */

public class PreferenceHelper {
    private static PreferenceHelper instance;

    private Context context;

    private SharedPreferences preferences;

    private PreferenceHelper() {
    }

    public static PreferenceHelper getInstance() {
        if (instance == null) {
            instance = new PreferenceHelper();
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void putAuthToken(String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("AuthToken", value);
        editor.apply();
    }

    public void putString(String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, value);
            editor.apply();
        }
    }

    public void putUserName(String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("UserName", value);
        editor.apply();
    }

    public void putEmail(String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Email", value);
        editor.apply();
    }

    public String getAuthToken() {
        return preferences.getString("AuthToken", null);
    }

    public void putUserId(int value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("UserId", value);
        editor.apply();
    }

    public String getUserName() {
        return preferences.getString("UserName", null);
    }

    public String getUserId() {
        return preferences.getString("UserId", null);
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public String getEmail() {
        return preferences.getString("Email", null);
    }

    public String getString(String key) {
        if (!TextUtils.isEmpty(key)){
            return preferences.getString(key, null);
        } else {
            return null;
        }
    }


}
