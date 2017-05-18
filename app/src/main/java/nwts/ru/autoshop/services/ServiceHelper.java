package nwts.ru.autoshop.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import nwts.ru.autoshop.setting.BaseConstant;
import nwts.ru.autoshop.setting.PreferenceHelper;

import static nwts.ru.autoshop.setting.BaseConstant.ACTION_SERVICE_GET_CATEGORY_LIST;
import static nwts.ru.autoshop.setting.BaseConstant.ACTION_SERVICE_GET_PRODUCT_DETAIL;
import static nwts.ru.autoshop.setting.BaseConstant.ACTION_SERVICE_GET_PRODUCT_LIST;
import static nwts.ru.autoshop.setting.BaseConstant.ACTION_SERVICE_GET_SUBCATEGORY_LIST;

/**
 * Created by пользователь on 13.03.2017.
 */

public class ServiceHelper extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(BaseConstant.TAG, "Start ServiceHelper:onStartCommand: services..");
        if (intent != null){
            if (intent.getAction() != null) {
                if (intent.getAction().equals(BaseConstant.ACTION_SERVICE_GET_ORDERS)){
                    Log.d(BaseConstant.TAG, "Start ServiceHelper:BaseConstant.ACTION_SERVICE_GET_ORDERS..");
                    Intent intent1Service = new Intent(getApplication(), ServiceIntentGetData.class);
                    intent1Service.putExtra(BaseConstant.API_PAGE ,BaseConstant.ACTION_SERVICE_GET_ORDERS);
                    intent1Service.putExtra(BaseConstant.API_GET_KEY , PreferenceHelper.getInstance().getUserId());
                    startService(intent1Service);
                }
                if (intent.getAction().equals(BaseConstant.ACTION_SERVICE_GET_CABINET)){
                    Log.d(BaseConstant.TAG, "Start ServiceHelper:BaseConstant.ACTION_SERVICE_GET_CABINET..");
                    Intent intent1Service = new Intent(getApplication(), ServiceIntentGetData.class);
                    intent1Service.putExtra(BaseConstant.API_PAGE ,BaseConstant.ACTION_SERVICE_GET_CABINET);
                    startService(intent1Service);
                }
                if (intent.getAction().equals(BaseConstant.ACTION_SERVICE_GET_CATEGORY_LIST)){
                    Log.d(BaseConstant.TAG, "Start ServiceHelper:BaseConstant.ACTION_SERVICE_GET_CATEGORY_LIST..");
                    Intent intent1Service = new Intent(getApplication(), ServiceIntentGetData.class);
                    intent1Service.putExtra(BaseConstant.API_PAGE ,BaseConstant.ACTION_SERVICE_GET_CATEGORY_LIST);
                    intent1Service.putExtra(BaseConstant.API_GET_KEY ,BaseConstant.GET_CATEGORY);
                    startService(intent1Service);
                }
                if (intent.getAction().equals(ACTION_SERVICE_GET_PRODUCT_LIST)){
                    Log.d(BaseConstant.TAG, "Start ServiceHelper:BaseConstant.ACTION_SERVICE_GET_PRODUCT_LIST..");
                    int key_id = intent.getIntExtra(BaseConstant.API_GET_KEY,1);
                    Log.d(BaseConstant.TAG, "Start ServiceHelper:BaseConstant.ACTION_SERVICE_GET_PRODUCT_LIST:key_id :"+key_id);
                    Intent intent1Service = new Intent(getApplication(), ServiceIntentGetData.class);
                    intent1Service.putExtra(BaseConstant.API_PAGE ,BaseConstant.ACTION_SERVICE_GET_PRODUCT_LIST);
                    intent1Service.putExtra(BaseConstant.API_GET_KEY ,key_id);
                    startService(intent1Service);
                }
                if (intent.getAction().equals(ACTION_SERVICE_GET_SUBCATEGORY_LIST)){
                    Log.d(BaseConstant.TAG, "Start ServiceHelper:BaseConstant.ACTION_SERVICE_GET_SUBCATEGORY_LIST..");
                    int key_id = intent.getIntExtra(BaseConstant.API_GET_KEY,1);
                    Log.d(BaseConstant.TAG, "Start ServiceHelper:BaseConstant.ACTION_SERVICE_GET_SUBCATEGORY_LIST:key_id :"+key_id);
                    Intent intent1Service = new Intent(getApplication(), ServiceIntentGetData.class);
                    intent1Service.putExtra(BaseConstant.API_PAGE ,BaseConstant.ACTION_SERVICE_GET_SUBCATEGORY_LIST);
                    intent1Service.putExtra(BaseConstant.API_GET_KEY ,key_id);
                    startService(intent1Service);
                }
                if (intent.getAction().equals(ACTION_SERVICE_GET_PRODUCT_DETAIL)){
                    Log.d(BaseConstant.TAG, "Start ServiceHelper:BaseConstant.ACTION_SERVICE_GET_PRODUCT_DETAIL..");
                    int key_id = intent.getIntExtra(BaseConstant.API_GET_KEY,2);
                    Log.d(BaseConstant.TAG, "Start ServiceHelper:BaseConstant.ACTION_SERVICE_GET_PRODUCT_LIST:key_id :"+key_id);
                    Intent intent1Service = new Intent(getApplication(), ServiceIntentGetData.class);
                    intent1Service.putExtra(BaseConstant.API_PAGE ,BaseConstant.ACTION_SERVICE_GET_PRODUCT_DETAIL);
                    intent1Service.putExtra(BaseConstant.API_GET_KEY ,key_id);
                    startService(intent1Service);
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        Log.d(BaseConstant.TAG,"ServiceHelper: destroy..");
        super.onDestroy();
    }
}

