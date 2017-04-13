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
import org.greenrobot.greendao.query.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.databases.DBHelper;
import nwts.ru.autoshop.databases.DataManager;
import nwts.ru.autoshop.models.CategoryItem;
import nwts.ru.autoshop.models.CategoryItemDao;
import nwts.ru.autoshop.models.CategoryItems;
import nwts.ru.autoshop.models.DaoSession;
import nwts.ru.autoshop.models.GetCache;
import nwts.ru.autoshop.models.GetCacheDao;
import nwts.ru.autoshop.models.ProductCategoris;
import nwts.ru.autoshop.models.ProductCategory;
import nwts.ru.autoshop.models.ProductCategoryDao;
import nwts.ru.autoshop.models.ProductDetail;
import nwts.ru.autoshop.models.ProductDetailImage;
import nwts.ru.autoshop.models.ProductDetailImageDao;
import nwts.ru.autoshop.models.ProductDetailImages;
import nwts.ru.autoshop.models.SubCategoryItemDao;
import nwts.ru.autoshop.network.request.ShopAPI;
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
    private CategoryItems mCategoryItemsList;
    List<ProductCategory> productCategory;
    List<SubCategoryItem> subCategoryItems;
    private SubCategoryItem mSubCategoryItem;
    private SubCategoryItemDao mSubCategoryItemDao;
    List<ProductDetail> productDetail;
    List<ProductDetailImage> productDetailImages;
    private ProductDetailImage mProductDetailImage;
    private ProductDetailImageDao mProductDetailImageDao;
    private ProductCategoryDao mProductCategoryDao;
    List<GetCache> mGetCacheList;
    GetCache mGetCache;
    private GetCacheDao mGetCacheDao;
    private CategoryItemDao mCategoryItemDao;

    private long timeLoadedFromServer = 60000 * 3; //1 min

//    private int results;

    private DaoSession mDaoSession;
