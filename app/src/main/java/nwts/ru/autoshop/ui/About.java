package nwts.ru.autoshop.ui;

import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.lb.material_preferences_library.PreferenceActivity;
import com.lb.material_preferences_library.custom_preferences.Preference;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.setting.BaseConstant;

/**
 * Created by пользователь on 20.03.2017.
 */

public class About extends PreferenceActivity
        implements Preference.OnPreferenceClickListener {

    //    //builds
    private int versionBuild = 0;
    private String versionName = "";


    @Override
    protected int getPreferencesXmlId() {
        return R.xml.pref_about;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Preference prefShareKey      = (Preference) findPreference(getString(R.string.pref_share_key));
        Preference prefRateReviewKey = (Preference) findPreference(getString(R.string.pref_rate_review_key));
        Preference prefBuild         = (Preference) findPreference(getString(R.string.build));

        prefBuild.setSummary(versionName);

        prefShareKey.setOnPreferenceClickListener(this);
        prefRateReviewKey.setOnPreferenceClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition (R.anim.open_main, R.anim.close_next);
    }


    @Override
    public boolean onPreferenceClick(android.preference.Preference preference) {
        if(preference.getKey().equals(getString(R.string.pref_share_key))) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT,
                    getString(R.string.subject));
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.message) +
                    " " + getString(R.string.googleplay_url));
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
        }else if(preference.getKey().equals(getString(R.string.pref_rate_review_key))) {
            Intent rateReviewIntent = new Intent(Intent.ACTION_VIEW);
            rateReviewIntent.setData(Uri.parse(
                    getString(R.string.googleplay_url)));
            startActivity(rateReviewIntent);
        }
        return true;
    }

}
