package nwts.ru.autoshop.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.databases.DBHelper;
import nwts.ru.autoshop.models.CategoryItem;
import nwts.ru.autoshop.models.CategoryItems;
import nwts.ru.autoshop.models.ProductCategoris;
import nwts.ru.autoshop.models.ProductCategory;
import nwts.ru.autoshop.models.ProductDetail;
import nwts.ru.autoshop.models.ProductDetailImage;
import nwts.ru.autoshop.models.ProductDetailImages;
import nwts.ru.autoshop.api.request.ShopAPI;
import nwts.ru.autoshop.models.SubCategoryItem;
import nwts.ru.autoshop.models.SubCategoryItems;
import nwts.ru.autoshop.setting.BaseConstant;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by пользователь on 17.03.2017.
 */

public class ServiceIntentGetData extends IntentService {

    public DBHelper dbHelper;
    List<CategoryItem> categoryItems;
    List<ProductCategory> productCategory;
    List<SubCategoryItem> subCategoryItems;
    List<ProductDetail> productDetail;
    List<ProductDetailImage> productDetailImages;

    private long timeLoadedFromServer = 60000; //1 min

    private final int FLOWERS_GET_NETWORK_SUCCES_0 = 0;
    private final int FLOWERS_GET_NETWORK_SUCCES = 1;
    private final int FLOWERS_GET_RESPONSE_ERROR = 2;
    private final int FLOWERS_GET_FAILURE = 3;
    private int results;

