package nwts.ru.autoshop.ui;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.setting.BaseConstant;
import nwts.ru.autoshop.setting.PreferenceHelper;
import uk.co.senab.photoview.PhotoView;

import static nwts.ru.autoshop.network.api.Api.AdminPageURL;

public class FrescoActovity extends AppCompatActivity {

    private Toolbar toolbar;
    PreferenceHelper preferenceHelper;
    ProgressBar prgLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_actovity);
        prgLoading = (ProgressBar) findViewById(R.id.prgLoadFresco);
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
            Url = AdminPageURL + Url;
        }

        PhotoView photoView = (PhotoView) findViewById(R.id.iv_photo);

        Picasso mPicasso = Picasso.with(getApplicationContext());
        mPicasso.setIndicatorsEnabled(true);
        mPicasso.setLoggingEnabled(true);
        mPicasso.load(Url)
//                .resize(300, 250)
//                .centerCrop()
                .error(R.drawable.error_loading)
                .into(photoView);
        prgLoading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition (R.anim.open_main, R.anim.close_next);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}

