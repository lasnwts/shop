package nwts.ru.autoshop.ui;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import nwts.ru.autoshop.BaseActivity;
import nwts.ru.autoshop.R;
import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.adapter.AdapterComment;
import nwts.ru.autoshop.adapter.interfaces.AdapterClickListener;
import nwts.ru.autoshop.fragment.dialogs.DialogFragmentAddComment;
import nwts.ru.autoshop.models.network.ProductComment;
import nwts.ru.autoshop.models.network.orders.ProductComments;
import nwts.ru.autoshop.network.ValidateToken;
import nwts.ru.autoshop.services.ServiceHelper;
import nwts.ru.autoshop.setting.BaseConstant;
import nwts.ru.autoshop.setting.PreferenceHelper;

import static nwts.ru.autoshop.network.api.Api.GET_IMAGES;

/**
 * Не стоит помещать объект RecyclerView в android.support.v4.widget.NestedScrollView
 * Будт проблемы с тем, что этот объект будет перехватывать event для recyclerview.
 * Если все же это произошло необходимо поставить
 * Add android:fillViewport=”true” to your NestedScrollView. Try below code in your xml, I am using same code and its working fine
 * And add this = mRecyclerView.setNestedScrollingEnabled(false);   to your activity/fragment
 * Сначала у меня он был в разметке content_scrolling_review.xml где recyclerView был в объекте NestedScrollView.
 */

public class ScrollingReviewActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private PreferenceHelper preferenceHelper;
    private ImageView productImage;
    FloatingActionButton fab, fab_up;
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    protected RecyclerView mRecyclerView;
    ProgressBar prgLoading;
    TextView txtAlert;
    private int totalItemCount = 2;
    private int lastVisibleItem;
    private DialogFragmentAddComment mDialogFragmentAddComment;
    private ValidateToken validateToken;

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
        //  layoutManager = new GridLayoutManager(this, SPAN_COUNT);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        prgLoading = (ProgressBar) findViewById(R.id.prgLoadingProdCatalog);
        txtAlert = (TextView) findViewById(R.id.txtAlertProdCatalog);
        AdapterComment adapterComment = new AdapterComment(mProductComments, this, new AdapterClickListener() {
            @Override
            public void adapterOnClickListener(int item) {
                Toast.makeText(ScrollingReviewActivity.this, "ID = " + item, Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapterComment);
        //mRecyclerView.setNestedScrollingEnabled(false);
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);


        fab_up = (FloatingActionButton) findViewById(R.id.fab_up);
        fab_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movedRecyclerViewOnTop();
            }
        });
        //security
        validateToken = ValidateToken.getInstance();
        //
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                if (!TODOApplication.getInstance().isValidateToken()) {
                    if (validateToken.getValidateToken()) {
                        TODOApplication.getInstance().setValidateToken(true);
                    } else {
                        if (!preferenceHelper.getBoolean(BaseConstant.errorNetworkValidation)) {
                            Intent loginIntent = (Intent) new Intent(ScrollingReviewActivity.this, LoginActivity.class);
                            startActivity(loginIntent);
                        } else {
                            Snackbar.make(view, "Для добавления отзыва необходимо зарегистрироваться.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            Toast.makeText(ScrollingReviewActivity.this, "Для добавления отзыва необходимо зарегистрироваться.", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    mDialogFragmentAddComment = new DialogFragmentAddComment();
                    mDialogFragmentAddComment.show(getFragmentManager(), "dialog_add_coomet");
                }
            }
        });

        if (TODOApplication.getUrl_Image() != null) {
            getImageRequest(TODOApplication.getUrl_Image());
        }


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                              @Override
                                              public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                                  super.onScrollStateChanged(recyclerView, newState);
                                                  lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                                                  if (fab.getVisibility() == View.VISIBLE) {
                                                      hideFab();
                                                  } else {
                                                      showFab();
                                                  }
                                              }

                                              @Override
                                              public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                                  super.onScrolled(recyclerView, dx, dy);
                                                  lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                                                  if (fab.getVisibility() == View.VISIBLE) {
                                                      hideFab();
                                                  } else {
                                                      showFab();
                                                  }
                                              }
                                          }

        );

    }


//    final FloatingActionButton.OnVisibilityChangedListener addVisibilityChanged = new FloatingActionButton.OnVisibilityChangedListener() {
//        public void onShown(final FloatingActionButton fab) {
//            super.onShown(fab);
//            Log.d("ScrollingReviewActivity", "Visibility changed");
//        }
//
//        public void onHidden(final FloatingActionButton fab) {
//            super.onHidden(fab);
//            Log.d("ScrollingReviewActivity", "Visibility hideen");
//        }
//    };

    private void getImageRequest(String url_image) {
        Picasso mPicasso = Picasso.with(this);
        mPicasso.setIndicatorsEnabled(true);
        mPicasso.setLoggingEnabled(true);
        mPicasso.load(GET_IMAGES + url_image)
                //  .resize(300, 300)
                //  .centerCrop()
                // .centerInside()
                .error(R.drawable.error_load_image)
                .into(productImage);
    }


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
        if (event.getProductComments().isEmpty() || event.getProductComments().size() < 1) {
            txtAlert.setVisibility(View.VISIBLE);
        } else {
            txtAlert.setVisibility(View.INVISIBLE);
            mProductComments.clear();
            mProductComments.addAll(event.getProductComments());
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }


    /*
fab
 */
    //magic happens here
    private void hideFab() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            final Point point = new Point();
            ScrollingReviewActivity.this.getWindow().getWindowManager().getDefaultDisplay().getSize(point);
            final float translation = fab_up.getY() - point.y;
            fab_up.animate().translationYBy(-translation).start();
        } else {
            Animation animation = AnimationUtils.makeOutAnimation(ScrollingReviewActivity.this, true);
            animation.setFillAfter(true);

            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    fab_up.setClickable(false);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            fab_up.startAnimation(animation);
        }
    }

    private void showFab() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            fab_up.animate().translationY(0).start();
        } else {
            Animation animation = AnimationUtils.makeInAnimation(ScrollingReviewActivity.this, false);
            animation.setFillAfter(true);

            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    fab_up.setClickable(true);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            fab_up.startAnimation(animation);
        }
    }
     /*
     fab 2
     */

    public void movedRecyclerViewOnTop() {
        if (mRecyclerView != null && mRecyclerView.getLayoutManager().canScrollVertically()
                && mRecyclerView.getLayoutManager().getItemCount() > 0) {
            // layoutManager.scrollToPosition(0); // тоже возможно
            mRecyclerView.smoothScrollToPosition(0);
        }
    }


}
