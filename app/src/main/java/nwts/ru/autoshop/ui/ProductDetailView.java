package nwts.ru.autoshop.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import nwts.ru.autoshop.BaseActivity;
import nwts.ru.autoshop.R;
import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.fragment.dialogs.DialogFragmentCartCount;
import nwts.ru.autoshop.models.ProductDetailImage;
import nwts.ru.autoshop.models.ProductDetailImages;
import nwts.ru.autoshop.models.network.cart.ErrorModel;
import nwts.ru.autoshop.models.network.cart.ErrorModels;
import nwts.ru.autoshop.services.ServiceHelper;
import nwts.ru.autoshop.setting.BaseConstant;
import nwts.ru.autoshop.setting.PreferenceHelper;

import static nwts.ru.autoshop.network.api.Api.GET_IMAGES;

public class ProductDetailView extends AppCompatActivity
        implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private Toolbar toolbar;
    PreferenceHelper preferenceHelper;
    private static final String TAG = BaseConstant.TAG_PRODUCT_DETAIL_FRAGMENT;
    private List<ProductDetailImage> productDetailImageList;
    ProgressBar prgLoading;
    TextView productName, productDesc, productFullDesc, productRating, productPrice, productCount;
    TextView divider1, divider2, divider3, divider4, divider5, divider6;
    ImageView productImage;
    private String URL_KEY_STATE = "url_key_state";
    private SliderLayout mDemoSlider;
    private int mDemoSliderCounts = 0; //кол-во images
    private ArrayList<String> mStringArrayList;
    private String fullTextDescription = "";
    private FloatingActionButton fab, fabReview;
    DecimalFormat formatData = new DecimalFormat("0.00");
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        productDetailImageList = new ArrayList<>();
        mStringArrayList = new ArrayList<>();
        prgLoading = (ProgressBar) findViewById(R.id.prgLoadProductDetail);
        productName = (TextView) findViewById(R.id.productDetailName);
        productDesc = (TextView) findViewById(R.id.productDetailShortDesc);
        productFullDesc = (TextView) findViewById(R.id.productDetailFullDesc);
        productRating = (TextView) findViewById(R.id.productDetailRating);
        productPrice = (TextView) findViewById(R.id.productDetailPrice);
        productCount = (TextView) findViewById(R.id.productDetailCount);


        /*
        Dividers
         */
        divider1 = (TextView) findViewById(R.id.productDetailDivider1);
        divider2 = (TextView) findViewById(R.id.productDetailDivider2);
        divider3 = (TextView) findViewById(R.id.productDetailDivider3);
        divider4 = (TextView) findViewById(R.id.productDetailDivider4);
        divider5 = (TextView) findViewById(R.id.productDetailDivider5);
        divider6 = (TextView) findViewById(R.id.productDetailDivider6);

        /*
        test
         */
        /*
        productCount.setText("    20");
        productCount.setVisibility(View.VISIBLE);
        productPrice.setText("200.00");
        productPrice.setVisibility(View.VISIBLE);
        productRating.setText("10");
        productRating.setVisibility(View.VISIBLE);

        productFullDesc.setText(R.string.productFullDesc);

         */
        /*
        test
         */
        productImage = (ImageView) findViewById(R.id.productDetailImage);

        PreferenceHelper.getInstance().init(getApplicationContext());
        preferenceHelper = PreferenceHelper.getInstance();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (null != toolbar) {
            toolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary_color)));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.product_detail_name));
            toolbar.inflateMenu(R.menu.menu_base);
        }

/*        if (savedInstanceState == null) {
//            if (savedInstanceState.getBoolean(TAG)) {
                request();
//            }
        }*/
        //Slider
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