    public ServiceIntentGetData() {
        super("name");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(BaseConstant.TAG, "Start:ServiceHelper:ServiceIntentGetData:Create: services..");
        categoryItems = new ArrayList<>();
        productCategory = new ArrayList<>();
        productDetail = new ArrayList<>();
        subCategoryItems = new ArrayList<>();
        productDetailImages = new ArrayList<>();
        dbHelper = TODOApplication.getInstance().dbHelper;
        if (dbHelper != null) {
            dbHelper.dbReadInLog();
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(BaseConstant.TAG, "Start:ServiceHelper:ServiceIntentGetData:: services..");
        if (intent != null) {
            if (intent.getStringExtra(BaseConstant.API_PAGE).equals(BaseConstant.ACTION_SERVICE_GET_CATEGORY_LIST)) {
                if (categoryItems != null) {
                    categoryItems.clear();
                }
                if (System.currentTimeMillis() - dbHelper.dbGetCategoryTimeRefresh() > timeLoadedFromServer && isOnline()) {
                    getCategory();
                } else {
                    getCategoryFromDBHelper(100);
                }
            }
            if (intent.getStringExtra(BaseConstant.API_PAGE).equals(BaseConstant.ACTION_SERVICE_GET_SUBCATEGORY_LIST)) {
                int key_id = intent.getIntExtra(BaseConstant.API_GET_KEY, 0);
                Log.d(BaseConstant.TAG, "Start:ServiceHelper:ServiceIntentGetData:: BaseConstant.API_GET_KEY =" + key_id);
                if (subCategoryItems != null) {
                    subCategoryItems.clear();
                }
                getSubCategory(key_id);
            }
            if (intent.getStringExtra(BaseConstant.API_PAGE).equals(BaseConstant.ACTION_SERVICE_GET_PRODUCT_LIST)) {
                int key_id = intent.getIntExtra(BaseConstant.API_GET_KEY, 0);
                Log.d(BaseConstant.TAG, "Start:ServiceHelper:ServiceIntentGetData:: BaseConstant.API_GET_KEY =" + key_id);
                if (productCategory != null) {
                    productCategory.clear();
                }
                getPoductCategory(key_id);
            }
            if (intent.getStringExtra(BaseConstant.API_PAGE).equals(BaseConstant.ACTION_SERVICE_GET_PRODUCT_DETAIL)) {
                int key_id = intent.getIntExtra(BaseConstant.API_GET_KEY, 0);
                Log.d(BaseConstant.TAG, "Start:ServiceHelper:ServiceIntentGetData:: BaseConstant.API_GET_KEY =" + key_id);
                if (productDetailImages != null) {
                    productDetailImages.clear();
                }
                getPoductDetail(key_id);
            }
        }
    }

    private void getCategory() {
        Log.d(BaseConstant.TAG, "Start:ServiceHelper:ServiceIntentGetData:: services.getCategory.");
        ShopAPI shopApi = ShopAPI.retrofit.create(ShopAPI.class);
        final Call<List<CategoryItem>> call = shopApi.getData();

        call.enqueue(new Callback<List<CategoryItem>>() {

            @Override
            public void onResponse(Call<List<CategoryItem>> call, Response<List<CategoryItem>> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().size(); i++) {
                        Log.d(BaseConstant.TAG, "ServiceIntentGetData:respones.body: "
                                + response.body().get(i).getCategory_ID() + " "
                                + response.body().get(i).getCategory_name() + " "
                                + response.body().get(i).getCategory_image()
                        );
                    }
                    categoryItems.addAll(response.body());
                    Log.d(BaseConstant.TAG, "ServiceIntentGetData:onResponse:Succesfull:categoryItems:size:" + categoryItems.size());
                    EventBus.getDefault().post(new CategoryItems(categoryItems, 200));
                    try {
                        dbHelper.dbDeleteCategory();
                    } catch (SQLiteException ex) {
                        Log.d(BaseConstant.TAG, "Error:Ощибка удаления категорий: dbHelper.dbDeleteCategory():" + ex.toString());
                    }
                    for (int i = 0; i < categoryItems.size(); i++) {
                        try {
                            dbHelper.putCategory(categoryItems.get(i).getCategory_ID(), categoryItems.get(i).getCategory_name(),
                                    categoryItems.get(i).getCategory_image(), System.currentTimeMillis());
                        } catch (SQLiteException ex) {
                            Log.d(BaseConstant.TAG, "Error:Ощибка вставки информации в табл.категорий: dbHelper.putCategory():" + ex.toString());
                        }
                    }
                    try {
                        dbHelper.dbReadInLog();
                    } catch (SQLiteException ex) {
                        Log.d(BaseConstant.TAG, "Error:Ошибка чтения инф. из табл. категорий: dbHelper.dbReadInLog():" + ex.toString());
                    }
                    Log.d(BaseConstant.TAG, "Start:ServiceHelper:ServiceIntentGetData:response:size=" + categoryItems.size());
                    Log.d(BaseConstant.TAG, "Start:ServiceHelper:ServiceIntentGetData:response:tostinrg" + categoryItems.toString());
                    Log.d(BaseConstant.TAG, "Start:ServiceHelper:ServiceIntentGetData:response:tostinrg" + categoryItems.get(0).getCategory_name());
                    Log.d(BaseConstant.TAG, "Start:ServiceHelper:ServiceIntentGetData:response:tostinrg" + categoryItems.get(1).getCategory_name());
                } else {
                    // Обрабатываем ошибку
                    Log.d(BaseConstant.TAG, "ServiceIntentGetData:getCategory:response.errorBody()");
                    ResponseBody errorBody = response.errorBody();
                    try {
                        Log.d(BaseConstant.TAG, errorBody.string());
                    } catch (IOException e) {
                        Log.d(BaseConstant.TAG, "ServiceIntentGetData:getCategory:response.errorBody()"+e.toString());
                    }
                    //Возвращаем данные из SQLite
                    getCategoryFromDBHelper(400);
                }
            }

            @Override
            public void onFailure(Call<List<CategoryItem>> call, Throwable throwable) {
                Log.d(BaseConstant.TAG, "ServiceIntentGetData:getCategory:onFailure:throwable:"+throwable.toString());
                getCategoryFromDBHelper(500);
            }
        });
    }

    private void getPoductCategory(int id_category) {
        Log.d(BaseConstant.TAG, "Start:ServiceHelper:ServiceIntentGetData:: services.getPoductCategory.id_category:" + id_category);
        ShopAPI shopApi = ShopAPI.retrofit.create(ShopAPI.class);
        final Call<List<ProductCategory>> call = shopApi.getProductCatalog(id_category);
        Log.d(BaseConstant.TAG, "getPoductCategory:call:" + call.request().toString());

        call.enqueue(new Callback<List<ProductCategory>>() {

            @Override
            public void onResponse(Call<List<ProductCategory>> call, Response<List<ProductCategory>> response) {
                if (response.isSuccessful()) {
                    productCategory.addAll(response.body());
                    EventBus.getDefault().post(new ProductCategoris(productCategory, 200));
                    Log.d(BaseConstant.TAG, "Start:ServiceHelper:ServiceIntentGetData:getPoductCategory:response:size=" + productCategory.size());
                    Log.d(BaseConstant.TAG, "Start:ServiceHelper:ServiceIntentGetData:getPoductCategory:response:tostinrg" + productCategory.toString());
                } else {
                    // Обрабатываем ошибку
                    Log.d(BaseConstant.TAG, "response.errorBody()");
                    ResponseBody errorBody = response.errorBody();
                    try {
                        Log.d(BaseConstant.TAG, errorBody.string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ProductCategory>> call, Throwable throwable) {
                Log.d(BaseConstant.TAG, "Что-то пошло не так");
            }
        });
    }

    private void getPoductDetail(int key_id) {
        ShopAPI shopApi = ShopAPI.retrofit.create(ShopAPI.class);
        final Call<List<ProductDetailImage>> call = shopApi.getProductDetail(key_id);
        Log.d(BaseConstant.TAG, "getPoductDetail:call:" + call.request().toString());
        call.enqueue(new Callback<List<ProductDetailImage>>() {

            @Override
            public void onResponse(Call<List<ProductDetailImage>> call, Response<List<ProductDetailImage>> response) {
                if (response.isSuccessful()) {
                    productDetailImages.addAll(response.body()); //this is object ProductDetail
                    EventBus.getDefault().post(new ProductDetailImages(productDetailImages, 200));
                    Log.d(BaseConstant.TAG, "Start:ServiceHelper:ServiceIntentGetData:getPoductDetail:response:size=" + productDetail.size());
                    Log.d(BaseConstant.TAG, "Start:ServiceHelper:ServiceIntentGetData:getPoductDetail:response:tostinrg" + productDetail.toString());
                } else {
                    // Обрабатываем ошибку
                    Log.d(BaseConstant.TAG, "response.errorBody()");
                    ResponseBody errorBody = response.errorBody();
                    try {
                        Log.d(BaseConstant.TAG, errorBody.string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ProductDetailImage>> call, Throwable throwable) {
                Log.d(BaseConstant.TAG, "Что-то пошло не так");
                Log.d(BaseConstant.TAG, "Что-то пошло не так:throwable:" + throwable.toString());

            }
        });
    }

    //Read category from local database
    private void getCategoryFromDBHelper(int errorCode) {
        Log.d(BaseConstant.TAG, "ServiceIntentGetData:getCategoryFromDBHelper:run...");

        try {
            for (int i = 0; i < dbHelper.getListCategoryItems().size(); i++) {
                Log.d(BaseConstant.TAG, "ServiceIntentGetData:getCategoryFromDBHelper.DB: "
                        + dbHelper.getListCategoryItems().get(i).getCategory_ID() + " "
                        + dbHelper.getListCategoryItems().get(i).getCategory_name() + " "
                        + dbHelper.getListCategoryItems().get(i).getCategory_image()
                );
            }
            categoryItems.addAll(dbHelper.getListCategoryItems());

            Log.d(BaseConstant.TAG, "ServiceIntentGetData:getCategoryFromDBHelper:categoryItems:size:" + categoryItems.size());
            for (int i = 0; i < categoryItems.size(); i++) {
                Log.d(BaseConstant.TAG, "ServiceIntentGetData:getCategoryFromDBHelper:categoryItems:Image: " + categoryItems.get(i).getCategory_image());
            }
            EventBus.getDefault().post(new CategoryItems(categoryItems, errorCode));
        } catch (SQLiteException ex) {
            Log.d(BaseConstant.TAG, "Error read Category from SQLite database:ServiceIntentGetData:getCategoryFromDBHelper: " + ex.toString());
        }
    }

    //Проверка доступности сети
    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    private void getSubCategory(int id_category) {
        Log.d(BaseConstant.TAG, "Start:ServiceHelper:ServiceIntentGetData:: services.getSubCategory.id_category:" + id_category);
        ShopAPI shopApi = ShopAPI.retrofit.create(ShopAPI.class);
        final Call<List<SubCategoryItem>> call = shopApi.getSubCatalog(id_category);
        Log.d(BaseConstant.TAG, "getSubCategory:call:" + call.request().toString());

        call.enqueue(new Callback<List<SubCategoryItem>>() {

            @Override
            public void onResponse(Call<List<SubCategoryItem>> call, Response<List<SubCategoryItem>> response) {
                if (response.isSuccessful()) {
                    subCategoryItems.addAll(response.body());
                    EventBus.getDefault().post(new SubCategoryItems(subCategoryItems, 200));
                    Log.d(BaseConstant.TAG, "Start:ServiceHelper:ServiceIntentGetData:getPoductCategory:response:size=" + subCategoryItems.size());
                    Log.d(BaseConstant.TAG, "Start:ServiceHelper:ServiceIntentGetData:getPoductCategory:response:tostinrg" + subCategoryItems.toString());
                } else {
                    // Обрабатываем ошибку
                    Log.d(BaseConstant.TAG, "response.errorBody()");
                    ResponseBody errorBody = response.errorBody();
                    try {
                        Log.d(BaseConstant.TAG, errorBody.string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<SubCategoryItem>> call, Throwable throwable) {
                Log.d(BaseConstant.TAG, "Что-то пошло не так");
            }
        });
    }
}
