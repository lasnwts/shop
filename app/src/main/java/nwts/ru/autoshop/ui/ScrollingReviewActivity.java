package nwts.ru.autoshop.ui;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.setting.PreferenceHelper;

public class ScrollingReviewActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private PreferenceHelper preferenceHelper;
    private ImageView productImage;
    FloatingActionButton fab, fab_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_review);

        PreferenceHelper.getInstance().init(getApplicationContext());
        preferenceHelper = PreferenceHelper.getInstance();
        toolbar = (Toolbar) findViewById(R.id.toolbar_scroll);
        if (null != toolbar) {
            setSupportActionBar(toolbar);
            toolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary_color)));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.show_title_toolbal_cooment)
                    + TODOApplication.getDetail_productName());
            toolbar.inflateMenu(R.menu.menu_base);
        }
        productImage = (ImageView) findViewById(R.id.imageView_scrollactivity_comment);
        productImage.setImageResource(R.drawable.bigbang);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

         }


    final FloatingActionButton.OnVisibilityChangedListener addVisibilityChanged = new FloatingActionButton.OnVisibilityChangedListener() {
        public void onShown(final FloatingActionButton fab) {
            super.onShown(fab);
            Log.d("ScrollingReviewActivity", "Visibility changed");
        }
        public void onHidden(final FloatingActionButton fab) {
            super.onHidden(fab);
            Log.d("ScrollingReviewActivity", "Visibility hideen");
        }
    };




    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }


}
