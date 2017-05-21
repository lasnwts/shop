package nwts.ru.autoshop.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import nwts.ru.autoshop.databases.DataManager;
import nwts.ru.autoshop.models.DaoSession;
import nwts.ru.autoshop.models.GetCache;
import nwts.ru.autoshop.models.GetCacheDao;
import nwts.ru.autoshop.models.network.CabinetModels;
import nwts.ru.autoshop.models.network.cart.CartModel;
import nwts.ru.autoshop.models.network.cart.CartModelDao;
import nwts.ru.autoshop.models.network.cart.CartModels;
import nwts.ru.autoshop.network.request.ShopAPI;
import nwts.ru.autoshop.setting.BaseConstant;
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

    private DaoSession mDaoSession;
    List<GetCache> mGetCacheList;
    GetCache mGetCache;
    private GetCacheDao mGetCacheDao;
    private long timeLoadedFromServer = 60000 * 3; //1 min


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ServiceIntentGetDataMore() {
        super("name");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mGetCacheList = new ArrayList<>();
        mCartModels = new ArrayList<>();
        DataManager dataManager = DataManager.getInstance();
        mDaoSession = dataManager.getDaoSession();
        if (mDaoSession == null) {
            Log.d(BaseConstant.LOG_TAG, "ServiceIntentGetData:DataManager dataManager =" +
                    "DataManager.getInstance();mDaoSession = dataManager.getDaoSession()== null!!");
        } else {
            mGetCacheDao = mDaoSession.getGetCacheDao();
            mCartModelDao = mDaoSession.getCartModelDao();
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            if (intent.getStringExtra(BaseConstant.API_PAGE).equals(BaseConstant.ACTION_SERVICE_GET_CART)) {
                int key_id = intent.getIntExtra(BaseConstant.API_GET_KEY, 0);
                if (mCartModels != null) {
                    mCartModels.clear();
                }
                getCart(key_id);
            }
        }
    }

    //get cart
    private void getCart(final int userId) {
        ShopAPI shopApi = ShopAPI.retrofit.create(ShopAPI.class);
        final Call<List<CartModel>> call = shopApi.getCart();
        if (System.currentTimeMillis() - getDateTimeFromGetCache(call.request().toString()) > timeLoadedFromServer) {
            call.enqueue(new Callback<List<CartModel>>() {
                @Override
                public void onResponse(Call<List<CartModel>> call, Response<List<CartModel>> response) {
                    if (response.isSuccessful()) {
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
        } else {
            getCartModelDao(userId, 700);
        }
    }


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

    private void getCartModelDao(int userId, int errorsId) {
        Query<CartModel> mCartModel = mDaoSession.queryBuilder(CartModel.class).build();
        mCartModels = mCartModel.list();
        EventBus.getDefault().post(new CartModels(mCartModels, errorsId));
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