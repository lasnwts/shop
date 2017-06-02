package nwts.ru.autoshop.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.databases.DataManager;
import nwts.ru.autoshop.models.DaoSession;
import nwts.ru.autoshop.models.GetCache;
import nwts.ru.autoshop.models.GetCacheDao;
import nwts.ru.autoshop.models.network.CabinetModels;
import nwts.ru.autoshop.models.network.ProductComment;
import nwts.ru.autoshop.models.network.ProductCommentDao;
import nwts.ru.autoshop.models.network.cart.CartModel;
import nwts.ru.autoshop.models.network.cart.CartModelDao;
import nwts.ru.autoshop.models.network.cart.CartModels;
import nwts.ru.autoshop.models.network.cart.ErrorModel;
import nwts.ru.autoshop.models.network.cart.ErrorModelDao;
import nwts.ru.autoshop.models.network.cart.ErrorModels;
import nwts.ru.autoshop.models.network.orders.BalOrderModel;
import nwts.ru.autoshop.models.network.orders.BalOrderModelDao;
import nwts.ru.autoshop.models.network.orders.BalOrderModels;
import nwts.ru.autoshop.models.network.orders.ProductComments;
import nwts.ru.autoshop.network.request.ShopAPI;
import nwts.ru.autoshop.setting.BaseConstant;
import nwts.ru.autoshop.setting.PreferenceHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by пользователь on 19.05.2017.
 */

public class ServiceIntentGetDataMore extends IntentService {

    //cart
    private List<CartModel> mCartModels;
    private CabinetModels mCabinetModelsList;
    private CartModelDao mCartModelDao;

    //Comments
    private List<ProductComment> mProductCommentList;
    private ProductComments mProductComments;
    private ProductCommentDao mProductCommentDao;

    //Bal order id
    private List<BalOrderModel> mBalOrderModelList;
    private BalOrderModels mBalOrderModels;
    private BalOrderModelDao mBalOrderModelDao;

    //error
    private List<ErrorModel> mErrorModelList;
    private ErrorModel mErrorModel;
    private ErrorModelDao mErrorModelDao;

    private DaoSession mDaoSession;
    List<GetCache> mGetCacheList;
    GetCache mGetCache;
    private GetCacheDao mGetCacheDao;
    private long timeLoadedFromServer = 60000 * 3; //1 min


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public ServiceIntentGetDataMore() {
        super("name");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mGetCacheList = new ArrayList<>();
        mCartModels = new ArrayList<>();
        mErrorModelList = new ArrayList<>();
        mBalOrderModelList = new ArrayList<>();
        mProductCommentList = new ArrayList<>();
        DataManager dataManager = DataManager.getInstance();
        mDaoSession = dataManager.getDaoSession();
        if (mDaoSession == null) {
            Log.d(BaseConstant.LOG_TAG, "ServiceIntentGetData:DataManager dataManager =" +
                    "DataManager.getInstance();mDaoSession = dataManager.getDaoSession()== null!!");
        } else {
            mGetCacheDao = mDaoSession.getGetCacheDao();
            mCartModelDao = mDaoSession.getCartModelDao();
            mErrorModelDao = mDaoSession.getErrorModelDao();
            mBalOrderModelDao = mDaoSession.getBalOrderModelDao();
            mProductCommentDao = mDaoSession.getProductCommentDao();
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            if (intent.getStringExtra(BaseConstant.API_PAGE).equals(BaseConstant.ACTION_SERVICE_GET_BALANCE_ADD)) {
                int key_id = intent.getIntExtra(BaseConstant.API_GET_KEY, 0);
                double sumBal = intent.getDoubleExtra(BaseConstant.API_BAL_SUM, 0);
                String paySys = intent.getStringExtra(BaseConstant.API_BAL_SYS);
                postSumBalance(key_id, sumBal, paySys);
            }
            if (intent.getStringExtra(BaseConstant.API_PAGE).equals(BaseConstant.ACTION_SERVICE_GET_PROCESSING_ID)) {
                int key_id = intent.getIntExtra(BaseConstant.API_GET_KEY, 0);
                int statusID = TODOApplication.getStatusID();
                postProcessing(key_id, statusID);
            }
            if (intent.getStringExtra(BaseConstant.API_PAGE).equals(BaseConstant.ACTION_SERVICE_GET_COMMENTS_ID)) {
                int key_id = intent.getIntExtra(BaseConstant.API_GET_KEY, 0);
                getComments(key_id);
            }
            if (intent.getStringExtra(BaseConstant.API_PAGE).equals(BaseConstant.ACTION_SERVICE_ADD_COMMENTS)) {
                int key_id = intent.getIntExtra(BaseConstant.API_GET_KEY, 0);
                int rating = intent.getIntExtra(BaseConstant.API_RATING, 0);
                int productID = intent.getIntExtra(BaseConstant.API_PRODUCT_ID, 0);
                String messages = intent.getStringExtra(BaseConstant.API_COOMENT);
                putComments(key_id, rating, productID, messages);
            }
            if (intent.getStringExtra(BaseConstant.API_PAGE).equals(BaseConstant.ACTION_SERVICE_GET_CART)) {
                int key_id = intent.getIntExtra(BaseConstant.API_GET_KEY, 0);
                if (mCartModels != null) {
                    mCartModels.clear();
                }
                getCart(key_id);
            }
            if (intent.getStringExtra(BaseConstant.API_PAGE).equals(BaseConstant.ACTION_SERVICE_GET_CART_INPUT)) {
                int key_id = PreferenceHelper.getInstance().getUserId();
                if (mErrorModelList != null) {
                    mErrorModelList.clear();
                }
                postCartInput(key_id, intent.getIntExtra(BaseConstant.API_KEY_ID, 0),
                        intent.getIntExtra(BaseConstant.API_GET_KEY, 1));
            }
            if (intent.getStringExtra(BaseConstant.API_PAGE).equals(BaseConstant.ACTION_SERVICE_GET_BALANCE_ID)) {
                int key_id = TODOApplication.getKey_id();
                if (mBalOrderModelList != null) {
                    mBalOrderModelList.clear();
                }
                getBalOrderModel(key_id);
            }
        }
    }

