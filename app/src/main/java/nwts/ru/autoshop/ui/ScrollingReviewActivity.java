package nwts.ru.autoshop.ui;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.adapter.AdapterComment;
import nwts.ru.autoshop.adapter.interfaces.AdapterClickListener;
import nwts.ru.autoshop.models.network.ProductComment;
import nwts.ru.autoshop.models.network.orders.ProductComments;
import nwts.ru.autoshop.services.ServiceHelper;
import nwts.ru.autoshop.setting.BaseConstant;
import nwts.ru.autoshop.setting.PreferenceHelper;

public class ScrollingReviewActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private PreferenceHelper preferenceHelper;
    private ImageView productImage;
    FloatingActionButton fab, fab_up;
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    protected RecyclerView mRecyclerView;
    ProgressBar prgLoading;
    TextView txtAlert;
    private int totalItemCount = 4;
    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }
    private static final int SPAN_COUNT = 1;
    private List<ProductComment> mProductComments;
    protected RecyclerView.LayoutManager layoutManager;

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
        mProductComments = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewCategoryProdCatalog);
        layoutManager = new GridLayoutManager(this, SPAN_COUNT);
        mRecyclerView.setLayoutManager(layoutManager);
        prgLoading = (ProgressBar) findViewById(R.id.prgLoadingProdCatalog);
        txtAlert = (TextView) findViewById(R.id.txtAlertProdCatalog);
        AdapterComment adapterComment = new AdapterComment(mProductComments, this, new AdapterClickListener() {
            @Override
            public void adapterOnClickListener(int item) {
                Toast.makeText(ScrollingReviewActivity.this, "ID = "+item,Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(adapterComment);

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

    private void request() {
        Intent intentService = new Intent(this, ServiceHelper.class);
        intentService.setAction(BaseConstant.ACTION_SERVICE_GET_COMMENTS_ID);
        startService(intentService);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        request();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventOrders(ProductComments event) {
        prgLoading.setVisibility(View.INVISIBLE);
        if (event.getProductComments().isEmpty() || event.getProductComments().size() < 1){
            txtAlert.setVisibility(View.VISIBLE);
        } else {
            txtAlert.setVisibility(View.INVISIBLE);
            mProductComments.clear();
            mProductComments.addAll(event.getProductComments());
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

}
