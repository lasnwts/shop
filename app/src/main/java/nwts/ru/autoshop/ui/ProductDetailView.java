package nwts.ru.autoshop.ui;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.models.ProductDetailImage;
import nwts.ru.autoshop.models.ProductDetailImages;
import nwts.ru.autoshop.services.ServiceHelper;
import nwts.ru.autoshop.setting.BaseConstant;
import nwts.ru.autoshop.setting.PreferenceHelper;

import static nwts.ru.autoshop.network.api.Api.AdminPageURL;

public class ProductDetailView extends AppCompatActivity
        implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private Toolbar toolbar;
    PreferenceHelper preferenceHelper;
    private static final String TAG = BaseConstant.TAG_PRODUCT_DETAIL_FRAGMENT;
    private List<ProductDetailImage> productDetailImageList;
    ProgressBar prgLoading;
    TextView productName, productDesc;
    ImageView productImage;
    private String URL_KEY_STATE = "url_key_state";
    private SliderLayout mDemoSlider;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        productDetailImageList = new ArrayList<>();
        prgLoading = (ProgressBar) findViewById(R.id.prgLoadProductDetail);
        productName = (TextView) findViewById(R.id.productDetailNaem);
        productDesc = (TextView) findViewById(R.id.productDetailShortDesc);
        productImage = (ImageView) findViewById(R.id.productDetailImage);
        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFresco = new Intent(getApplicationContext(), FrescoActovity.class);
                intentFresco.putExtra(BaseConstant.URL_IMAGE_DOWNLOADED,TODOApplication.getUrl_Image());
                startActivity(intentFresco);
            }
        });
        PreferenceHelper.getInstance().init(getApplicationContext());
        preferenceHelper = PreferenceHelper.getInstance();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (null != toolbar) {
            toolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary_color)));
//            toolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.header)));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.product_detail_name));
            toolbar.inflateMenu(R.menu.menu_base);
        }

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean(TAG)) {
                request();
            }
        }
        //Slider
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

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
//            ListView l = (ListView) findViewById(R.id.transformers);
//            l.setAdapter(new TransformerAdapter(this));
//            l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    mDemoSlider.setPresetTransformer(((TextView) view).getText().toString());
//                    Toast.makeText(getApplicationContext(), ((TextView) view).getText().toString(), Toast.LENGTH_SHORT).show();
//                    Log.d("myLogs", "onItemClick:"+((TextView) view).getText().toString());
//                }
//            });
        }
    }

    private void request() {
        Log.d(BaseConstant.TAG, "ProductDetailView:request:product_id:" + TODOApplication.getMenu_Id());
        Intent intentService = new Intent(this, ServiceHelper.class);
        intentService.setAction(BaseConstant.ACTION_SERVICE_GET_PRODUCT_DETAIL);
        intentService.putExtra(BaseConstant.API_GET_KEY,TODOApplication.getMenu_Id());
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
        overridePendingTransition (R.anim.open_main, R.anim.close_next);
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
        outState.putString(URL_KEY_STATE,TODOApplication.getUrl_Image());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
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
    public void onEventProductDetails(ProductDetailImages event) {
        prgLoading.setVisibility(View.INVISIBLE);
        /*
        (List<ProductDetail> productDetails, int errors,
                          String menu_name, double price, String description, int category_ID, int subCategory_ID, int quantity, int product_Id
         */
//        productDetails = new ProductDetails(event.getProductDetails(),event.getErrors(),event.getMenu_name(),event.getPrice(),
//                event.getDescription(),event.getCategory_ID(),event.getSubCategory_ID(),event.getQuantity(),event.getProduct_Id());

        productName.setText(TODOApplication.getDetail_productName());
        productDesc.setText(Html.fromHtml(TODOApplication.getDetail_description()));
        if ( TODOApplication.getUrl_Image() != null){
            getImageRequest( TODOApplication.getUrl_Image());
        }
        productDetailImageList.addAll(event.getProductDetailImages());
        Log.d(BaseConstant.TAG,"ProductDetailView:onEventProductDetails:run");
        Log.d(BaseConstant.TAG,"ProductDetailView:onEventProductDetails:productDetailImageList.size():"+productDetailImageList.size());
        if (productDetailImageList.size()>0){
            Log.d(BaseConstant.TAG,"ProductDetailView:onEventProductDetails:productDetailImageList.size()>0");
            for (int i=0;i<productDetailImageList.size();i++){
                Log.d(BaseConstant.TAG,"ProductDetailView:onEventProductDetails:productDetailImageList.size(i)="+i);
                if ( TODOApplication.getUrl_Image() == null){
                    getImageRequest(productDetailImageList.get(i).getMenu_image());
                }
                Log.d(BaseConstant.TAG,"ProductDetailView:onEventProductDetails:"+productDetailImageList.get(i).getMenu_image());
                /*
                1
                 */
                TextSliderView textSliderView = new TextSliderView(this);
                // initialize a SliderLayout
                textSliderView
                        .description("My Image View")
                        .image(AdminPageURL + productDetailImageList.get(i).getMenu_image())
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .error(R.drawable.error_loading)
                        .setOnSliderClickListener(this);

                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", "This is my Image View");

                mDemoSlider.addSlider(textSliderView);
                /*
                2
                 */
            }
        }
    }

    private void getImageRequest(String url_image){
        Picasso mPicasso = Picasso.with(this);
        mPicasso.setIndicatorsEnabled(true);
        mPicasso.setLoggingEnabled(true);
        mPicasso.load(AdminPageURL + url_image)
                .resize(200, 150)
                .centerCrop()
                .error(R.drawable.error_load_image)
                .into(productImage);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
        Log.d("myLogs", "onSliderClick:"+slider.getBundle().get("extra"));
        Log.d("myLogs", "onSliderClick:url:"+slider.getUrl()); //.image().getUrl());
        Log.d("myLogs", "onSliderClick:url2:"+slider.getDescription());
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

    private void startFreescoActivity(){
        Intent intentFresco = new Intent(getApplicationContext(), FrescoActovity.class);
        intentFresco.putExtra(BaseConstant.URL_IMAGE_DOWNLOADED,TODOApplication.getUrl_Image());
        startActivity(intentFresco);
    }

    private void startFreescoActivityGallery(String url){
        Intent intentFresco = new Intent(getApplicationContext(), FrescoActovity.class);
        intentFresco.putExtra(BaseConstant.URL_IMAGE_DOWNLOADED,url);
        startActivity(intentFresco);
    }

}