    private void getComments(final int key_id) {
        ShopAPI shopApi = ShopAPI.retrofit.create(ShopAPI.class);
        final Call<List<ProductComment>> call = shopApi.getComments(key_id);
        call.enqueue(new Callback<List<ProductComment>>() {
            @Override
            public void onResponse(Call<List<ProductComment>> call, Response<List<ProductComment>> response) {
                if (response.isSuccessful()) {
                    mProductCommentList.clear();
                    mProductCommentList.addAll(response.body());
                    putProductComments(mProductCommentList, key_id);
                    getProductComments(key_id, 200);
                } else {
                    getProductComments(key_id, 400);
                }
            }

            @Override
            public void onFailure(Call<List<ProductComment>> call, Throwable t) {
                getProductComments(key_id, 500);
            }
        });
    }

    private void getBalOrderModel(final int key_id) {
        ShopAPI shopApi = ShopAPI.retrofit.create(ShopAPI.class);
        final Call<List<BalOrderModel>> call = shopApi.getBalOrdersId(key_id);
        call.enqueue(new Callback<List<BalOrderModel>>() {
            @Override
            public void onResponse(Call<List<BalOrderModel>> call, Response<List<BalOrderModel>> response) {
                if (response.isSuccessful()) {
                    mBalOrderModelList.clear();
                    mBalOrderModelList.addAll(response.body());
                    putGetCache(call.request().toString());
                    putBalOrderModels(mBalOrderModelList, key_id);
                    getBalOrderModels(key_id, 200);

                } else {
                    getBalOrderModels(key_id, 400);
                }
            }

            @Override
            public void onFailure(Call<List<BalOrderModel>> call, Throwable t) {
                getBalOrderModels(key_id, 500);
            }
        });
    }

    private void getProductComments(int key_id, int errors) {
        Query<ProductComment> qProductCommentList = mDaoSession.queryBuilder(ProductComment.class)
                .where(ProductCommentDao.Properties.Product_ID.eq(key_id)).build();
        List<ProductComment> mProductCommentsOut = qProductCommentList.list();
        EventBus.getDefault().post(new ProductComments(mProductCommentsOut, errors));
    }

    private void getBalOrderModels(int key_id, int errors) {
        Query<BalOrderModel> balOrderModels = mDaoSession.queryBuilder(BalOrderModel.class).
                where(BalOrderModelDao.Properties.Oper_ID.eq(key_id)).build();
        List<BalOrderModel> balOrderModels1 = balOrderModels.list();
        EventBus.getDefault().post(new BalOrderModels(balOrderModels1, errors));
    }

    private void putProductComments(List<ProductComment> productComments, int key_id) {
        if (productComments == null || productComments.size() < 1) {
            return;
        } else {
            Query<ProductComment> qProductCommentDel = mDaoSession.queryBuilder(ProductComment.class)
                    .where(ProductCommentDao.Properties.Product_ID.eq(key_id)).build();
            List<ProductComment> mProductCommentsDel = qProductCommentDel.list();
            if (mProductCommentsDel != null && mProductCommentsDel.size() != 0 && !mProductCommentsDel.isEmpty()) {
                mProductCommentDao.deleteInTx(mProductCommentsDel);
            }
            mProductCommentDao.insertOrReplaceInTx(productComments);
        }
    }

