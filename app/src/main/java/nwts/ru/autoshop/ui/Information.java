package nwts.ru.autoshop.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.fragment.PageFragment;
import nwts.ru.autoshop.setting.BaseConstant;
import nwts.ru.autoshop.setting.PreferenceHelper;

/**
 * Created by пользователь on 20.03.2017.
 */

public class Information extends AppCompatActivity {

    static final int PAGE_COUNT = 3;

    ViewPager pager;
    PagerAdapter pagerAdapter;
    private Toolbar toolbar;
    PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_layout);
        PreferenceHelper.getInstance().init(getApplicationContext());
        preferenceHelper = PreferenceHelper.getInstance();
        pager = (ViewPager) findViewById(R.id.pager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (null != toolbar) {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    toolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary_color, getTheme())));
                }else {
                    toolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary_color)));
                }
            } else {
                toolbar.setBackgroundColor(getResources().getColor(R.color.primary_color));
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(R.string.information_name);
            toolbar.inflateMenu(R.menu.menu_base);
        }

        //toolbar.setDisplayHomeAsUpEnabled(true);
        //toolbar.setHomeButtonEnabled(true);

        pagerAdapter = new InfoFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Log.d(BaseConstant.TAG, "Information navigate:onSupportNavigateUp");
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private class InfoFragmentPagerAdapter extends FragmentPagerAdapter {
        public InfoFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String mTitle = "";
            switch (position) {
                case 0:
                    mTitle = getResources().getString(R.string.txt_screen_1);
                    break;
                case 1:
                    mTitle = getResources().getString(R.string.txt_screen_2);
                    break;
                case 2:
                    mTitle = getResources().getString(R.string.txt_screen_3);
                    break;
                default:
                    break;
            }
            return mTitle;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition (R.anim.open_main, R.anim.close_next);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_alone, menu);
        MenuItem splashItem = menu.findItem(R.id.action_splash);
        splashItem.setChecked(preferenceHelper.getBoolean(BaseConstant.SPLASH_IS_INVISIBLE));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_splash:
                item.setChecked(!item.isChecked());
                PreferenceHelper.getInstance().putBoolean(BaseConstant.SPLASH_IS_INVISIBLE, item.isChecked());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
