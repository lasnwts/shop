package nwts.ru.autoshop.databases;

import android.app.Application;
import android.content.Context;

import com.squareup.picasso.Picasso;

import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.models.DaoSession;
import nwts.ru.autoshop.network.PicassoCache;

/**
 * Created by пользователь on 10.04.2017.
 */

public class DataManager {
    private static DataManager INSTANCE = null;
    private Context mContext;
    private Picasso mPicasso;
    private DaoSession mDaoSession;


    public DataManager(Context context) {
        this.mContext = context;
        mPicasso = new PicassoCache(mContext).getPicassoInstance();
        mDaoSession = TODOApplication.getDaoSession();
    }

    public static DataManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataManager(TODOApplication.getInstance());
        }
        return INSTANCE;
    }


    public Context getContext() {
        return mContext;
    }

    public Picasso getPicasso() {
        /**
         * Индикация загрузки
         */
        mPicasso.setIndicatorsEnabled(true);
        mPicasso.setLoggingEnabled(true);
        return mPicasso;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

}