    private void putBalOrderModels(List<BalOrderModel> balOrderModels, int key_id) {
        if (balOrderModels == null || balOrderModels.size() < 1) {
            return;
        } else {
            Query<BalOrderModel> mBalOrderModelDel = mDaoSession.queryBuilder(BalOrderModel.class)
                    .where(BalOrderModelDao.Properties.Oper_ID.eq(key_id)).build();
            List<BalOrderModel> balOrderModelsDel = mBalOrderModelDel.list();
            if (balOrderModelsDel != null && balOrderModelsDel.size() != 0 && !balOrderModelsDel.isEmpty()) {
                mBalOrderModelDao.deleteInTx(balOrderModelsDel);
            }
            mBalOrderModelDao.insertInTx(balOrderModels);
        }
    }


    //add cooment
    private void putComments(int keyId, int rating, int productID, String messages) {
        ShopAPI shopApi = ShopAPI.retrofit.create(ShopAPI.class);
        final Call<List<ErrorModel>> call = shopApi.addComment(keyId, productID, rating, messages);
        Log.d(ServiceIntentGetDataMore.class.getCanonicalName(), call.toString());
        call.enqueue(new Callback<List<ErrorModel>>() {
            @Override
            public void onResponse(Call<List<ErrorModel>> call, Response<List<ErrorModel>> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 201 || response.code() == 200) {
                        if (response.body().get(0).getMessage() != null && !TextUtils.isEmpty(response.body().get(0).getMessage())) {
                            try {
                                TODOApplication.setDetail_rating(Integer.parseInt(response.body().get(0).getMessage()));
                            } catch (NumberFormatException e) {
                            }
                            //
                        }
                        Intent intentService = new Intent(getApplication(), ServiceHelper.class);
                        intentService.setAction(BaseConstant.ACTION_SERVICE_GET_COMMENTS_ID);
                        startService(intentService);
                    } else {
                        //get error messgae
                        setErrorMessage("Возникла непонятная ошибка:" + response.code() + " " + response.body().toString());
                    }
                } else {
                    //get error message
                    setErrorMessage("Возникла ошибка! Код ошибки:" + response.code() + "; сообщение: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<ErrorModel>> call, Throwable throwable) {
                //get error message
                setErrorMessage("Возникла сетевая ошибка. Попробуйте позже, после установления связи. Сообщение: " + throwable.toString());
            }
        });
    }


    private void postSumBalance(int key_id, double sumBal, String paySys) {
        ShopAPI shopApi = ShopAPI.retrofit.create(ShopAPI.class);
        final Call<ErrorModel> call = shopApi.addBalance(sumBal, paySys);
        call.enqueue(new Callback<ErrorModel>() {
            @Override
            public void onResponse(Call<ErrorModel> call, Response<ErrorModel> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 201 || response.code() == 200) {
                        //   delBalanceCache(Api.GET_CABINET_BALANCE);
                        Intent intentService = new Intent(getApplication(), ServiceHelper.class);
                        intentService.setAction(BaseConstant.ACTION_SERVICE_GET_BALANCE);
                        intentService.putExtra(BaseConstant.API_GET_KEY, PreferenceHelper.getInstance().getUserId());
                        getApplication().startService(intentService);

                    } else {
                        //get error messgae
                        setErrorMessage("Возникла непонятная ошибка:" + response.code() + " " + response.body().toString());
                    }
                } else {
                    //get error message
                    setErrorMessage("Возникла ошибка! Код ошибки:" + response.code() + "; сообщение: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ErrorModel> call, Throwable throwable) {
                //get error message
                setErrorMessage("Возникла сетевая ошибка. Попробуйте позже, после установления связи. Сообщение: " + throwable.toString());
            }
        });
    }