/*
        HashMap<String, String> url_maps = new HashMap<String, String>();
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal", R.drawable.hannibal);
        file_maps.put("Big Bang Theory", R.drawable.bigbang);
        file_maps.put("House of Cards", R.drawable.house);
        file_maps.put("Game of Thrones", R.drawable.game_of_thrones);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
            mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mDemoSlider.setCustomAnimation(new DescriptionAnimation());
            mDemoSlider.setDuration(4000);
            mDemoSlider.addOnPageChangeListener(this);
        }
*/

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFresco = new Intent(getApplicationContext(), FrescoActivity.class);
                intentFresco.putExtra(BaseConstant.URL_IMAGE_DOWNLOADED, TODOApplication.getUrl_Image());
                if (mDemoSlider != null) {
                    intentFresco.putExtra(BaseConstant.URL_IMAGE_COUNTS, mDemoSliderCounts);
                }
                startActivity(intentFresco);
            }
        });

        productDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProductFullDescription();
            }
        });
        productFullDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProductFullDescription();
            }
        });
        fab = (FloatingActionButton) findViewById(R.id.fabProductDetailCart);
        if (TODOApplication.getDetail_quantity() < 1) {
            fab.setVisibility(View.GONE);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragmentCartCount dialogFragmentCartCount = new DialogFragmentCartCount();
                dialogFragmentCartCount.show(getFragmentManager(), null);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //mBlankFragment.fragmentSettext(getString(R.string.frset_text));
            }
        });
        fabReview = (FloatingActionButton) findViewById(R.id.fabProductDetailReviews);
        fabReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentScrollReview = (Intent) new Intent(ProductDetailView.this, ScrollingReviewActivity.class);
                startActivity(intentScrollReview);
                //mBlankFragment.fragmentSettext(getString(R.string.frset_text));
            }
        });
    }

    private void getProductFullDescription() {
        if (productFullDesc.getText().toString().equals(getResources().getString(R.string.productFullDesc))) {
            productDesc.setTextColor(getResources().getColor(R.color.primary_dark));
            productDesc.setText(R.string.productFullToShortDesc);
            productFullDesc.setTypeface(null, Typeface.NORMAL);
            productFullDesc.setTextColor(getResources().getColor(R.color.black));
            productFullDesc.setText(Html.fromHtml(fullTextDescription + fullTextDescription));
            fab.setVisibility(View.INVISIBLE);
            fabReview.setVisibility(View.INVISIBLE);
        } else {
            productDesc.setTextColor(getResources().getColor(R.color.black));
            if (TODOApplication.getDetail_description() != null && TODOApplication.getDetail_description().length() > 150) {
                productDesc.setText(Html.fromHtml(TODOApplication.getDetail_description().substring(0, 150)) + "...");
            } else {
                productDesc.setText(Html.fromHtml(TODOApplication.getDetail_description()));
            }
            productFullDesc.setTypeface(null, Typeface.BOLD);
            productFullDesc.setTextColor(getResources().getColor(R.color.primary_dark));
            productFullDesc.setText(R.string.productFullDesc);
            fab.setVisibility(View.VISIBLE);
            fabReview.setVisibility(View.VISIBLE);
        }
    }

    private void request() {
        Log.d(BaseConstant.TAG, "ProductDetailView:request:product_id:" + TODOApplication.getMenu_Id());
        Intent intentService = new Intent(this, ServiceHelper.class);
        intentService.setAction(BaseConstant.ACTION_SERVICE_GET_PRODUCT_DETAIL);
        intentService.putExtra(BaseConstant.API_GET_KEY, TODOApplication.getProductDetail_Id());
        startService(intentService);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base, menu);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(TAG, true);
        outState.putString(URL_KEY_STATE, TODOApplication.getUrl_Image());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        request();
        mDemoSlider.startAutoCycle();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventQuantity(ErrorModels event) {
        if (!event.getErrorModels().get(0).getError()) {
            productCount.setText("Доступно: " + event.getErrorModels().get(0).getCounters());
        } else {
            String errorMessage;
            if (TextUtils.isEmpty(event.getErrorModels().get(0).getMessage())) {
                errorMessage = getString(R.string.show_non_detect_error);
            } else {
                errorMessage = event.getErrorModels().get(0).getMessage();
                if (errorMessage.equals(getString(R.string.show_errpr_registration_check))) {
                    errorMessage = getString(R.string.show_error_need_login);
                }
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Возникла ошибка: ")
                    .setMessage(errorMessage)
                    .setIcon(R.drawable.ic_error)
                    .setCancelable(false)
                    .setNegativeButton("Да, поятненько  :(",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    if (!preferenceHelper.getBoolean(BaseConstant.errorNetworkValidation)) {
                                        Intent loginIntent = (Intent) new Intent(ProductDetailView.this, LoginActivity.class);
                                        startActivity(loginIntent);
                                    }
                                }
                            });
            AlertDialog alertBalance = builder.create();
            alertBalance.show();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventProductDetails(ProductDetailImages event) {
        prgLoading.setVisibility(View.INVISIBLE);
        divider1.setVisibility(View.VISIBLE);
        divider2.setVisibility(View.VISIBLE);
        divider3.setVisibility(View.VISIBLE);
        divider4.setVisibility(View.VISIBLE);
        divider5.setVisibility(View.VISIBLE);
        divider6.setVisibility(View.VISIBLE);
        //
        productCount.setText("Доступно: " + TODOApplication.getDetail_quantity());
        productCount.setVisibility(View.VISIBLE);
        productPrice.setText("Стоимость: " + formatData.format(TODOApplication.getDetail_price()) + " р.");
        productPrice.setVisibility(View.VISIBLE);
        productRating.setText("Рейтинг: ");
        productRating.setVisibility(View.VISIBLE);
        productFullDesc.setText(R.string.productFullDesc);
        //clear url
        TODOApplication.clearUrlProductDetailImages();
        mStringArrayList.clear();

        productName.setText(TODOApplication.getDetail_productName());
        if (TODOApplication.getDetail_description() != null && TODOApplication.getDetail_description().length() > 150) {
            productDesc.setText(Html.fromHtml(TODOApplication.getDetail_description().substring(0, 150)) + "...");
        } else {
            productDesc.setText(Html.fromHtml(TODOApplication.getDetail_description()));
        }
        // productDesc.setText(Html.fromHtml(TODOApplication.getDetail_description().substring(0,150+TODOApplication.getDetail_description().indexOf(" ",150)))+"...");
        fullTextDescription = TODOApplication.getDetail_description();
        if (TODOApplication.getUrl_Image() != null) {
            getImageRequest(TODOApplication.getUrl_Image());
        }
        productDetailImageList.clear();
        productDetailImageList.addAll(event.getProductDetailImages());
        Log.d(BaseConstant.TAG, "ProductDetailView:onEventProductDetails:run");
        Log.d(BaseConstant.TAG, "ProductDetailView:onEventProductDetails:productDetailImageList.size():" + productDetailImageList.size());
        if (productDetailImageList.size() > 0) {
            mDemoSlider.removeAllSliders();
//            mDemoSlider.removeAllViews();
            Log.d(BaseConstant.TAG, "ProductDetailView:onEventProductDetails:productDetailImageList.size()>0");
            for (int i = 0; i < productDetailImageList.size(); i++) {
                Log.d(BaseConstant.TAG, "ProductDetailView:onEventProductDetails:productDetailImageList.size(i)=" + i);
                if (TODOApplication.getUrl_Image() == null) {
                    getImageRequest(productDetailImageList.get(i).getMenu_image());
                }
                Log.d(BaseConstant.TAG, "ProductDetailView:onEventProductDetails:" + productDetailImageList.get(i).getMenu_image());
                /*
                1
                 */
                TextSliderView textSliderView = new TextSliderView(this);
                // initialize a SliderLayout
                textSliderView
                        .description(productDetailImageList.get(i).getDescription())
                        .image(GET_IMAGES + productDetailImageList.get(i).getMenu_image())
                        .setScaleType(BaseSliderView.ScaleType.CenterInside)
//                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .error(R.drawable.error_loading)
                        .setOnSliderClickListener(this);

                //add new image_url
                mStringArrayList.add(GET_IMAGES + productDetailImageList.get(i).getMenu_image());

                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", "This is my Image View");

                mDemoSlider.addSlider(textSliderView);
                /*
                2
                 */
            }
            mDemoSliderCounts = productDetailImageList.size();
            TODOApplication.setUrlProductDetailImages(mStringArrayList);
        }
    }

    private void getImageRequest(String url_image) {
        Picasso mPicasso = Picasso.with(this);
        mPicasso.setIndicatorsEnabled(true);
        mPicasso.setLoggingEnabled(true);
        mPicasso.load(GET_IMAGES + url_image)
                .resize(300, 300)
                //  .centerCrop()
                .centerInside()
                .error(R.drawable.error_load_image)
                .into(productImage);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
        Log.d("myLogs", "onSliderClick:" + slider.getBundle().get("extra"));
        Log.d("myLogs", "onSliderClick:url:" + slider.getUrl()); //.image().getUrl());
        Log.d("myLogs", "onSliderClick:url2:" + slider.getDescription());
        mDemoSlider.setDuration(4000);
        if (slider.getUrl() != null) {
            startFreescoActivityGallery(slider.getUrl());
        }
//        if (slider.getUrl() != null) {


        //       loadImage(getApplicationContext(),slider.getUrl());
        //       Log.d("myLogs","TODOApplication:Uri:"+TODOApplication.getBmpUri().toString());
//            Intent intent = new Intent(this, .class);
//            intent.putExtra("url_images",slider.getUrl());
//            startActivity(intent);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void startFreescoActivity() {
        Intent intentFresco = new Intent(getApplicationContext(), FrescoActivity.class);
        intentFresco.putExtra(BaseConstant.URL_IMAGE_DOWNLOADED, TODOApplication.getUrl_Image());
        startActivity(intentFresco);
    }

    private void startFreescoActivityGallery(String url) {
        Intent intentFresco = new Intent(getApplicationContext(), FrescoActivity.class);
        intentFresco.putExtra(BaseConstant.URL_IMAGE_DOWNLOADED, url);
        startActivity(intentFresco);
    }

    public void startCartInput(int item, int product_id) {
        Toast.makeText(this, "Кол-во:" + item, Toast.LENGTH_LONG).show();
        Intent intentService = new Intent(this, ServiceHelper.class);
        intentService.setAction(BaseConstant.ACTION_SERVICE_GET_CART_INPUT);
        intentService.putExtra(BaseConstant.API_GET_KEY, item);
        intentService.putExtra(BaseConstant.API_KEY_ID, product_id);
        startService(intentService);
    }


}