//    protected DataManager mDataManager;

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
        mGetCacheList = new ArrayList<>();
        dbHelper = TODOApplication.getInstance().dbHelper;
        if (dbHelper != null) {
            dbHelper.dbReadInLog();
        }

        DataManager dataManager = DataManager.getInstance();
        mDaoSession = dataManager.getDaoSession();

        if (mDaoSession == null) {
            Log.d(BaseConstant.LOG_TAG, "ServiceIntentGetData:DataManager dataManager =" +
                    "DataManager.getInstance();mDaoSession = dataManager.getDaoSession()== null!!");
        } else {
            mProductCategoryDao = mDaoSession.getProductCategoryDao();
            mGetCacheDao = mDaoSession.getGetCacheDao();
            mCategoryItemDao = mDaoSession.getCategoryItemDao();
            mSubCategoryItemDao = mDaoSession.getSubCategoryItemDao();
            mProductDetailImageDao = mDaoSession.getProductDetailImageDao();

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
                getCategory();
//                if (System.currentTimeMillis() - dbHelper.dbGetCategoryTimeRefresh() > timeLoadedFromServer && isOnline()) {
//                    getCategory();
//                } else {
//                    getCategoryFromDBHelper(100);
//                }
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

        /**
         * Оценка времени с последнего именно такого запроса
         */
        if (System.currentTimeMillis() - getDateTimeFromGetCache(call.request().toString()) > timeLoadedFromServer) {

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
                        putCategory(categoryItems);
                        putGetCache(call.request().toString());
//                        try {
//                            dbHelper.dbDeleteCategory();
//                        } catch (SQLiteException ex) {
//                            Log.d(BaseConstant.TAG, "Error:Ощибка удаления категорий: dbHelper.dbDeleteCategory():" + ex.toString());
//                        }
//                        for (int i = 0; i < categoryItems.size(); i++) {
//                            try {
//                                dbHelper.putCategory(categoryItems.get(i).getCategory_ID(), categoryItems.get(i).getCategory_name(),
//                                        categoryItems.get(i).getCategory_image(), System.currentTimeMillis());
//                            } catch (SQLiteException ex) {
//                                Log.d(BaseConstant.TAG, "Error:Ощибка вставки информации в табл.категорий: dbHelper.putCategory():" + ex.toString());
//                            }
//                        }
//                        try {
//                            dbHelper.dbReadInLog();
//                        } catch (SQLiteException ex) {
//                            Log.d(BaseConstant.TAG, "Error:Ошибка чтения инф. из табл. категорий: dbHelper.dbReadInLog():" + ex.toString());
//                        }
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
                            Log.d(BaseConstant.TAG, "ServiceIntentGetData:getCategory:response.errorBody()" + e.toString());
                        }
                        //Возвращаем данные из SQLite
//                        getCategoryFromDBHelper(400);
                        getCategoryDao(response.code());
                    }
                }

                @Override
                public void onFailure(Call<List<CategoryItem>> call, Throwable throwable) {
                    Log.d(BaseConstant.TAG, "ServiceIntentGetData:getCategory:onFailure:throwable:" + throwable.toString());
//                    getCategoryFromDBHelper(500);
                    getCategoryDao(500);
                }
            });
        } else {
            /**
             *  Отдаем из кэша ode = 700
             */
//            Query<ProductCategory> mProducts = mDaoSession.queryBuilder(ProductCategory.class)
//                    .where(ProductCategoryDao.Properties.SubCategory_ID.eq(id_category)).build();
//            Log.d("MyLogs", "QueryBuilder<NewPerson> pers:" + mProducts.toString());
//            productCategory = mProducts.list();
//            EventBus.getDefault().post(new ProductCategoris(productCategory, 700));
            getCategoryDao(700);
        }
    }

    /**
     * Получаем список товаров в подкатегории
     *
     * @param id_category
     */
    private void getPoductCategory(final int id_category) {
        Log.d(BaseConstant.TAG, "Start:ServiceHelper:ServiceIntentGetData:: services.getPoductCategory.id_category:" + id_category);
        ShopAPI shopApi = ShopAPI.retrofit.create(ShopAPI.class);
        final Call<List<ProductCategory>> call = shopApi.getProductCatalog(id_category);
        Log.d(BaseConstant.TAG, "getPoductCategory:call:" + call.request().toString());

        /**
         * Оценка времени с последнего именно такого запроса
         */
        if (System.currentTimeMillis() - getDateTimeFromGetCache(call.request().toString()) > timeLoadedFromServer) {

            call.enqueue(new Callback<List<ProductCategory>>() {

                @Override
                public void onResponse(Call<List<ProductCategory>> call, Response<List<ProductCategory>> response) {
                    if (response.isSuccessful()) {
                        productCategory.addAll(response.body());
                        EventBus.getDefault().post(new ProductCategoris(productCategory, response.code()));
                        putProductCategory(productCategory);
                        putGetCache(call.request().toString());
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
                        getProductCategoryDao(id_category, response.code());
                    }
                }

                @Override
                public void onFailure(Call<List<ProductCategory>> call, Throwable throwable) {
                    Log.d(BaseConstant.TAG, "Что-то пошло не так");
                    getProductCategoryDao(id_category, 501);
                }
            });

        } else {
            /**
             *  Отдаем из кэша ode = 700
             */
            getProductCategoryDao(id_category, 700);
        }
    }

    private void getPoductDetail(final int key_id) {
        ShopAPI shopApi = ShopAPI.retrofit.create(ShopAPI.class);
        final Call<List<ProductDetailImage>> call = shopApi.getProductDetail(key_id);
        Log.d(BaseConstant.TAG, "getPoductDetail:call:" + call.request().toString());

        /**
         * Оценка времени с последнего именно такого запроса
         */
        if (System.currentTimeMillis() - getDateTimeFromGetCache(call.request().toString()) > timeLoadedFromServer) {

            call.enqueue(new Callback<List<ProductDetailImage>>() {

                @Override
                public void onResponse(Call<List<ProductDetailImage>> call, Response<List<ProductDetailImage>> response) {
                    if (response.isSuccessful()) {
                        productDetailImages.addAll(response.body()); //this is object ProductDetail
                        EventBus.getDefault().post(new ProductDetailImages(productDetailImages, 200));
                        putProductDetailImages(productDetailImages);
                        putGetCache(call.request().toString());
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
                        getProductDetailImagesDao(key_id, response.code());
                    }
                }

                @Override
                public void onFailure(Call<List<ProductDetailImage>> call, Throwable throwable) {
                    Log.d(BaseConstant.TAG, "Что-то пошло не так");
                    Log.d(BaseConstant.TAG, "Что-то пошло не так:throwable:" + throwable.toString());
                    getProductDetailImagesDao(key_id, 501);
                }
            });
        } else {
            /**
             *  Отдаем из кэша ode = 700
             */
            getProductDetailImagesDao(key_id, 700);
        }
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

    private void getSubCategory(final int id_category) {
        Log.d(BaseConstant.TAG, "Start:ServiceHelper:ServiceIntentGetData:: services.getSubCategory.id_category:" + id_category);
        ShopAPI shopApi = ShopAPI.retrofit.create(ShopAPI.class);
        final Call<List<SubCategoryItem>> call = shopApi.getSubCatalog(id_category);
        Log.d(BaseConstant.TAG, "getSubCategory:call:" + call.request().toString());

        /**
         * Оценка времени с последнего именно такого запроса
         */
        if (System.currentTimeMillis() - getDateTimeFromGetCache(call.request().toString()) > timeLoadedFromServer) {

            call.enqueue(new Callback<List<SubCategoryItem>>() {

                @Override
                public void onResponse(Call<List<SubCategoryItem>> call, Response<List<SubCategoryItem>> response) {
                    if (response.isSuccessful()) {
                        subCategoryItems.addAll(response.body());
                        EventBus.getDefault().post(new SubCategoryItems(subCategoryItems, 200));
                        putSubCategoryItems(subCategoryItems);
                        putGetCache(call.request().toString());
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
                        getSubCategoryItemDaoDao(id_category, response.code());
                    }
                }

                @Override
                public void onFailure(Call<List<SubCategoryItem>> call, Throwable throwable) {
                    Log.d(BaseConstant.TAG, "Что-то пошло не так");
                    getSubCategoryItemDaoDao(id_category, 501);
                }
            });
        } else {
            /**
             *  Отдаем из кэша ode = 700
             */
            getSubCategoryItemDaoDao(id_category, 700);
        }
    }

    /**
     * Получаем дату+время последнего именно такого запроса к серверу
     * для решение делать запрос или взять данные из БД
     *
     * @return
     */
    private long getDateTimeFromGetCache(String get_Url) {
        Query<GetCache> mGetCache = mDaoSession.queryBuilder(GetCache.class)
                .where(GetCacheDao.Properties.FieldGet.eq(get_Url)).build();
        Log.d("MyLogs", "QueryBuilder<NewPerson> pers:" + mGetCache.toString());
        mGetCacheList = mGetCache.list();

        if (mGetCacheList == null || mGetCacheList.size() < 1) {
            return 0;
        } else {
            return mGetCacheList.get(0).getDateTime();
        }
    }


    /**
     * Установка значения времени запроса именно такого Get ресурса
     *
     * @param get_Url
     */
    private void putGetCache(String get_Url) {
        Query<GetCache> mGetCache = mDaoSession.queryBuilder(GetCache.class)
                .where(GetCacheDao.Properties.FieldGet.eq(get_Url)).build();
        mGetCacheList = mGetCache.list();
        if (mGetCacheList == null || mGetCacheList.size() < 1) {
            GetCache mGetCachePut = new GetCache(get_Url, System.currentTimeMillis());
            mGetCacheDao.insertOrReplace(mGetCachePut);
        } else {
            GetCache mGetCachePut = mGetCacheList.get(0);
            mGetCachePut.setDateTime(System.currentTimeMillis());
            mGetCacheDao.update(mGetCachePut);
        }
    }

    /**
     * Вставка и удаление из кэша старых записей
     *
     * @param productCategory
     */
    private void putProductCategory(List<ProductCategory> productCategory) {
        if (productCategory == null || productCategory.size() < 1) {
            return;
        } else {
            for (int i = 0; i < productCategory.size(); i++) {
                Query<ProductCategory> mProductCategoryQuery = mDaoSession.queryBuilder(ProductCategory.class)
                        .where(ProductCategoryDao.Properties.Menu_ID.eq(productCategory.get(i).getMenu_ID())).build();
                List<ProductCategory> productCategoryList = mProductCategoryQuery.list();
                if (productCategoryList != null && productCategoryList.size() != 0 && !productCategoryList.isEmpty()) {
//                    ProductCategory mProductCategoryDelete = productCategoryList.get(0);
//                    mProductCategoryDao.delete(mProductCategoryDelete);
                    mProductCategoryDao.deleteInTx(productCategoryList);
                }
            }
            mProductCategoryDao.insertOrReplaceInTx(productCategory);
        }
    }

    /**
     * Выборка каталога продуктов
     * productCategoryId -ID подкатегории продуктов
     * errorsId - номер ошибки или код завершения
     */
    private void getProductCategoryDao(int productCategoryId, int errorsId) {
        Query<ProductCategory> mProducts = mDaoSession.queryBuilder(ProductCategory.class)
                .where(ProductCategoryDao.Properties.SubCategory_ID.eq(productCategoryId)).build();
        Log.d("MyLogs", "QueryBuilder<NewPerson> pers:" + mProducts.toString());
        productCategory = mProducts.list();
        EventBus.getDefault().post(new ProductCategoris(productCategory, errorsId));
    }


    /**
     * Вставка и удаление из кэша старых записей подкатегорий
     *
     * @param subCategoryItems
     */
    private void putSubCategoryItems(List<SubCategoryItem> subCategoryItems) {
        if (subCategoryItems == null || subCategoryItems.size() < 1) {
            return;
        } else {
            for (int i = 0; i < subCategoryItems.size(); i++) {
                Query<SubCategoryItem> mSubCategoryItem = mDaoSession.queryBuilder(SubCategoryItem.class)
                        .where(SubCategoryItemDao.Properties.SubCategory_ID.eq(subCategoryItems.get(i).getSubCategory_ID())).build();
                List<SubCategoryItem> mSubCategoryItemList = mSubCategoryItem.list();
                if (mSubCategoryItemList != null && mSubCategoryItemList.size() != 0 && !mSubCategoryItemList.isEmpty()) {
                    mSubCategoryItemDao.deleteInTx(mSubCategoryItemList);
                }
            }
            mSubCategoryItemDao.insertOrReplaceInTx(subCategoryItems);
        }
    }

    /**
     * Чтение из кэша подкатегорий
     *
     * @param subCategory_ID
     * @param errorsId
     */
    private void getSubCategoryItemDaoDao(int subCategory_ID, int errorsId) {
        Query<SubCategoryItem> mSubCategoryItem = mDaoSession.queryBuilder(SubCategoryItem.class)
                .where(SubCategoryItemDao.Properties.Category_ID.eq(subCategory_ID)).build();
        Log.d("MyLogs", "QueryBuilder<NewPerson> pers:" + mSubCategoryItem.toString());
        subCategoryItems = mSubCategoryItem.list();
        EventBus.getDefault().post(new SubCategoryItems(subCategoryItems, errorsId));
    }


    /**
     * Помещаем в таблицу рисунки и дополнительно описание продукта
     *
     * @param productDetailImages
     */
    private void putProductDetailImages(List<ProductDetailImage> productDetailImages) {
        if (productDetailImages == null && productDetailImages.size() < 1) {
            return;
        } else {
            for (int i = 0; i < productDetailImages.size(); i++) {
                Query<ProductDetailImage> mProductDetailImage = mDaoSession.queryBuilder(ProductDetailImage.class)
                        .where(ProductDetailImageDao.Properties.Menu_ID.eq(productDetailImages.get(i).getMenu_ID())).build();
                List<ProductDetailImage> productDetailImagesList = mProductDetailImage.list();
                if (productDetailImagesList != null && productDetailImagesList.size() != 0 && !productDetailImagesList.isEmpty()) {
                    mProductDetailImageDao.deleteInTx(productDetailImagesList);
                }
            }
            mProductDetailImageDao.insertOrReplaceInTx(productDetailImages);
        }
    }

    /**
     * Чтание из кэша данных по деталировке продукта
     *
     * @param pId    = Product_Id
     * @param errors
     */
    private void getProductDetailImagesDao(int pId, int errors) {
        Query<ProductDetailImage> mProductDetailImages = mDaoSession.queryBuilder(ProductDetailImage.class)
                .where(ProductDetailImageDao.Properties.Product_ID.eq(pId)).build();
        productDetailImages = mProductDetailImages.list();
        EventBus.getDefault().post(new ProductDetailImages(productDetailImages, errors));
    }

    /**
     * Получем список основных категорий
     *
     * @param errors
     */
    private void getCategoryDao(int errors) {
        Query<CategoryItem> mCategoryItem = mDaoSession.queryBuilder(CategoryItem.class).build();
        categoryItems = mCategoryItem.list();
        EventBus.getDefault().post(new CategoryItems(categoryItems, errors));
    }

    /**
     * Вставка и удаление из кэша старых Категорий 1-го уровня
     *
     * @param mCategoryItem
     */
    private void putCategory(List<CategoryItem> mCategoryItem) {
        if (mCategoryItem == null || mCategoryItem.size() < 1) {
            return;
        } else {
            Query<CategoryItem> mCategoryQuery = mDaoSession.queryBuilder(CategoryItem.class).build();
            List<CategoryItem> categoryItemList = mCategoryQuery.list();
            if (categoryItemList != null && categoryItemList.size() != 0 && !categoryItemList.isEmpty()) {
                mCategoryItemDao.deleteInTx(categoryItemList);
            }
            mCategoryItemDao.insertOrReplaceInTx(mCategoryItem);
        }
    }

}