    private void postCartInput(int userID, int productID, int quantiy) {
        ShopAPI shopApi = ShopAPI.retrofit.create(ShopAPI.class);
        final Call<List<ErrorModel>> call = shopApi.getInputCart(userID, productID, quantiy);
        call.enqueue(new Callback<List<ErrorModel>>() {
            @Override
            public void onResponse(Call<List<ErrorModel>> call, Response<List<ErrorModel>> response) {
                if (response.isSuccessful()) {
                    //
                    mErrorModelList.clear();
                    mErrorModelList.addAll(response.body());
                    //
                    Intent intentService = new Intent(getApplicationContext(), ServiceHelper.class);
                    intentService.setAction(BaseConstant.ACTION_SERVICE_GET_PRODUCT_LIST);
                    intentService.putExtra(BaseConstant.API_GET_KEY, TODOApplication.getCategory_Id());
                    startService(intentService);
                    EventBus.getDefault().post(new ErrorModels(mErrorModelList, 200));
                } else {
                    //get error messgae
                    setErrorMessage("Возникла непонятная ошибка:" + response.code() + " " + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<List<ErrorModel>> call, Throwable throwable) {
                //get error message
                setErrorMessage("Возникла сетевая ошибка. Попробуйте позже, после установления связи. Сообщение: " + throwable.toString());
                Log.d("Service" + ServiceIntentGetDataMore.class.getName(), "Error: " + throwable.toString());
            }

        });
    }


    private void postProcessing(int key_id, int statusID) {
        ShopAPI shopApi = ShopAPI.retrofit.create(ShopAPI.class);
        final Call<ErrorModel> call = shopApi.getProcessingCart(key_id, statusID);
        call.enqueue(new Callback<ErrorModel>() {
            @Override
            public void onResponse(Call<ErrorModel> call, Response<ErrorModel> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 201) {
                        Intent intentService = new Intent(getApplication(), ServiceHelper.class);
                        intentService.setAction(BaseConstant.ACTION_SERVICE_GET_CART);
                        intentService.putExtra(BaseConstant.API_GET_KEY, PreferenceHelper.getInstance().getUserId());
                        getApplication().startService(intentService);
                    } else {
                        //get error messgae
                        setErrorMessage("Возникла непонятная ошибка:" + response.code() + " " + response.body().toString());
                    }
                } else {
                    //get error message
                    setErrorMessage("Возникла ошибка! Код ошибки:" + response.code() + "; сообщение: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ErrorModel> call, Throwable throwable) {
                //get error message
                setErrorMessage("Возникла сетевая ошибка. Попробуйте позже, после установления связи. Сообщение: " + throwable.toString());
            }
        });
    }


    //get cart
    private void getCart(final int userId) {
        ShopAPI shopApi = ShopAPI.retrofit.create(ShopAPI.class);
        final Call<List<CartModel>> call = shopApi.getCart();
        call.enqueue(new Callback<List<CartModel>>() {
            @Override
            public void onResponse(Call<List<CartModel>> call, Response<List<CartModel>> response) {
                if (response.isSuccessful()) {
                    mCartModels.clear();
                    mCartModels.addAll(response.body());
                    putGetCache(call.request().toString());
                    putCartModelItems(mCartModels);
                    getCartModelDao(userId, 200);
                } else {
                    getCartModelDao(userId, 400);
                }
            }

            @Override
            public void onFailure(Call<List<CartModel>> call, Throwable throwable) {
                getCartModelDao(userId, 500);
            }
        });
    }


//    private void delBalanceCache(String get_Url) {
//        if (TODOApplication.getUrlGetBalance() == null) {
//            return;
//        }
//        Query<GetCache> mGetCache = mDaoSession.queryBuilder(GetCache.class)
//                .where(GetCacheDao.Properties.FieldGet.like(TODOApplication.getUrlGetBalance())).build();
//        mGetCacheList = mGetCache.list();
//        if (mGetCacheList != null || mGetCacheList.size() > 0) {
//            mGetCacheDao.deleteInTx(mGetCacheList);
//        }
//    }


    /**
     * Вставка и удаление из кэша старых записей подкатегорий
     *
     * @param mCartModels
     */
    private void putCartModelItems(List<CartModel> mCartModels) {
        if (mCartModels == null || mCartModels.size() < 1) {
            return;
        } else {
            Query<CartModel> mCartModelList = mDaoSession.queryBuilder(CartModel.class).build();
            List<CartModel> mCartList = mCartModelList.list();
            if (mCartList != null && mCartList.size() != 0 && !mCartList.isEmpty()) {
                mCartModelDao.deleteAll();
            }
            mCartModelDao.insertOrReplaceInTx(mCartModels);
        }
    }

    private void setErrorMessage(String error) {
        EventBus.getDefault().post(new ErrorModel(true, error));
    }

    private void getCartModelDao(int userId, int errorsId) {
        Query<CartModel> mCartModel = mDaoSession.queryBuilder(CartModel.class).build();
        mCartModels = mCartModel.list();
        EventBus.getDefault().post(new CartModels(mCartModels, errorsId));
        Intent intentService = new Intent(this, ServiceHelper.class);
        intentService.setAction(BaseConstant.ACTION_SERVICE_GET_CABINET);
        startService(intentService);
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


}
