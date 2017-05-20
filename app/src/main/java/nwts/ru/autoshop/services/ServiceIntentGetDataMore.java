package nwts.ru.autoshop.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nwts.ru.autoshop.databases.DataManager;
import nwts.ru.autoshop.models.DaoSession;
import nwts.ru.autoshop.models.GetCache;
import nwts.ru.autoshop.models.GetCacheDao;
import nwts.ru.autoshop.setting.BaseConstant;

/**
 * Created by пользователь on 19.05.2017.
 */

public class ServiceIntentGetDataMore extends IntentService {

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
    public ServiceIntentGetDataMore(String name) {
        super("name");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mGetCacheList = new ArrayList<>();
        DataManager dataManager = DataManager.getInstance();
        mDaoSession = dataManager.getDaoSession();
        if (mDaoSession == null) {
            Log.d(BaseConstant.LOG_TAG, "ServiceIntentGetData:DataManager dataManager =" +
                    "DataManager.getInstance();mDaoSession = dataManager.getDaoSession()== null!!");
        } else {
            mGetCacheDao = mDaoSession.getGetCacheDao();
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {

        }
    }
}
