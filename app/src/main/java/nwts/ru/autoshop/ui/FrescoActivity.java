package nwts.ru.autoshop.ui;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.fragment.PageFragment;
import nwts.ru.autoshop.fragment.PageProductDetail;
import nwts.ru.autoshop.setting.BaseConstant;
import nwts.ru.autoshop.setting.PreferenceHelper;
import uk.co.senab.photoview.PhotoView;

import static nwts.ru.autoshop.network.api.Api.GET_IMAGES;
import static nwts.ru.autoshop.ui.Information.PAGE_COUNT;

public class FrescoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    PreferenceHelper preferenceHelper;
    ProgressBar prgLoading;
    protected int pageCount;
    ViewPager pager;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_actovity);
        pageCount = TODOApplication.getUrlProductDetailImages().size();
        prgLoading = (ProgressBar) findViewById(R.id.prgLoadFresco);
        pager = (ViewPager) findViewById(R.id.pagerProductImageDetail);
        preferenceHelper = PreferenceHelper.getInstance();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (null != toolbar) {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    toolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary_color, getTheme())));
                } else {
                    toolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary_color)));
                }
            } else {
                toolbar.setBackgroundColor(getResources().getColor(R.color.primary_color));
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(R.string.product_detail_name);
        }

        String Url = getIntent().getStringExtra(BaseConstant.URL_IMAGE_DOWNLOADED);
        if (!Url.matches("http:(.*)")) {
            Url = GET_IMAGES + Url;
        }

        prgLoading.setVisibility(View.INVISIBLE);
        pagerAdapter = new ProductFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        if (getIntent().getIntExtra(BaseConstant.URL_IMAGE_COUNTS,0) > 1){
            pager.setCurrentItem(getIntent().getIntExtra(BaseConstant.URL_IMAGE_COUNTS,0));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private class ProductFragmentPagerAdapter extends FragmentPagerAdapter {
        public ProductFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageProductDetail.newInstance(position);
    }

        @Override
        public int getCount() {
            return pageCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Фото " + (position + 1);
        }
    }
}

